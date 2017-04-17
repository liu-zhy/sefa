package cn.sefa.ast;

import cn.sefa.symbol.ArrayEnv;
import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class OptMethod extends OptFunction {

	OptSefaObject self ;
	
	public OptMethod(ParameterList params, BlockStmt body, 
						IEnvironment env, int size, OptSefaObject self) {
		
		super(params, body, env ,size);
		this.self = self ;
	}
	
	@Override
	public IEnvironment makeEnv(){
		ArrayEnv e = new ArrayEnv(size,env) ;
		e.put(0,0,self) ;
		return  e ;
	}

}
