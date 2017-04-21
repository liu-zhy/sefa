package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.Code;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.SymbolThis;
import cn.sefa.symbol.Symbols;
import cn.sefa.vm.Opcode;
import cn.sefa.vm.SefaVM;
import cn.sefa.vm.VMFunction;

/**
 * @author Lionel
 *
 */
public class FuncStmt extends ASTList {

	private int index , size ;
	public FuncStmt(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	
	public String getFuncName(){
		return ((ASTLeaf) child(0)).getToken().getText();
	}
	
	public ParameterList getParams(){
		return (ParameterList) child(1);
	}
	
	public BlockStmt getBody(){ 
		return (BlockStmt) child(2);
	}

	public int getSize(){
		return this.size;
	}
	
	@Override
	public String toString(){
		return "(function: "+getFuncName()
		+getParams()+"body:"+getBody() +")";
	}
	
	@Override
	public void lookup(Symbols sym){
		index = sym.putNew(getFuncName());
		size = Closure.lookup(sym,getParams(),getBody());
	}

	@Override
	public Object eval(IEnvironment env){
		
		Code code = env.code();
		int entry = code.begin();
		compile(code);
		env.putInCrtEnv(getFuncName(), 
					new VMFunction(getParams(),getBody(),env,entry));
		return getFuncName();
	}

	public void lookupAsMethod(Symbols sym) {
		Symbols newSym = new Symbols(sym);
		newSym.putNew(SymbolThis.NAME);
		getParams().lookup(newSym);
		getBody().lookup(newSym);
		size = newSym.size();
	}
	
	public void compile(Code c){
		c.nextReg = 0 ;
		c.frameSize = size + SefaVM.SAVE_AREA_SIZE;
		c.add(Opcode.SAVE);
		c.add(Opcode.encodeByteOffset(size));
		getBody().compile(c);
		c.add(Opcode.MOVE);
		c.add(Opcode.encodeRegister(c.nextReg-1));
		c.add(Opcode.encodeByteOffset(0));
		c.add(Opcode.RESTORE);
		c.add(Opcode.encodeByteOffset(size));
		c.add(Opcode.RETURN);
	}
	
	
}
