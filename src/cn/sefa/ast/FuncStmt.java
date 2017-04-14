package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.ResizableArrayEnv;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 *
 */
public class FuncStmt extends ASTList {

	private int index , size ;
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
	public void lookup(Symbols sym){
		index = sym.putNew(getFuncName());
		size = Closure.lookup(sym,getParams(),getBody());
	}

	@Override
	public Object eval(IEnvironment env){
		
		((ResizableArrayEnv)env).put(0, index ,
				new OptFunction(getParams(),getBody(),env,size));
//		env.putInCrtEnv(getFuncName(), new Function(getParams(),getBody(),env));
//		System.out.println(env.get("fib"));
		return getFuncName();
	}
	
	
}
