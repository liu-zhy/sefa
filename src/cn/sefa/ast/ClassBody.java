package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class ClassBody extends ASTList {

	
	public ClassBody(List<ASTree> children) {
		super(children);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object eval(IEnvironment env){
		Object res = null;
		for(ASTree t : children){
			res = t.eval(env);
		}
		return res ;
	}
}
