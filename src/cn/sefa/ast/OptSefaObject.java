package cn.sefa.ast;

import cn.sefa.exception.AccessException;
import cn.sefa.symbol.NestedEnv;

/**
 * @author Lionel
 *
 */
public class OptSefaObject{

	private OptClassInfo classInfo ;
	private Object[] fields ;
	
	public OptSefaObject(OptClassInfo ci , int size) {
		classInfo = ci ;
		fields = new Object[size];
	}
	
	public OptClassInfo classInfo(){
		return classInfo ;
	}
	
	public Object read(String name) throws AccessException{
		Integer i = classInfo.fieldIndex(name);
		if(i != null){
			return fields[i];
		}
		else{
			i = classInfo.methodIndex(name);
			if(i != null)
				return getMethod(i);
		}
		throw new AccessException();
	}
	
	public void write(String name,Object val) throws AccessException{
		Integer i = classInfo.fieldIndex(name);
		if(i == null)
			throw new AccessException();
		else
			fields[i] = val ;
	}
	
	public Object read(int index){
		return fields[index] ;
	}
	
	public void write(int index , Object val){
		fields[index] = val ;
	}
	
	public Object getMethod(int i) {
		return classInfo.getMethod(this, i);
	}

}
