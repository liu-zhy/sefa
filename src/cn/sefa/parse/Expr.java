/**
 * 
 */
package cn.sefa.parse;

import java.util.List;

import cn.sefa.ast.ASTList;
import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;

/**
 * @author Lionel
 *
 */
public class Expr extends Element {

	private Factory factory ;
	private Operators opers;
	private Parser factor;
	
	/**
	 * @param clazz
	 * @param factor2
	 * @param operators
	 */
	public Expr(Class<? extends ASTList> clazz, Parser factor, Operators operators) {
		
		
		
	}
	
	@Override
	protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
	}

	@Override
	protected boolean match(Lexer lexer) throws ParseException {

		return false;
	}

}
