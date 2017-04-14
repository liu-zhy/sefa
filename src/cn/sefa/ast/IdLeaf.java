package cn.sefa.ast;

import cn.sefa.exception.SefaException;
import cn.sefa.lexer.Token;
import cn.sefa.symbol.ArrayEnv;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Location;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 * 
 */
public class IdLeaf extends ASTLeaf {

	private static final int UNKNOWN = -1 ;
	private int nest , index ;
	public IdLeaf(Token t) {
		super(t);
		index = UNKNOWN;
	}

	public String getId(){
		return token.getText();
	}
	
	public void lookup(Symbols sym){
		Location loc = sym.get(getId());
		if(loc == null){
			throw new SefaException("undefined name: "+getId()+", ",this);
		}
		else{
			this.nest = loc.nest;
			this.index = loc.index;
		}
	}
	
	public void lookupForAssign(Symbols sym){
		Location loc = sym.put(getId());
		this.nest = loc.nest;
		this.index = loc.index;
	}
	
	@Override
	public Object eval(IEnvironment env){
		//我觉得并没有必要判断，保险起见
		if(index == UNKNOWN)
			return env.get(getId());
		else
			return ((ArrayEnv)env).get(nest,index);
	}
	
	public void evalForAssign(IEnvironment env , Object val){
		if(index == UNKNOWN)
			env.put(getId(), val);
		else
			((ArrayEnv)env).put(nest,index,val);
	}
	
}
