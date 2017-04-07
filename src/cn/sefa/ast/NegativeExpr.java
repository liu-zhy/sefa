package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;


/**
 * @author Lionel
 *
 */
public class NegativeExpr extends ASTList {

	public NegativeExpr(List<ASTree> list) {
		super(list);
	}
	
	public ASTree operand(){
		return child(0);
	}
	@Override
	public String toString(){
		return "-"+operand();
	}
	
	@Override
	public Object eval(IEnvironment env){
		Object obj = (Integer)(operand().eval(env));
		if(obj instanceof Integer){
			return new Integer(-1*((Integer)obj).intValue());
		}
		else{
			throw new SefaException("here is a - before not expr",this);
		}
		
	}
	
}
