package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class ClassStmt extends ASTList {

	public ClassStmt(List<ASTree> children) {
		super(children);
		// TODO Auto-generated constructor stub
	}

	public String getClassName(){
		return ((IdLeaf)child(0)).getId();
	}
	
	public String getSuperClass(){
		if(numOfChildren()<3)
			return null;
		return ((IdLeaf)child(1)).getId();
	}
	
	public ClassBody getBody(){
		return (ClassBody) child(numOfChildren()-1);
	}
	
	public Object eval(IEnvironment env){
		
		ClassInfo ci = new ClassInfo(this,env);
		env.put(getClassName(),ci) ;
		return getClassName() ;
	}
	
	@Override
	public String toString(){
		String parent = getSuperClass();
		if(parent == null){
			parent = "extends "+parent;
		}
		else{
			parent = "";
		}
		return "(class "+getClassName()+parent+getBody()+")" ;
	}
	
}
