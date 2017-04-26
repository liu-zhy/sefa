package cn.sefa.vm;

import java.util.ArrayList;

import cn.sefa.ast.ASTList;
import cn.sefa.ast.ASTree;
import cn.sefa.ast.NativeFunction;
import cn.sefa.exception.SefaVMException;

/**
 * @author Lionel
 *
 */
public class SefaVM {

	/*
	 * The memory is divided into four parts, 
	 * including code ,stack , constant literal ,heap.
	 */
	protected byte[] code ;
	protected Object[] stack;
	protected String[] strings ;
	protected HeapMemory heap;
	public int pc , fp , sp , ret ;
	protected Object[] registers ;
	public final static int NUM_OF_REG = 8 ;
	public final static int SAVE_AREA_SIZE = NUM_OF_REG + 2;
	public final static int TRUE = 1 ;
	public final static int FALSE = 0 ;
	
	public SefaVM(int codeSize , int stackSize, int stringsSize , HeapMemory hm){
		code = new byte[codeSize];
		stack = new Object[stackSize] ;
		strings = new String[stringsSize] ;
		registers = new Object[NUM_OF_REG] ;
		heap = hm ;
	}
	
	public Object getReg(int i){
		return registers[i] ;
	}
	
	public void setReg(int i , Object val){
		registers[i] = val ;
	}
	
	public String[] getStrings(){
		return strings ;
	}
	
	public Object[] getRegisters(){
		return registers;
	}
	
	public byte[] getCode(){
		return code ;
	}
	
	public Object[] getStack(){
		return stack ;
	}

	public HeapMemory getHeap(){
		return heap ;
	}
	
	public void run(int entry) {
		pc = entry ;
		fp = 0 ;
		sp = 0 ;
		ret = -1 ;
		while(pc >= 0){
			mainInLoop() ;
		}
	}

	protected void mainInLoop(){		
		switch(code[pc]){
		case Opcode.LOADI :{
			registers[Opcode.decodeRegister(code[pc+5])] = readInt(code,pc+1) ;
			pc += 6 ;
			break ;
		}
		case Opcode.LOADB :{
			registers[Opcode.decodeRegister(code[pc+2])] = (int)code[pc+1] ; 
			pc += 3;
			break ;
		}
		case Opcode.LOADS:{
			registers[Opcode.decodeRegister(code[pc+3])] = strings[readShort(code,pc+1)];
			pc+=4;
			break ;
		}
		case Opcode.MOVE:{
			move(pc+1,pc+2) ;
			break; 
		}
		case Opcode.GMOVE:{
			/*
			 * src and dest are not all register or heap
			 * src is register ,meanwhile dest is heap , or the opposite.
			 */
			gmove();
			break ;
		}
		case Opcode.IFZERO :{
			int regAddr = Opcode.decodeRegister(code[pc+1]) ;
			Object val = registers[regAddr];
			if(val instanceof Integer && ((Integer) val).intValue() == 0){
				pc += readShort(code, pc+2);
			}
			else{
				pc += 4 ;
			}
			break ;
		}
		case Opcode.IFNONZERO :{
			Object val = registers[Opcode.decodeRegister(code[pc+1])];
			if(val instanceof Integer && ((Integer) val).intValue() != 0){
				pc += readShort(code, pc+2);
			}
			else{
				pc += 4 ;
			}
			break ;
		}
		case Opcode.GOTO:{
			Object offset = readShort(code,pc+1) ;
			if(offset instanceof Integer){
				pc += ((Integer) offset).intValue();
			}
			else
				throw new SefaVMException("the value of offset is not a number. ");
			break ;
		}
		case Opcode.CALL:{
			callFunction() ;
			break ;
		}
		case Opcode.RETURN:{
			pc = ret ;
			break ;
		}
		case Opcode.SAVE :{
			saveRegs();
			break;
		}
		case Opcode.RESTORE:{
			restoreRegs();
			break ;
		}
		case Opcode.NEG :{
			if(Opcode.isRegister(code[pc+1])){
				int regAddr = Opcode.decodeRegister(code[pc+1]);
				Object val = registers[regAddr];
				if(val instanceof Integer){
					registers[regAddr] = -(((Integer) val).intValue());
				}
				else
					throw new SefaVMException("The object in register is not an integer type");
			}
			else
				throw new SefaVMException("the code of register in the instruction of NEG is wrong.");
			pc += 2 ;
			break ;
		}
		case Opcode.ARRAYR :{
			readInArray();
			pc+=3;
			break ;
		}
		case Opcode.ARRAYW:{
			writeInArray();
			pc += 4;
			break ;
		}
		default :{
			if(code[pc]>Opcode.LEQ){
				throw new SefaVMException("the code of instructions is incorrect.");
			}
			else{
				computeNumber();
			}
			break ;
			
		}
		}
	}


	private Object readInt(byte[] mem, int i) {
		int res = 0;
		int offset = 2<<8;
		for(int j =i ; j < i+4 ; ++j){
			res = res*offset + mem[j];
		}
		return res;
	}
	
	private int readShort(byte[] mem, int i) {
		
		return mem[i]<<8 | (mem[i+1]&0xff);
	}
	
	private void move(int srcAddr , int destAddr){
		
		byte src = code[srcAddr];
		byte dest = code[destAddr];
		Object value;
		if(Opcode.isRegister(src)){
			value = registers[Opcode.decodeRegister(src)] ; 
		}
		else if(Opcode.isOffset(src)){
			value = stack[fp+Opcode.decodeOffset(src)] ;
		}
		else{
			throw new SefaVMException("the instruction of move decode failure.");
		}
		
		if(Opcode.isRegister(dest)){
			registers[Opcode.decodeRegister(dest)] = value ;
		}
		else if(Opcode.isOffset(dest)){
			stack[fp+Opcode.decodeOffset(dest)] = value ;
		}
		else{
			throw new SefaVMException("the instruction of move decode failure.");
		}
		pc += 3 ;
	}
	
	private void gmove(){
		
		if(Opcode.isRegister(code[pc+1])){
			 heap.write(readShort(code, pc+2), registers[Opcode.decodeRegister(code[pc+1])]);
		}
		else{
			int addr = Opcode.decodeRegister(code[pc+3]);
			registers[addr] = heap.read(readShort(code, pc+1)) ;
		}
		pc += 4 ;
	}
	
	private void callFunction() {
		
		Object val = registers[Opcode.decodeRegister(code[pc+1])] ;
		int numOfArgs = code[pc+2] ;
		if(val instanceof VMFunction 
				&& ((VMFunction)val).getParams().numOfParams() == numOfArgs){
			ret = pc + 3 ;
			pc = ((VMFunction)val).entry() ;
		}
		else if(val instanceof NativeFunction 
				&& ((NativeFunction) val).numOfParams() == numOfArgs){
			Object[] args = new Object[numOfArgs] ;
			for(int i = 0 ; i < numOfArgs ; i++){
				args[i] = stack[sp+i];
			}
			stack[sp] =((NativeFunction)val)
					.invoke(args , new ASTList(new ArrayList<ASTree>()));
			pc += 3 ;
		}
		else
			throw new SefaVMException("function call failed.");
	}
	
	private void saveRegs() {
		
		int size = Opcode.decodeOffset(code[pc+1]) ;
		int dest = size + sp ;
		for(int i = 0 ; i < NUM_OF_REG ; ++i){
			stack[dest++] = registers[i];
		}
		stack[dest++] = fp ;
		fp = sp ;
		sp += size + SAVE_AREA_SIZE ;
		stack[dest++] = ret ;
		pc += 2 ;
	}
	
	private void restoreRegs() {
		
		int dest = Opcode.decodeOffset(code[pc+1])+fp;
		for(int i = 0 ; i < NUM_OF_REG ; ++i){
			registers[i] = stack[dest++];
		}
		sp = fp ;
		fp = ((Integer) stack[dest++]).intValue();
		ret = ((Integer) stack[dest++]).intValue();
		pc += 2 ;
	}
	
	private void computeNumber() {
		int left = Opcode.decodeRegister(code[pc+1]);
		int right = Opcode.decodeRegister(code[pc+2]);
		Object lval = registers[left];
		Object rval = registers[right];
		boolean allNum = (lval instanceof Integer) && (rval instanceof Integer) ;
		if(code[pc] == Opcode.ADD && !allNum){
			registers[left] = String.valueOf(lval)+String.valueOf(rval);
		}
		else if(code[pc] == Opcode.EQUAL &&  !allNum){
			if(lval == null)
				registers[left] = rval == null ? TRUE : FALSE ;
			else
				registers[left] = lval.equals(rval)?TRUE:FALSE;
		}
		else{
			if(!allNum)
				throw new SefaVMException("operands are incompatible. ");
			int res ;
			int lnum = ((Integer) lval).intValue();
			int rnum = ((Integer) rval).intValue();
			switch(code[pc]){
			case Opcode.ADD:{
				res = lnum+rnum;
				break ;
			}
			case Opcode.SUB:{
				res = lnum-rnum ;
				break ;
			}
			case Opcode.MUL:{
				res = lnum*rnum;
				break ;
			}
			case Opcode.DIV:{
				res = lnum/rnum;
				break ;
			}
			case Opcode.REM:{
				res = lnum%rnum	;
				break ;
			}
			case Opcode.EQUAL:{
				res = lnum == rnum ? TRUE : FALSE;
				break;
			}
			case Opcode.MORE:{
				res = lnum>rnum?TRUE:FALSE;
				break ;
			}
			case Opcode.MEQ:{
				res = lnum>=rnum?TRUE:FALSE;
				break ;
			}
			case Opcode.LESS:{
				res = lnum<rnum?TRUE:FALSE;
				break ;
			}
			case Opcode.LEQ:{
				res = lnum<=rnum?TRUE:FALSE;
				break ;
			}
			default :
				throw new SefaVMException("Cannot find this operator ,Opcode="+code[pc]);
			}
			registers[left] = res ;
		}
		pc += 3 ;
	}
	
	private void readInArray() {
		if(Opcode.isRegister(code[pc+1]) && Opcode.isRegister(code[pc+2])){
			Object arrObj = registers[Opcode.decodeRegister(code[pc+1])];
			Object indexObj = registers[Opcode.decodeRegister(code[pc+2])];
			if(arrObj instanceof ArrayList<?> && indexObj instanceof Integer){
				registers[Opcode.decodeRegister(code[pc+1])] = ((ArrayList<Object>)arrObj).get((Integer)indexObj) ;
			}
			else{
				throw new SefaVMException("Not a array type or Not a integral index. ");
			}
		}
		else{
			throw new SefaVMException("The instruction of array is unreasonable. ");
		}
	}
	
	private void writeInArray() {
		if(Opcode.isRegister(code[pc+1]) 
				&& Opcode.isRegister(code[pc+2])
				&& Opcode.isRegister(code[pc+3])){
			int addr1 = Opcode.decodeRegister(code[pc+1]);
			int addr2 = Opcode.decodeRegister(code[pc+2]);
			int addr3 = Opcode.decodeRegister(code[pc+3]);
			if(registers[addr1] instanceof ArrayList<?>
					&& registers[addr2] instanceof Integer){
				ArrayList<Object> array = (ArrayList<Object>) registers[addr1];
				int index = (int) registers[addr2];
				array.set(index, registers[addr3]);
			}
		}
	}
}
