package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;

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
	
	@Override
	public void compile(Code c){
		getCondition().compile(c);
		int begin = c.position();
		c.add(Opcode.IFZERO);
		c.add(Opcode.encodeRegister(--c.nextReg));
		c.add(Opcode.encodeShortOffset(0));
		int oldReg = c.nextReg ;
		getThenBlock().compile(c);
		int elsePos = c.position();
		c.add(Opcode.GOTO);
		c.add(Opcode.encodeShortOffset(0));
		c.set(Opcode.encodeShortOffset(c.position()-begin),begin+2);
		ASTree elze = getElseBolck();
		c.nextReg = oldReg;
		if(elze != null )
			elze.compile(c);
		else{
			c.add(Opcode.LOADB);
			c.add((byte)0);
			c.add(Opcode.encodeRegister(c.nextReg));
		}
		c.set(Opcode.encodeShortOffset(c.position()-elsePos) , elsePos+1);
	}
	
	@Override
	public void setBegin(Code c , int pos){
			this.getThenBlock().setBegin(c, pos);
			if(this.getElseBolck() != null)
				this.getElseBolck().setBegin(c, pos);
	}
	
	@Override
	public void setEnd(Code c, int pos){
		this.getThenBlock().setEnd(c, pos);
		if(this.getElseBolck() != null)
			this.getElseBolck().setEnd(c, pos);
	}
}
