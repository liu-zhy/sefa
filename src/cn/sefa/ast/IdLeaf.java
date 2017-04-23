package cn.sefa.ast;

import cn.sefa.exception.SefaException;
import cn.sefa.lexer.Token;
import cn.sefa.symbol.ArrayEnv;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Location;
import cn.sefa.symbol.MemberSymbols;
import cn.sefa.symbol.Symbols;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 * 
 */
public class IdLeaf extends ASTLeaf {

	private static final int UNKNOWN = -1 ;
	private int nest , index;
	public IdLeaf(Token t) {
		super(t);
		index = UNKNOWN;
	}

	public String getId(){
		return token.getText();
	}
	
	public void lookup(Symbols sym){
		if("new".equals(getId()))
			return ;
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
		Location pos = sym.put(getId());
		nest = pos.nest;
		index = pos.index;
	}
	
	@Override
	public Object eval(IEnvironment env){
		//我觉得并没有必要判断，保险起见
		if(index == UNKNOWN)
			return env.get(getId());
		else if(nest == MemberSymbols.FIELD)
			return getThis(env).read(index);
		else if(nest == MemberSymbols.METHOD)
			return getThis(env).getMethod(index);
		return ((ArrayEnv)env).get(nest,index);
	}
	
	protected OptSefaObject getThis(IEnvironment env){
		return (OptSefaObject) ((ArrayEnv)env).get(0,0);
	}
	

	public void evalForAssign(IEnvironment env , Object val){
		if(index == UNKNOWN)
			env.put(getId(), val);
		else if(nest == MemberSymbols.FIELD)
			getThis(env).write(index, val);
		else if(nest == MemberSymbols.METHOD)
			throw new SefaException("cannot update this method:"+getId(),this);
		else	
			((ArrayEnv)env).put(nest,index,val);
	}

	@Override
	public void compile(Code c) {
		
		if(nest>0){
			c.add(Opcode.GMOVE);
			c.add(Opcode.encodeShortOffset(index));
			c.add(Opcode.encodeRegister(c.nextReg++)); 
		}
		else{
			c.add(Opcode.MOVE);
			c.add(Opcode.encodeByteOffset(index));
			c.add(Opcode.encodeRegister(c.nextReg++));
		}
		
	}
	
	public void compileAssign(Code c){
		if(nest>0){
			c.add(Opcode.GMOVE);
			c.add(Opcode.encodeRegister(c.nextReg-1));
			c.add(Opcode.encodeShortOffset(index));
		}
		else{
			c.add(Opcode.MOVE);
			c.add(Opcode.encodeRegister(c.nextReg-1));
			c.add(Opcode.encodeByteOffset(index));
		}
	}
	

}
