package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class FuncStmt extends ASTList {

	public FuncStmt(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	
	public String getFuncName(){
		return ((ASTLeaf) child(0)).getToken().getText();
	}
	
	public ParameterList getParams(){
		return (ParameterList) child(1);
	}
	
	public BlockStmt getBody(){ 
		return (BlockStmt) child(2);
	}

	@Override
	public String toString(){
		return "(function: "+getFuncName()
		+getParams()+"body:"+getBody() +")";
	}
	
	@Override
	public Object eval(IEnvironment env){
		
		env.putInCrtEnv(getFuncName(), new Function(getParams(),getBody(),env));
//		System.out.println(env.get("fib"));
		return getFuncName();
	}
	
}
