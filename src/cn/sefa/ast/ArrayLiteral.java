package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class ArrayLiteral extends ASTList {

	public ArrayLiteral(List<ASTree> children) {
		super(children);
	}

	public ArrayList<Object> eval(IEnvironment env){
		
		ArrayList<Object> res = new ArrayList<Object>() ;
		for(ASTree t : children){
			res.add(t.eval(env)) ;
		}
		
		return res;
	}
	
}
