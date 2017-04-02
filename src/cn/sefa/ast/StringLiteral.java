/**
 * 
 */
package cn.sefa.ast;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class StringLiteral extends ASTLeaf {

	/**
	 * @param t
	 */
	public StringLiteral(Token t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
	public String value(){
		return super.token.getText();
	}

}