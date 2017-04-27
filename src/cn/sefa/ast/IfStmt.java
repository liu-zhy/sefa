package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class IfStmt extends ASTList {

	public IfStmt(List<ASTree> list) {
		super(list);
	}

	public ASTree getCondition(){
		return child(0) ;
	}
	
	public ASTree getThenBlock(){
		return child(1);
	}
	
	public ASTree getElseBolck(){
		return child(2);
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("(if"+getCondition() + " " + getThenBlock());
		if(getElseBolck() !=null){
			sb.append(" else " + getElseBolck()) ;
		}
		return sb.toString();	
	}
	
	@Override
	public Object eval(IEnvironment env){
		ASTree t = getCondition() ;
		Object cond = t.eval(env);
		Object res = 0 ;
		if(cond instanceof Boolean){
			if((boolean)cond){
				res = getThenBlock().eval(env);
				if(res instanceof Break || res instanceof Continue)
					return res ;
			}
			else{
				if(getElseBolck() == null)
					return res ;
				res = getElseBolck().eval(env);
				if(res instanceof Break || res instanceof Continue)
					return res ;
			}
		}
		else{
			throw new SefaException("condition is not the type of bool", this);
		}
		
		return res ;
	}
	
}
