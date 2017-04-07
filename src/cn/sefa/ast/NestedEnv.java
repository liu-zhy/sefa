package cn.sefa.ast;

import java.util.HashMap;

/**
 * @author Lionel
 *
 */
public class NestedEnv implements IEnvironment {

	private HashMap<String,Object> values ;
	private IEnvironment outer; 
	
	public NestedEnv(IEnvironment outer) {
		super();
		this.values = new HashMap<String, Object>();
		this.outer = outer;
	}
	
	public NestedEnv() {
		super();
		this.values = new HashMap<String, Object>();
		this.outer = null;
	}
	

	@Override
	public void setOuter(IEnvironment env) {
		this.outer = (NestedEnv) env;
	}
	
	
	@Override
	public IEnvironment getOuter(){
		return outer;
	}
	public HashMap<String,Object> getValues(){
		return values;
	}
	
	@Override
	public void put(String name, Object v) {
		IEnvironment env = where(name) ;
		if(env != null){
			env.putInCrtEnv(name, v) ;
		}
		else
			putInCrtEnv(name, v);
		
	}

	//不管外部环境存不存在这个变量，只将这个变量加入当前环境(put key in current environment)
	public void putInCrtEnv(String name, Object v){
		values.put(name, v);
	}
	
	@Override
	public Object get(String name) {

		Object res = values.get(name);
		if(res == null && outer != null)
			return outer.get(name);
		
		return res;
	}
	
	public IEnvironment where(String name){
		
		if(values.get(name) != null){
			return this;
		}
		if(outer != null){
			return outer.where(name) ;			
		}	
		return null;
	}


}
