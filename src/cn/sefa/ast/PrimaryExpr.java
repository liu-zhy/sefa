/**
 * 
 */
package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class PrimaryExpr extends ASTList {

	public PrimaryExpr(List<ASTree> list) {
		super(list);
	}
	
	public static ASTree create(List<ASTree> c){
		return c.size() == 1 ? c.get(0):new PrimaryExpr(c) ;
	}
	
	public ASTree operand(){
		return child(0);
	}
	
	public boolean hasPostfix(int nest){
		return numOfChildren()-nest>1;
	}
	
	public Postfix postfix(int nest){
		return (Postfix)child(numOfChildren()-nest-1);
	}
	
	public Object eval(IEnvironment env){
		return evalSubExpr(env,0);
	}

	private Object evalSubExpr(IEnvironment env, int nest) {
		if(hasPostfix(nest)){
			Object target = evalSubExpr(env ,nest+1) ;
			return postfix(nest).eval(env,target) ;
		}
		else{
			return operand().eval(env);
		}

	}
	
}
