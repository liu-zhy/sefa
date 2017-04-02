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
public class Repeat extends Element {

	Parser parser ;
	boolean onlyOnce;
	public Repeat(Parser parser , boolean onlyOnce){		
		this.parser = parser; 
		this.onlyOnce = onlyOnce;
	}
	
	@Override
	protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
		
		while(parser.match(lexer)){
			ASTree t = parser.parse(lexer);
			res.add(t);
			if(onlyOnce)
				break ;
		}
		
	}
	
	@Override
	protected boolean match(Lexer lexer) throws ParseException {
		return parser.match(lexer);
	}

}
