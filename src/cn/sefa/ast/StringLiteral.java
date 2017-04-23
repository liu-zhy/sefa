package cn.sefa.ast;

import cn.sefa.lexer.Token;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 *
 */
public class StringLiteral extends ASTLeaf {

	public StringLiteral(Token t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
	public String value(){
		return super.token.getText();
	}

	@Override
	public String toString() {
		return "\""+token.getText()+"\"";
	}
	
	@Override
	public Object eval(IEnvironment env){
		return value();
	}

	@Override
	public void compile(Code c) {
		
		int i = c.recode(value());
		c.add(Opcode.LOADS).add(Opcode.encodeShortOffset(i))
		 .add(Opcode.encodeRegister(c.nextReg++)) ;
		
	}
	

}
