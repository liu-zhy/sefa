/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class BinaryExpr extends ASTList {

	public BinaryExpr(List<ASTree> list) {
		super(list);
	}

	public ASTree getLeft(){
		return child(0);
	}
	
	public String getOperator(){
		return ((ASTLeaf)child(1)).getToken().getText();
	}
	
	public ASTree getRight(){
		return child(2);
	}
	
	
}
