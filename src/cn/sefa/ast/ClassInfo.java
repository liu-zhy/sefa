package cn.sefa.ast;

import cn.sefa.exception.SefaException;
import cn.sefa.symbol.IEnvironment;

/**
 * @author Lionel
 *
 */
public class ClassInfo {

	private ClassStmt definition ;
	private IEnvironment env ;
	private ClassInfo superClass;
	
	public ClassInfo(ClassStmt classStmt, IEnvironment env) {
		
		this.definition = classStmt;
		this.env = env;
		Object obj = env.get(classStmt.getSuperClass()) ;
		if(obj == null)
			superClass = null; 
		else if(obj instanceof ClassInfo)
			superClass = (ClassInfo)obj ;
		else 
			throw new SefaException("cannot find the superclass :"
		+classStmt.getSuperClass(),classStmt);
		
	}

	
	public IEnvironment getEnv() {
		
		return this.env;
	}

	public ClassInfo getSuperClass() {
		
		return this.superClass;
	}

	public ASTList getBody() {
		
		return definition.getBody();
	}
	
	public String getClassName(){
		return definition.getClassName();
	}
	
	@Override
	public String toString(){
		return "<Class "+getClassName()+">";
	}

}
