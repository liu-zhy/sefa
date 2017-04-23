/**
 * 
 */
package cn.sefa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.exception.SefaVMException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.StrToken;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;
import cn.sefa.parse.Natives;
import cn.sefa.symbol.ResizableArrayEnv;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;
import cn.sefa.vm.SefaVM;

/**
 * @author Lionel
 *
 */
public class Debug {

	public static void printToken(Token t){
			
		if(t.isEOF()){
			System.out.println("the token is EOF");
		}
		else if(t.isIdentifier()){
			System.out.println("the token is Indentifier :"+t.getText());
		}
		else if(t.isNumber()){
			System.out.println("the token is number :"+((NumToken)t).getNumber());
		}
		else if(t.isString()){
			System.out.println("the token is string :"+((StrToken)t).getText());
		}
		else{
			System.out.println("other token:"+t.getText());
		}
	}
	

	public static void runTest(Lexer lexer , boolean isDebug) throws ParseException {
		BasicParser bp = new BasicParser();
		ResizableArrayEnv env = new ResizableArrayEnv();
		Natives nat = new Natives();
		nat.setEnv(env);
		while(lexer.peek(0)!=Token.EOF){
			if(lexer.peek(0)==null){
				System.out.println("null...");
			}
			ASTree t =bp.parse(lexer);
			t.lookup(env.getSymbols());
			if(isDebug)
				System.out.println("->>"+t.eval(env));
			else
				t.eval(env);
			//t.eval(env);
		}
	}
	
	public static void runTest(Lexer lexer) throws ParseException{
		runTest(lexer , true);
	}
	
	public static Lexer getLexer(String name) throws FileNotFoundException {
		File file = new File("src/cn/sefa/test/testFile/"+name);
		Reader reader = new InputStreamReader(new FileInputStream(file));
		return new Lexer(reader);
	}
	
	public static void printCode(Code code , SefaVM svm){

		byte[] codes = svm.getCode();
		int i = 0 ,len = code.getCodeSize();
		while(i<len){
			switch(codes[i]){
			case Opcode.LOADI :{
				System.out.println("loadi "+readInt(codes,i+1)+" reg"+Opcode.decodeRegister(codes[i+5]));
				i += 6 ;
				break ;
			}
			case Opcode.LOADB :{
				System.out.println("loadb "+readShort(codes,i+1)+" reg"+Opcode.decodeRegister(codes[i+2]));
				i += 3;
				break ;
			}
			case Opcode.LOADS:{
				System.out.println("loads reg"+Opcode.decodeRegister(codes[i+3])+" "+svm.getStrings()[readShort(codes, i)]);
				i+=4;
				break ;
			}
			case Opcode.MOVE:{
				move(i+1,i+2,svm) ;
				i += 3;
				break; 
			}
			case Opcode.GMOVE:{
				/*
				 * src and dest are not all register or heap
				 * src is register ,meanwhile dest is heap , or the opposite.
				 */
				gmove(i,svm);
				i+=4;
				break ;
			}
			case Opcode.IFZERO :{
				System.out.println("ifzero "
							+svm.getRegisters()[Opcode.decodeRegister(codes[i+1])]
									+"("+svm.getRegisters()[Opcode.decodeRegister(codes[i+1])]+") "
										+readShort(codes, i+2));
				i += 4 ;
				break ;
			}
			case Opcode.IFNONZERO :{
				/*Object val = registers[Opcode.decodeRegister(code[i+1])];
				if(val instanceof Integer && ((Integer) val).intValue() != 0){
					i += readShort(code, i+2);
				}
				else{
					i += 4 ;
				}*/
				break ;
			}
			case Opcode.GOTO:{
				System.out.println("goto "+readShort(codes,i+1));
				i+=3;
				break ;
			}
			case Opcode.CALL:{
				System.out.println("call "+svm.getRegisters()[Opcode.decodeRegister(codes[i+1])]);
				i+=3;
				break ;
			}
			case Opcode.RETURN:{
				System.out.println("ret");
				i+=1;
				break ;
			}
			case Opcode.SAVE :{
				System.out.println("save "+codes[i+1]);
				i+=2;
				break;
			}
			case Opcode.RESTORE:{
				System.out.println("restore "+codes[i+1]);
				i += 2 ;
				break ;
			}
			case Opcode.NEG :{
				i += 2 ;
				break ;
			}
			default :{
				if(codes[i]>Opcode.LEQ){
					throw new SefaVMException("the code of instructions is incorrect.");
				}
				else{
					computeNumber(i , svm);
				}
				i+=3;
				break ;
				
			}
			
			}
		}
	}
	
	private static void computeNumber(int pc,SefaVM svm) {
		byte[] code = svm.getCode();
		Object[] registers = svm.getRegisters();
		Object[] stack = svm.getStack();
		StringBuilder sb = new StringBuilder();
		switch(code[pc]){
		case Opcode.ADD:{
			sb.append("add ");
			break ;
		}
		case Opcode.SUB:{
			sb.append("sub ");
			break ;
		}
		case Opcode.MUL:{
			sb.append("mul ");
			break ;
		}
		case Opcode.DIV:{
			sb.append("div ");
			break ;
		}
		case Opcode.REM:{
			sb.append("rem ");
			break ;
		}
		case Opcode.EQUAL:{
			sb.append("equal ");
			break;
		}
		case Opcode.MORE:{
			sb.append("more ");
			break ;
		}
		case Opcode.MEQ:{
			sb.append("meq ");
			break ;
		}
		case Opcode.LESS:{
			sb.append("less ");
			break ;
		}
		case Opcode.LEQ:{
			sb.append("leq");
			break ;
		}
		default :
			throw new SefaVMException("Cannot find this operator ,Opcode="+code[pc]);
		}
		sb.append("reg"+Opcode.decodeRegister(code[pc+1])+" ");
		sb.append("reg"+Opcode.decodeRegister(code[pc+2])+" ");
		System.out.println(sb.toString());
	}


	private static Object readInt(byte[] mem, int i) {
		int res = 0;
		int offset = 2<<8;
		for(int j =i ; j < i+4 ; ++j){
			res = res*offset + mem[j];
		}
		return res;
	}
	
	private static int readShort(byte[] mem, int i) {
		
		return mem[i]<<8 | (mem[i+1]&0xff);
	}
	
private static void move(int srcAddr , int destAddr , SefaVM svm) {
		byte[] code = svm.getCode();
		Object[] registers = svm.getRegisters();
		Object[] stack = svm.getStack();
		byte src = code[srcAddr];
		byte dest = code[destAddr];
		Object value;
		System.out.print("move ");
		if(Opcode.isRegister(src)){
			System.out.print(registers[Opcode.decodeRegister(src)]+" ") ; 
		}
		else if(Opcode.isOffset(src)){
			System.out.print(stack[svm.fp+Opcode.decodeOffset(src)]+" ") ;
		}
		else{
			throw new SefaVMException("the instruction of move decode failure.");
		}
		if(Opcode.isRegister(dest)){
			System.out.print(registers[Opcode.decodeRegister(dest)]+"\n") ; 
		}
		else if(Opcode.isOffset(dest)){
			System.out.print(stack[svm.fp+Opcode.decodeOffset(dest)]+"\n") ;
		}
		else{
			throw new SefaVMException("the instruction of move decode failure.");
		}
		
	}

	private static void gmove(int pc ,SefaVM svm) {
		byte[] code = svm.getCode();
		Object[] registers = svm.getRegisters();
		Object[] stack = svm.getStack();
		
		if(Opcode.isRegister(code[pc+1])){
			if(Opcode.isRegister(code[pc+1])){
				System.out.println("gmove "+"reg"+readShort(code, pc+2)+" "+Opcode.decodeRegister(code[pc+1]));
			}
			else{
				System.out.println("gmove "+"reg"+Opcode.decodeRegister(code[pc+1])+" "+svm.getHeap().read(readShort(code, pc+1)));
			}
		}
	}
}
