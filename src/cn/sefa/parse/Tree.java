/**
 * 
 */
package cn.sefa.parse;

import java.util.List;

import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;

/**
 * @author Lionel
 *
 */
public class Tree extends Element {

	protected Parser parser ;
	protected Tree(Parser p){
		parser = p ;
	}
	
	@Override
	protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
	
		res.add(parser.parse(lexer)) ;
	}

	@Override
	protected boolean match(Lexer lexer) throws ParseException {

		return parser.match(lexer);
	}
	

}
