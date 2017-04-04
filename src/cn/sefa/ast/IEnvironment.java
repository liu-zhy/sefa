package cn.sefa.ast;

/**
 * @author Lionel
 *
 */
public interface IEnvironment {

	void put(String name , Object t);
	Object get(String name);
	
}
