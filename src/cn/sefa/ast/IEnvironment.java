package cn.sefa.ast;

import java.util.HashMap;

/**
 * @author Lionel
 *
 */
public interface IEnvironment {

	void put(String name , Object obj);
	void putInCrtEnv(String name ,Object obj) ;
	Object get(String name);
	IEnvironment where(String name) ;
	void setOuter(IEnvironment env);
	IEnvironment getOuter();
	HashMap<String , Object> getTable();
	
}
