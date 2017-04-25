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
public class Break extends ASTLeaf {

	int posOfBreak = -1;
	
	public Break(Token t) {
		super(t);
	}
	
	@Override
	public Object eval(IEnvironment env) {
		
		return this;
	}
	
	@Override
	public void compile(Code c){
		posOfBreak = c.position();
		c.add(Opcode.GOTO);
		c.add(Opcode.encodeShortOffset(0));
	}
	
	@Override
	public void setBegin(Code c ,int pos){
	}
	
	@Override 
	public void setEnd(Code c ,int pos){
		if(posOfBreak == -1){
			throw new SefaException("cannot break",this);
		}
		c.set(Opcode.encodeShortOffset(pos-posOfBreak) ,posOfBreak+1);
	}
}
