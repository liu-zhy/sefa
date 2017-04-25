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
			if(!(t instanceof NullStmt)){
				if(t instanceof Break)
					return t;
				else if( t instanceof Continue)
					return t;
				res = t.eval(env);
				if(res instanceof Break || res instanceof Continue)
					return res ;
			}
		}
		return res;
	}
	
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
	
	public void setBegin(Code c ,int pos){
		for(ASTree t : this){
			t.setBegin(c , pos);
		}
	}
	
	@Override
	public void setEnd(Code c ,int pos){
		for(ASTree t : this){
			t.setEnd(c ,pos);
		}
	}
}
