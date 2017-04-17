package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.MemberSymbols;
import cn.sefa.symbol.ResizableArrayEnv;
import cn.sefa.symbol.SymbolThis;
import cn.sefa.symbol.Symbols;

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
	
/*	public Object eval(IEnvironment env){
		
		ClassInfo ci = new ClassInfo(this,env);
		env.put(getClassName(),ci) ;
		return getClassName() ;
	}
*/
	
	public void lookup(Symbols sym){
		
	}
	
	public Object eval(IEnvironment env){
		ResizableArrayEnv re = (ResizableArrayEnv)env ;
		Symbols methodNames = new MemberSymbols(re.getSymbols(),
													MemberSymbols.METHOD) ;
		Symbols fieldNames = new MemberSymbols(methodNames,MemberSymbols.FIELD) ;
		OptClassInfo ci = new OptClassInfo(this, env , methodNames , fieldNames) ;
		re.put(getClassName(), ci);
		ArrayList<FuncStmt> methods = new ArrayList<FuncStmt>() ;
		if(ci.getSuperClass() != null){
			ci.getSuperClass().copyTo(fieldNames, methodNames, methods);
		}
		Symbols newSym = new SymbolThis(fieldNames);
		getBody().lookup(newSym,methodNames , fieldNames , methods);
		ci.setMethod(methods);
		return getClassName();
	}
	
	@Override
	public String toString(){
		String parent = getSuperClass();
		if(parent == null){
			parent = " extends "+parent;
		}
		else{
			parent = "";
		}
		return "(class "+getClassName()+parent+getBody()+")" ;
	}
	
}
