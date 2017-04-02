/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class IfStmt extends ASTList {

	public IfStmt(List<ASTree> list) {
		super(list);
	}

	public ASTree getCondition(){
		return child(0) ;
	}
	
	public ASTree getThenBlock(){
		return child(1);
	}
	
	public ASTree getElseBolck(){
		return child(2);
	}
	
	@Override
	public String toString(){
		
		return "(if"+getCondition() + " " + getThenBlock()
			+" else " + getElseBolck();	
	}
	
	
	
}
