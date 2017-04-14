/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class NullStmt extends ASTList {

	public NullStmt(List<ASTree> list) {
		super(list);
	}
	
	//do nothing.
	@Override
	public Object eval(IEnvironment env){
		return null;
	}
	
}
