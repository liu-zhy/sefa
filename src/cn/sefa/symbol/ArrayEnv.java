package cn.sefa.symbol;

import cn.sefa.exception.SefaException;
import cn.sefa.vm.SefaVM;

/**
 * @author Lionel
 *
 */
public class ArrayEnv implements IEnvironment {

	protected Object[] values;
	protected IEnvironment outer ;
	
	public ArrayEnv(int size , IEnvironment env){
		this.values = new Object[size];
		this.outer = env ;
	}
	
	public Symbols getSymbols(){
		throw new SefaException("no symbols");
	}
	
	public Object get(int nest , int index){
		if(nest == 0)
			return values[index];
		else if(outer == null)
			return null ;
		else
			return ((ArrayEnv)outer).get(nest-1,index);
	}
	
	public void put(int nest , int index , Object val){
		if(nest == 0)
			values[index] = val;
		else if(outer == null)
			throw new SefaException("without outer env , cannot find the symbol. ");
		else
			((ArrayEnv)outer).put(nest-1, index, val);
	}
	
	@Override
	public void put(String name, Object obj) {
		error(name);
	}

	@Override
	public void putInCrtEnv(String name, Object obj) {
		error(name);
	}

	@Override
	public Object get(String name) {
		
		error(name);
		return null;
	}

	@Override
	public IEnvironment where(String name) {
		error(name);
		return null;
	}

	@Override
	public void setOuter(IEnvironment env) {
		this.outer = env;
	}

	@Override
	public IEnvironment getOuter() {

		return outer;
	}

	public Object[] getValues() {
		
		return this.values;
	}
	
	private void error(String name) {
		throw new SefaException("cannot access by name : "+name);
	}

	/* (non-Javadoc)
	 * @see cn.sefa.symbol.IEnvironment#sefaVM()
	 */
	@Override
	public SefaVM sefaVM() {
		
		return null;
	}

	@Override
	public Code code() {
		
		return null;
	}

}
