package cn.sefa.ast;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class Continue extends ASTLeaf {

	public Continue(Token t) {
		super(t);
	}
	
	@Override
	public Object eval(IEnvironment env) {
		return this;
	}
}
