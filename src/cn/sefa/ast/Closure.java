package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class Closure extends ASTList {

	public Closure(List<ASTree> list) {
		super(list);
	}
	
	public ParameterList getParams(){
		return (ParameterList) child(0);
	}
	
	public BlockStmt getBody(){ 
		return (BlockStmt) child(1);
	}
	
	@Override
	public Object eval(IEnvironment env){
		
		return new Function(getParams(), getBody(), env);
		
	}
	
	@Override
	public String toString(){
		return "(closure "+getParams()+getBody()+")";
	}
	
}
