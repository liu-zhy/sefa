package cn.sefa.ast;

import java.rmi.AccessException;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.NestedEnv;

/**
 * @author Lionel
 *
 */
public class SefaObject {

	private IEnvironment env ;
	
	public SefaObject(NestedEnv env) {
		this.env = env ;
	}

	public Object read(String member) throws AccessException {
		return getEnv(member).get(member) ;
		
	}
	
	public IEnvironment getEnv(String member) throws AccessException {
		IEnvironment e = env.where(member);
		if(e != null && e == env)
			return e ;
		throw new AccessException("cannot find :" + member+" in class.");
	}

	public void write(String name, Object rval) {
		
		env.putInCrtEnv(name, rval);
		
	}

}
