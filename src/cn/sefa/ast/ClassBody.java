package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 *
 */
public class ClassBody extends ASTList {

	
	public ClassBody(List<ASTree> children) {
		super(children);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object eval(IEnvironment env){
		Object res = null;
		for(ASTree t : children){
			if(!(t instanceof FuncStmt))
				res = t.eval(env);
		}
		return res ;
	}
	public void lookup(Symbols sym, Symbols methodNames, Symbols fieldNames, ArrayList<FuncStmt> methods) {
		for(ASTree t : this){
			if(t instanceof FuncStmt){
				FuncStmt func = (FuncStmt) t ;
				int oldSize = methodNames.size();
				int i= methodNames.putNew(func.getFuncName()) ;
				if(i >= oldSize)
					methods.add(func);
				else
					methods.set(i, func);
				func.lookupAsMethod(fieldNames);
			}
			else
				t.lookup(sym);
			
		}
	}
}
