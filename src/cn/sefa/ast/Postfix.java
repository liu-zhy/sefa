package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public abstract class Postfix extends ASTList {

	public Postfix(List<ASTree> list) {
		super(list);
	}

	public abstract Object eval(IEnvironment env, Object target) ;
	
}
