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
public class Leaf extends Element {

	private String tokens[];
	
	public Leaf(String[] pat){
		tokens = pat ;
	}
						
	@Override
	public void parse(Lexer lexer, List<ASTree> res) throws ParseException {
		
		Token t = lexer.read();
		if(t.isIdentifier()){
			for(String token : tokens){
				if(token.equals((t.getText()))){
					addLeaf(res,t);
					return ;
				}
			}
		}
		if(tokens.length>0){
			throw new ParseException(tokens[0]+" expected.",t);
		}
		else 
			throw new ParseException(t);
		
	}
	
	protected void addLeaf(List<ASTree> res, Token t) {
		res.add(new ASTLeaf(t));
	}

	@Override
	protected boolean match(Lexer lexer) throws ParseException {
		Token t = lexer.peek(0);
		if(t.isIdentifier()){
			for(String token : tokens){
				if(token.equals(t.getText())){
					return true ;
				}
			}
		}
		return false;
	}

}
