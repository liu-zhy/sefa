package cn.sefa.ast;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.NestedEnv;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 *
 */
public class Function {

	protected ParameterList params ;
	protected BlockStmt body ;
	protected IEnvironment env ;
	
	public ParameterList getParams() {
		return params;
	}

	public BlockStmt getBody() {
		return body;
	}

	public Function(ParameterList params, BlockStmt body, IEnvironment env) {
		
		this.params = params;
		this.body = body;
		this.env= env;
	}


	public IEnvironment makeEnv(){
		return new NestedEnv(env);
	}
	
	@Override
	public String toString(){
		return "<function:"+hashCode()+">";
	}

	
}
