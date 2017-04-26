package cn.sefa.test;

import cn.sefa.exception.SefaException;
import cn.sefa.vm.SefaVM;

/**
 * @author Lionel
 *
 */
public enum EnumTest {

	LOADI , //load an integer
	LOADB , // load an integer of one byte
	LOADS , //load a string
	/*
	 * reg to reg
	 * reg to stack
	 * stack to stack
	 * stack to reg 
	 * 
	 */
	MOVE ,
	/*
	 * GMOVE src dest
	 * reg to heap
	 * heap to reg 
	 * 
	 */
	GMOVE , //move a global value. 
	ARRAY , //get the element of array
	IFZERO ,
	IFNONZERO ,
	GOTO ,
	CALL ,
	RETURN ,
	SAVE ,	   //save all value of register to stack
	RESTORE , //restore the value of all registers from stack
	ADD ,
	SUB ,
	MUL ,
	DIV ,
	REM ,	//remainder
	NEG ,
	EQUAL ,
	MORE ,
	LESS ,
	MEQ ,  //more or euqal
	LEQ ; //less or equal
	
	public static byte encodeRegister(int reg){
		if(reg>SefaVM.NUM_OF_REG)
			throw new SefaException("the number of reg has exceeded. ");
		else
			return (byte)(-(reg+1));
	}
	
	public static int decodeRegister(byte operand){
		return -1-operand ;
	}
	
	public static byte encodeByteOffset(int offset){
		if(offset>Byte.MAX_VALUE)
			throw new SefaException("byte offset has exceeded the limits.");
		else
			return (byte)offset;
	}
	
	public static short encodeShortOffset(int offset){
		if(offset<Short.MIN_VALUE || Short.MAX_VALUE < offset)
			throw new SefaException("short offset has exceeded.");
		else
			return (short)offset ;
	}
	
	public static int decodeOffset(byte operand){
		return operand;
	}
	public static boolean isRegister(byte operand){
		return operand < 0;
	}
	public static boolean isOffset(byte operand){
		return operand >= 0;
	}

}
