package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.exception.SefaException;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 *
 */
public class ArrayRef extends Postfix {

	public ArrayRef(List<ASTree> list) {
		super(list);
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
	public void compile(Code c){
		getIndex().compile(c);
		c.add(Opcode.ARRAYR);
		c.add(Opcode.encodeRegister(c.nextReg-2));
		c.add(Opcode.encodeRegister(c.nextReg-1));
		c.nextReg-- ;
	}
	
	@Override
	public String toString(){
		return "["+getIndex()+"]" ;
	}

}
