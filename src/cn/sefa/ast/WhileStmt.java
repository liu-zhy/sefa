/**
 * 
 */
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
public class WhileStmt extends ASTList {

	public WhileStmt(List<ASTree> list) {
		super(list);
	}
	
	public ASTree getCondition(){
		return child(0);
	}
	
	public ASTree getBody(){
		return child(1);
	}
	
	@Override
	public String toString(){
		return "(while " + getCondition() +" "	+ getBody() + ")"  ;         
	}
	
	@Override
	public Object eval(IEnvironment env){
		ASTree t = getCondition();
		Object cond = t.eval(env);
		Object res = 0 ;
		if(cond instanceof Boolean){
			ASTree block = getBody() ;
			while((boolean)cond){
				res = block.eval(env);
				cond = t.eval(env);
			}
		}
		else{
			throw new SefaException("condition is not the type of bool", this);		
		}
		return res ;
	}
	
	@Override
	public void compile(Code c){
		int oldReg = c.nextReg;
		c.add(Opcode.LOADB);
		c.add((byte)0);
		c.add(Opcode.encodeRegister(c.nextReg++));
		int begin = c.position();
		ASTree cond = getCondition();
		cond.compile(c);
		int condOffset = c.position();
		c.add(Opcode.IFZERO);
		c.add(Opcode.encodeRegister(--c.nextReg));
		c.add((short)0);
		c.nextReg = oldReg ;
		getBody().compile(c);
		int endPos = c.position();
		c.add(Opcode.GOTO);
		c.add(Opcode.encodeShortOffset(begin-endPos));
		c.set(Opcode.encodeShortOffset(c.position()-condOffset),condOffset+2);
	}
	
}
