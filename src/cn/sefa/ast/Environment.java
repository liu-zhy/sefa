package cn.sefa.ast;

import java.util.HashMap;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 * Environment已无用，已升级成NestedEvn
 * 懒得删除此类。
 */
@Deprecated
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

	@Override
	public void putInCrtEnv(String name, Object obj) {
	}

	@Override
	public IEnvironment where(String name) {
		
		return null;
	}

	@Override
	public void setOuter(IEnvironment env) {
	}

	/* (non-Javadoc)
	 * @see cn.sefa.ast.IEnvironment#getOuter()
	 */
	@Override
	public IEnvironment getOuter() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.ast.IEnvironment#getTable()
	 */
	@Override
	public HashMap<String, Object> getTable() {
		
		return null;
	}

}
