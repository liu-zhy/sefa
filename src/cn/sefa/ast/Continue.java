package cn.sefa.ast;

import cn.sefa.exception.SefaException;
import cn.sefa.lexer.Token;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 *
 */
public class Continue extends ASTLeaf {

	int posOfContinue  = -1;
	
	public Continue(Token t) {
		super(t);
	}
	

	@Override
	public Object eval(IEnvironment env) {
		
		return this;
	}
	
	@Override
	public void compile(Code c){
		posOfContinue = c.position();
		c.add(Opcode.GOTO);
		c.add(Opcode.encodeShortOffset(0));
	}
	
	@Override
	public void setBegin(Code c ,int pos){
		if(pos == -1){
			throw new SefaException("cannot continue. ",this);
		}
		c.set(Opcode.encodeShortOffset(pos-posOfContinue) ,posOfContinue+1);
	}
	
	@Override 
	public void setEnd(Code c ,int pos){
	}



}
