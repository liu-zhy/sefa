/**
 * 
 */
package cn.sefa.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.sefa.exception.SefaException;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class ASTLeaf extends ASTree {

	protected Token token ;
	private static final List<ASTree> empty	= new ArrayList<ASTree>();
	public ASTLeaf(Token t){
		token = t ;
	}
	
	@Override
	public ASTree child(int i) {
		throw new IndexOutOfBoundsException()	;
	}
	
	@Override
	public int numOfChildren() {

		return 0;
	}

	@Override
	public Iterator<ASTree> getChildren() {

		return empty.iterator();
	}

	@Override
	public String location() {

		return  "at line " + token.getLineNumber();
	}
	
	public Token getToken(){
		return this.token;
	}

	public String toString(){
		return token.getText() ;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.ast.ASTree#eval()
	 */
	@Override
	public Object eval(IEnvironment env) {
		
		throw new SefaException("can't directly evaluate : "+toString(),this);
		
	}	
	
}
