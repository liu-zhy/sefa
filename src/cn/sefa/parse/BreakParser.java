package cn.sefa.parse;

import java.util.List;

import cn.sefa.ast.ASTLeaf;
import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class BreakParser extends AToken{

	public BreakParser(Class<? extends ASTLeaf> type) {
		super(type);
	}
	
	@Override
	public void parse(Lexer lexer, List<ASTree> res) throws ParseException {
		Token t = lexer.read();
		if(test(t)){
			ASTree leaf = factory.make(t);
			res.add(leaf);
		}
		else
			throw new ParseException(t);
	}
	
	@Override
	public boolean test(Token peek) {
		return peek.getText().equals("break");
	}
	

}
