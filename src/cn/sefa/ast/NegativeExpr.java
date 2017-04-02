/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class NegativeExpr extends ASTList {

	public NegativeExpr(List<ASTree> list) {
		super(list);
	}
	
	public ASTree operand(){
		return child(0);
	}
	
	public String toString(){
		return "-"+operand();
	}
	
}
