package cn.sefa.vm;

import cn.sefa.ast.BlockStmt;
import cn.sefa.ast.Function;
import cn.sefa.ast.ParameterList;
import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class VMFunction extends Function{

	protected int entry ;
	
	public VMFunction(ParameterList params , BlockStmt body ,
						IEnvironment env , int entry){
		super(params , body ,env);
		this.entry = entry ;
	}
	
	
	public int entry() {
		return this.entry;
	}

	
	
	public ParameterList getParams() {
		
		return null;
	}

}
