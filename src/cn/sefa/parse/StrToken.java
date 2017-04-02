/**
 * 
 */
package cn.sefa.parse;

import cn.sefa.ast.ASTLeaf;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class StrToken extends AToken {

	public StrToken(Class<? extends ASTLeaf> type) {
		super(type);
	}

	@Override
	public boolean test(Token peek) {
		
		return peek.isString();
	}

}
