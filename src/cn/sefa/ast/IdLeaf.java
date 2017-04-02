/**
 * 
 */
package cn.sefa.ast;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class IdLeaf extends ASTLeaf {

	/**
	 * @param t
	 */
	public IdLeaf(Token t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public String getId(){
		return token.getText();
	}
}