package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 *
 */
public class BlockStmt extends ASTList {

	public BlockStmt(List<ASTree> list) {
		super(list);
	}
	
	@Override
	public Object eval(IEnvironment env){
		Object res = 0 ;
		for(ASTree t : children){
			if(!(t instanceof NullStmt))
				res = t.eval(env);
		}
		return res;
	}
	
	@Override
	public void compile(Code c){
		if(this.numOfChildren() > 0){
			int initReg = c.nextReg;
			for(ASTree t : this){
				c.nextReg = initReg;
				t.compile(c);
			}
		}
		else{
			c.add(Opcode.LOADB);
			c.add((byte)0);
			c.add(Opcode.encodeRegister(c.nextReg++));
		}
	}
	
}
