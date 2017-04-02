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
abstract class Element {

	protected abstract void parse(Lexer lexer , List<ASTree> res) 
			throws ParseException;
	protected abstract boolean match(Lexer lexer) 
			throws ParseException;
	
}
