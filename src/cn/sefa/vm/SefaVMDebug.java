package cn.sefa.vm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cn.sefa.ast.ASTList;
import cn.sefa.ast.ASTree;
import cn.sefa.ast.NativeFunction;
import cn.sefa.exception.SefaVMException;

/**
 * @author Lionel
 *
 */
public class SefaVMDebug extends SefaVM{

	protected OutputStreamWriter writer ;
	
	public SefaVMDebug(int codeSize , int stackSize, int stringsSize , HeapMemory hm){
		super(codeSize, stackSize, stringsSize, hm);
		File fout = new File("src/cn/sefa/test/testFile/bytecode.ir");
		if(!fout.exists()){
			try {
				fout.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(fout);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
        this.writer = new OutputStreamWriter(fos);
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
			try {
				mainInLoop() ;
				writer.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	protected void mainInLoop(){		
		switch(code[pc]){
		case Opcode.LOADI :{
			try {
				writer.write("loadi "+readInt(code,pc+1)+" reg"+Opcode.decodeRegister(code[pc+5]));
			} catch (IOException e) {
				e.printStackTrace();
			}
			registers[Opcode.decodeRegister(code[pc+5])] = readInt(code,pc+1) ;
			pc += 6 ;
			break ;
		}
		case Opcode.LOADB :{
			try {
				writer.write("loadb "+(int)code[pc+1]+" reg"+Opcode.decodeRegister(code[pc+2]));
			} catch (IOException e) {
				e.printStackTrace();
			}
			registers[Opcode.decodeRegister(code[pc+2])] = (int)code[pc+1] ; 
			pc += 3;
			break ;
		}
		case Opcode.LOADS:{
			try {
				writer.write("loads reg"+Opcode.decodeRegister(code[pc+3])+" "+strings[readShort(code, pc)]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			registers[Opcode.decodeRegister(code[pc+3])] = strings[readShort(code,pc+1)];
			pc+=4;
			break ;
		}
		case Opcode.MOVE:{
			try {
				move(pc+1,pc+2) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break; 
		}
		case Opcode.GMOVE:{
			/*
			 * src and dest are not all register or heap
			 * src is register ,meanwhile dest is heap , or the opposite.
			 */
			try {
				gmove();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break ;
		}
		case Opcode.IFZERO :{
			int regAddr = Opcode.decodeRegister(code[pc+1]) ;
			Object val = registers[regAddr];
			try {
				writer.write("ifzero reg"+regAddr+"("+val+") "+readShort(code, pc+2));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			try {
				writer.write("goto "+offset);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(offset instanceof Integer){
				pc += ((Integer) offset).intValue();
			}
			else
				throw new SefaVMException("the value of offset is not a number. ");
			break ;
		}
		case Opcode.CALL:{
			try {
				callFunction() ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break ;
		}
		case Opcode.RETURN:{
			try {
				writer.write("ret");
			} catch (IOException e) {
				e.printStackTrace();
			}
			pc = ret ;
			break ;
		}
		case Opcode.SAVE :{
			try {
				writer.write("save "+code[pc+1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveRegs();
			break;
		}
		case Opcode.RESTORE:{
			try {
				writer.write("restore "+code[pc+1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			restoreRegs();
			break ;
		}
		case Opcode.NEG :{
			if(Opcode.isRegister(code[pc+1])){
				int regAddr = Opcode.decodeRegister(code[pc+1]);
				Object val = registers[regAddr];
				try {
					writer.write("neg reg"+Opcode.decodeRegister(code[pc+1]) +"("+registers[regAddr]+")");
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			int reg1 = Opcode.decodeRegister(code[pc+1]);
			int reg2 = Opcode.decodeRegister(code[pc+2]);
			try {
				writer.write("arrayr reg"+reg1+"("+registers[reg1]+")"
						+ " reg"+reg2+"("+registers[reg2]+")");
			} catch (IOException e) {
				e.printStackTrace();
			}
			readArray();
			pc+=3;
			break ;
		}
		case Opcode.ARRAYW:{
			if(!(Opcode.isRegister(code[pc+1]) 
					&& Opcode.isRegister(code[pc+2])
					&& Opcode.isRegister(code[pc+3]))){
				throw new SefaVMException("cannot find the register(s) in ARRAYW");
			}
			int addr1 = Opcode.decodeRegister(code[pc+1]);
			int addr2 = Opcode.decodeRegister(code[pc+2]);
			int addr3 = Opcode.decodeRegister(code[pc+3]);
			try {
				writer.write("arrayw reg"+addr1+"("+registers[addr1]+")"
				+" reg"+addr2+"("+registers[addr2]+")"
				+" reg"+addr3+"("+registers[addr3]+")");
			} catch (IOException e) {
				e.printStackTrace();
			}
			writeInArray();
			pc += 4;
			break ;
		}
		default :{
			if(code[pc]>Opcode.LEQ){
				throw new SefaVMException("the code of instructions is incorrect.");
			}
			else{
				try {
					computeNumber();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break ;
		}
		}
		try {
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
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
	
	private void move(int srcAddr , int destAddr) throws IOException {
		
		byte src = code[srcAddr];
		byte dest = code[destAddr];
		Object value;
		writer.write("move ");
		if(Opcode.isRegister(src)){
			writer.write("reg"+Opcode.decodeRegister(src)+
					"("+registers[Opcode.decodeRegister(src)]+")"+" ");
			value = registers[Opcode.decodeRegister(src)] ; 
		}
		else if(Opcode.isOffset(src)){
			writer.write(Opcode.decodeOffset(src)+"("+stack[fp+Opcode.decodeOffset(src)]+") ") ;
			value = stack[fp+Opcode.decodeOffset(src)] ;
		}
		else{
			throw new SefaVMException("the instruction of move decode failure.");
		}
		
		if(Opcode.isRegister(dest)){
			writer.write("reg"+Opcode.decodeRegister(dest)+
					"("+registers[Opcode.decodeRegister(dest)]+")");
			registers[Opcode.decodeRegister(dest)] = value ;
		}
		else if(Opcode.isOffset(dest)){
			writer.write(Opcode.decodeOffset(dest)+"("+stack[fp+Opcode.decodeOffset(dest)]+")") ;
			stack[fp+Opcode.decodeOffset(dest)] = value ;
		}
		else{
			throw new SefaVMException("the instruction of move decode failure.");
		}
		pc += 3 ;
	}
	
	private void gmove() throws IOException {
		
		if(Opcode.isRegister(code[pc+1])){
			 heap.write(readShort(code, pc+2),registers[Opcode.decodeRegister(code[pc+1])]);
			 writer.write("gmove "+"reg"+
							Opcode.decodeRegister(code[pc+1])+"("+registers[Opcode.decodeRegister(code[pc+1])]+")"+
							" "+readShort(code, pc+2));
		}
		else{
			int addr = Opcode.decodeRegister(code[pc+3]);
			writer.write("gmove "+heap.read(readShort(code, pc+1))
			+" reg"+addr+"("+registers[addr]+")");
			registers[Opcode.decodeRegister(code[pc+3])] = heap.read(readShort(code, pc+1)) ;
		}
		pc += 4 ;
	}
	
	private void callFunction() throws IOException {
		
		writer.write("call "+registers[Opcode.decodeRegister(code[pc+1])]);
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
	
	private void computeNumber() throws IOException {
		int left = Opcode.decodeRegister(code[pc+1]);
		int right = Opcode.decodeRegister(code[pc+2]);
		Object lval = registers[left];
		Object rval = registers[right];
		StringBuilder sb = new StringBuilder();
		boolean allNum = (lval instanceof Integer) && (rval instanceof Integer) ;
		if(code[pc] == Opcode.ADD && !allNum){
			registers[left] = String.valueOf(lval)+String.valueOf(rval);
			sb.append("add "+"reg"+left+"("+registers[left]+")"
					+"reg"+right+"("+registers[right]+")");
		}
		else if(code[pc] == Opcode.EQUAL &&  !allNum){
			if(lval == null)
				registers[left] = rval == null ? TRUE : FALSE ;
			else
				registers[left] = lval.equals(rval)?TRUE:FALSE;
			sb.append("equal reg"+left+" reg"+right) ;
		}
		else{
			if(!allNum){
				String msg ;
				if(!(lval instanceof Integer)){
					msg = "lval is "+lval.getClass().getName();
				}
				else{
					msg = "rval is "+rval.getClass().getName();
				}
				throw new SefaVMException("operands are incompatible. "+msg);
				
			}
			int res ;
			int lnum = ((Integer) lval).intValue();
			int rnum = ((Integer) rval).intValue();
			switch(code[pc]){
			case Opcode.ADD:{
				sb.append("add");
				res = lnum+rnum;
				break ;
			}
			case Opcode.SUB:{
				sb.append("sub");
				res = lnum-rnum ;
				break ;
			}
			case Opcode.MUL:{
				sb.append("mul");
				res = lnum*rnum;
				break ;
			}
			case Opcode.DIV:{
				sb.append("div");
				res = lnum/rnum;
				break ;
			}
			case Opcode.REM:{
				sb.append("rem");
				res = lnum%rnum	;
				break ;
			}
			case Opcode.EQUAL:{
				sb.append("equal");
				res = lnum == rnum ? TRUE : FALSE;
				break;
			}
			case Opcode.MORE:{
				sb.append("more");
				res = lnum>rnum?TRUE:FALSE;
				break ;
			}
			case Opcode.MEQ:{
				sb.append("meq");
				res = lnum>=rnum?TRUE:FALSE;
				break ;
			}
			case Opcode.LESS:{
				sb.append("less");
				res = lnum<rnum?TRUE:FALSE;
				break ;
			}
			case Opcode.LEQ:{
				sb.append("leq");
				res = lnum<=rnum?TRUE:FALSE;
				break ;
			}
			default :
				throw new SefaVMException("Cannot find this operator ,Opcode="+code[pc]);
			}
			sb.append(" reg"+left+"("+registers[left]+")"
					+" reg"+right+"("+registers[right]+")");
			registers[left] = res ;
		}
		writer.write(sb.toString());
		pc += 3 ;
	}
	
	private void readArray() {
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
}
