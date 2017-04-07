/**
 * 
 */
package cn.sefa.parse;

import java.util.List;

import javax.swing.text.AbstractDocument.LeafElement;

import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.util.Debug;

/**
 * @author Lionel
 *
 */
public class OrTree extends Element{
	
	protected Parser[] parsers ;
	public OrTree(Parser[] p){
		parsers = p ;
	}
	public void parse(Lexer lexer , List<ASTree> res)
			throws ParseException {
		
		Parser p = choose(lexer);
//		Debug.printToken(lexer.peek(0));
		if(p == null){
			throw new ParseException(lexer.peek(0));
		}
		else{
			res.add(p.parse(lexer));
		}
		
	}
	
	protected Parser choose(Lexer lexer) throws ParseException {
		for(Parser p : parsers){
			if(p.match(lexer)){
				return p ;
			}
		}
		return null;
	}
	
	@Override
	public boolean match(Lexer lexer) throws ParseException {
		
		return choose(lexer)!=null ;
	}
	
	public void insert(Parser p){
		
		Parser[] newParsers = new Parser[parsers.length+1] ;
		newParsers[0] = p ;
		System.arraycopy(parsers,0 , newParsers,1,parsers.length) ;
		parsers = newParsers ;
		
	}
	
	
	
}
