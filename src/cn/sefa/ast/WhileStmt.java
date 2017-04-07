/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;

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
	
	@Override
	public Object eval(IEnvironment env){
		ASTree t = getCondition();
		Object cond = t.eval(env);
		Object res = 0 ;
		if(cond instanceof Boolean){
			ASTree block = getBody() ;
			while((boolean)cond){
				res = block.eval(env);
				cond = t.eval(env);
			}
		}
		else{
			throw new SefaException("condition is not the type of bool", this);		
		}
		return res ;
		
	}
	
}
