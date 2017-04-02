package cn.sefa.parse;

import java.util.HashSet;

import cn.sefa.ast.ASTLeaf;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class IdToken extends AToken {
	
	HashSet<String> reserved ;
	
	public IdToken(Class<? extends ASTLeaf> type , HashSet<String> reserved) {
		super(type);
		this.reserved = reserved;
	}

	@Override
	public boolean test(Token peek) {
		
		return peek.isIdentifier() && !reserved.contains(peek.getText());
	}

}
