/**
 * 
 */
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
public abstract class AToken extends Element {

	protected Factory factory ;
	
	public AToken(Class<? extends ASTLeaf> type){
		if(type == null)
			type = ASTLeaf.class;
		//获得产生ASTLeaf 及其之类的工厂类实例
		factory = Factory.get(type, Token.class) ;
		
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
	public boolean match(Lexer lexer) throws ParseException {
		return test(lexer.peek(0));
	}

	public abstract boolean test(Token peek) ;
		
	
}
