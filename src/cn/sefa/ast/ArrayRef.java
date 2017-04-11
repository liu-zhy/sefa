package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class ArrayRef extends Postfix {

	public ArrayRef(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public ASTree getIndex(){
		return child(0);
	}
	
	@Override
	public Object eval(IEnvironment env, Object target) {
		
		if(target instanceof ArrayList<?>){
			Object index = getIndex().eval(env);
			if(index instanceof Integer){
				ArrayList<?> list = (ArrayList<?> )target ;
				if((int)index>=0 && (int)index<list.size())
					return list.get((int) index);
				return null;
			}
		}
		throw new SefaException("not a array type",this) ;
		
	}
	
	@Override
	public String toString(){
		return "["+getIndex()+"]" ;
	}

}
