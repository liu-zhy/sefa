package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.AccessException;
import cn.sefa.exception.SefaException;
import cn.sefa.symbol.ArrayEnv;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 *
 */
public class Dot extends Postfix {

	public Dot(List<ASTree> list) {
		super(list);
	}

	public void lookup(Symbols sym){
		
	}
	
	public String getName(){
		return ((IdLeaf)child(0)).getId();
	}

	@Override
	public Object eval(IEnvironment env, Object target) {
		String member = getName();
		if(target instanceof ClassInfo){
			if("new".equals(member)){
				OptClassInfo ci = (OptClassInfo) target ;
				ArrayEnv newEnv = new ArrayEnv(1,ci.getEnv()) ;
				OptSefaObject so = new OptSefaObject(ci , ci.size()) ;
				newEnv.put(0,0,so);
				initObject(ci , newEnv);  
				return so ;
			}
		}
		else if (target instanceof OptSefaObject){
			try {
				return ((OptSefaObject)target).read(member);
			} catch (AccessException e) {
				
				e.printStackTrace();
			}
		}
		throw new SefaException("cannot find :"+member,this);
	}

	private void initObject(OptClassInfo ci,IEnvironment env){ 
	
		if(ci.getSuperClass() != null){
			initObject(ci.getSuperClass(),env) ;
		}
		ci.getBody().eval(env);
	}
	
}
