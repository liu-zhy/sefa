package cn.sefa.ast;

import java.util.Iterator;
import java.util.List;

import cn.sefa.exception.SefaException;
import cn.sefa.symbol.Code;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel  
 *
 */
public class ASTList extends ASTree {
	
	protected List<ASTree> children;
	public ASTList(List<ASTree> children){
		this.children = children ;
	}
	
	@Override
	public ASTree child(int i) {
		if(children.size()>i)
			return children.get(i);
		return null;
	}

	@Override
	public int numOfChildren() {

		return children.size();
	}

	@Override
	public Iterator<ASTree> getChildren() {

		return children.iterator();
	}

	@Override
	public String location() {
		for(ASTree t : children){
			String s = t.location ();
			if(s!=null)
				return s ;
		}
		
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(ASTree t : children){
			sb.append(" "+t.toString());
			
		}
		return sb.append(")").toString();
	}

	@Override
	public Object eval(IEnvironment env) {
		throw new SefaException("can't directly evaluate : "+toString(),this);
	}

	@Override
	public void lookup(Symbols sym) {
		
		for(ASTree t : this){
			t.lookup(sym);
		}
		
	}
	
	public void compile(Code c){
		for(ASTree t : this){
			t.compile(c);
		}
	}
	@Override
	public Iterator<ASTree> iterator() {
		
		return children.iterator();
	}

}
