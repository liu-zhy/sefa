package cn.sefa.symbol;

import java.util.Arrays;

/**
 * @author Lionel
 *
 */
public class ResizableArrayEnv extends ArrayEnv {

	private Symbols names ;
	
	public ResizableArrayEnv() {
		super(10, null);
		names = new Symbols();
	}
	
	public ResizableArrayEnv(IEnvironment env) {
		super(10, env);
		names = new Symbols();
	}
	
	
	@Override
	public Symbols getSymbols(){
		return names;
	}

	@Override
	public Object get(String name){
		Integer i = names.find(name);
		if(i == null)
			if(outer == null)
				return null;
			else
				return outer.get(name);
		return values[i] ;
	}
	
	@Override 
	public void put(String name ,Object value){
		IEnvironment e = where(name);
		if(e == null){
			e = this ;
		}
		e.putInCrtEnv(name, value);
	}
	
	@Override
	public void putInCrtEnv(String name , Object val){
		assign(names.putNew(name),val); 
	}

	@Override
	public IEnvironment where(String name){
		if(names.find(name) != null)
			return this;
		else if(outer == null)
			return null;
		else
			return outer.where(name);
	}
	
	@Override
	public void put(int nest , int index , Object val){
		if(nest == 0)
			assign(index, val);
		else
			super.put(nest, index, val);
	
	}
	
	public void assign(int index , Object val) {
		
		if(index >= values.length){
			int newLen = index + 20 ;
			values = Arrays.copyOf(values,newLen) ;
		}
		values[index] = val;
		
	}
	
}
