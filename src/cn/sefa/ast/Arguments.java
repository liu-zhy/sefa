package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class Arguments extends Postfix {

	public Arguments(List<ASTree> list) {
		super(list);
	}

	public int numOfArgs(){
		return numOfChildren() ;
	}
	
	public List<ASTree> getArgs(){
		return children;
	}

	@Override
	public Object eval(IEnvironment callerEnv, Object val) {
		if(!(val instanceof Function)){
			throw new SefaException(val+"is not a function",this);
		}
		Function func = (Function)val;
		ParameterList params = func.getParams();
		
		if(numOfArgs() != params.numOfChildren()){
			throw new SefaException("the numbers of argument don't match .",this);
		}
		IEnvironment newEnv = func.makeEnv();
		int num = 0 ;
		for(ASTree t: getArgs()){
			params.eval(newEnv,num++,t.eval(callerEnv));
		}
		
		return func.getBody().eval(newEnv);
	}
	
	
	
}
