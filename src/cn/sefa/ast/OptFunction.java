package cn.sefa.ast;

import cn.sefa.symbol.ArrayEnv;
import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class OptFunction extends Function {

	private int size = -1 ; 

	public OptFunction(ParameterList params, BlockStmt body, IEnvironment env) {
		super(params, body, env);
		// TODO Auto-generated constructor stub
	}
	
	public OptFunction(ParameterList params, BlockStmt body, IEnvironment env, int size) {
		super(params, body, env);
		this.size = size ;
	}

	@Override 
	public IEnvironment makeEnv(){
		
		return new ArrayEnv(size,env);
	}
	
}
