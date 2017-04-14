package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 *
 */
public class Closure extends ASTList {

	private int size = -1 ;
	public Closure(List<ASTree> list) {
		super(list);
	}
	
	public ParameterList getParams(){
		return (ParameterList) child(0);
	}
	
	public BlockStmt getBody(){ 
		return (BlockStmt) child(1);
	}
	
	@Override
	public void lookup(Symbols sym){
		size = lookup(sym,getParams(),getBody()) ;
	}
	
	public static int lookup(Symbols sym, ParameterList params, BlockStmt body) {
		Symbols newSym = new Symbols(sym);
		params.lookup(newSym);
		body.lookup(newSym);
		return newSym.size();
	}

	@Override
	public Object eval(IEnvironment env){
		
		return new OptFunction(getParams(), getBody(), env, size);
	}
	
	@Override
	public String toString(){
		return "(closure "+getParams()+getBody()+")";
	}
	
}
