/**
 * 
 */
package cn.sefa.ast;

import java.util.Iterator;
import java.util.List;

/**
 * @author Lionel  
 *
 */
public class ASTList extends ASTree {
	
	protected List<ASTree> children;
	public ASTList(List<ASTree> list){
		children = list ;
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
	public Iterator<ASTree> children() {

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
		
		return "ASTList [children=" + children + "]";
	}
	
	
	

}
