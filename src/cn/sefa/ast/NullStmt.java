/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

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
	public Object eval(Environment env){
		return null;
	}
	
}
