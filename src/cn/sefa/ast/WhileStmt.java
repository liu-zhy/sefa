/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class WhileStmt extends ASTList {

	public WhileStmt(List<ASTree> list) {
		super(list);
	}
	
	public ASTree getCondition(){
		return child(0);
	}
	
	public ASTree getBody(){
		return child(1);
	}
	
	@Override
	public String toString(){
		return "(while " + getCondition() +" "	+ getBody() + ")"  ;         
	}
}
