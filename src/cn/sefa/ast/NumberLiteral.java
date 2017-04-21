/**
 * 
 */
package cn.sefa.ast;

import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.Token;
import cn.sefa.symbol.Code;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 *
 */
public class NumberLiteral extends ASTLeaf {

	public NumberLiteral(Token t) {
		super(t);
	}

	public int value(){
		return ((NumToken)token).getNumber();
	}
	
	public Object eval(IEnvironment env){
		return value();
	}

	@Override
	public void compile(Code c) {
		int v = value();
		if(Byte.MIN_VALUE <= v && v <= Byte.MAX_VALUE){
			c.add(Opcode.LOADB);
			c.add((byte)v);
		}
		else{
			c.add(Opcode.LOADI).add(v);
		}
		c.add(Opcode.encodeRegister(c.nextReg++));
	}
}
