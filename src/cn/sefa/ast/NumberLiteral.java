/**
 * 
 */
package cn.sefa.ast;

import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.Token;

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
}
