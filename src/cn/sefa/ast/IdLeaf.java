/**
 * 
 */
package cn.sefa.ast;

import cn.sefa.exception.SefaException;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class IdLeaf extends ASTLeaf {

	public IdLeaf(Token t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public String getId(){
		return token.getText();
	}
	
	@Override
	public Object eval(IEnvironment env){
		Object obj = env.get(getId());
		if(obj == null){
			throw new SefaException("undefined identifier : "+getId()+". ",this);
		}
		return obj;
	}
	
}
