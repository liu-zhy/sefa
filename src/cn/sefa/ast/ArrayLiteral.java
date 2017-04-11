package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lionel
 *
 */
public class ArrayLiteral extends ASTList {

	public ArrayLiteral(List<ASTree> children) {
		super(children);
	}

	public ArrayList<Object> eval(IEnvironment env){
		
		int s = numOfChildren();
		ArrayList<Object> res = new ArrayList<Object>() ;
		int i = 0 ;
		for(ASTree t : children){
			res.add(t.eval(env)) ;
		}
		
		return res;
	}
	
}
