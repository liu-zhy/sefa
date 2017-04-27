package cn.sefa.ast;

import java.rmi.AccessException;
import java.util.List;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class Dot extends Postfix {

	public Dot(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public String getName(){
		return ((IdLeaf)child(0)).getId();
	}

	@Override
	public Object eval(IEnvironment env, Object target) {
		String member = getName();
		if(target instanceof ClassInfo){
			if("new".equals(member)){
				ClassInfo ci = (ClassInfo) target ;
				NestedEnv newEnv = new NestedEnv(ci.getEnv()) ;
				initObject(ci,newEnv);  
				SefaObject so = new SefaObject(newEnv);
				newEnv.putInCrtEnv("this",so);
				return so ;
			}
		}
		else if (target instanceof SefaObject){
			
			try{
				return ((SefaObject)target).read(member);
				
			}catch(AccessException e){}
			
		}
		throw new SefaException("cannot find :"+member,this);
	}

	private void initObject(ClassInfo ci, NestedEnv env) {
	
		if(ci.getSuperClass() != null){
			initObject(ci.getSuperClass(),env) ;
		}
		ci.getBody().eval(env);
	}
	
}
