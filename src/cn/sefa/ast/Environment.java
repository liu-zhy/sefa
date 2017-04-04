package cn.sefa.ast;

import java.util.HashMap;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class Environment implements IEnvironment {

	private HashMap<String , Object> env ;
	
	public Environment() {
		
		this.env = new HashMap<String , Object>();
	}
	
	@Override
	public void put(String name, Object obj) {
		env.put(name, obj);
	}

	@Override
	public Object get(String name) {
		return env.get(name);
	}

}
