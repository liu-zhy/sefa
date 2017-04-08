package cn.sefa.ast;

import java.lang.reflect.Method;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class NativeFunction {

	protected Method method ;
	protected String name;
	
	public NativeFunction(String name , Method method){
		this.name = name ;
		this.method = method ;
	}
	
	protected int numOfParams(){
		return method.getParameterCount() ;
	}
	
	public Object invoke(Object[] args , ASTree tree){
		
		try {
			return method.invoke(null, args);
		} catch (Exception e) {
			throw new SefaException("There is not native function:"+name+" in here",tree) ;
		}
		
		
	}
	
	@Override
	public String toString(){
		
		return "<native function :"+hashCode()+">"; 
	}
	
}
