package cn.sefa.ast;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class Break extends ASTLeaf {

	public Break(Token t) {
		super(t);
	}
	
	@Override
	public Object eval(IEnvironment env) {
		
		return this;
	}
}
