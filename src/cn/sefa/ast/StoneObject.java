package cn.sefa.ast;

import java.rmi.AccessException;

/**
 * @author Lionel
 *
 */
public class StoneObject {

	private IEnvironment env ;
	
	public StoneObject(NestedEnv env) {
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
