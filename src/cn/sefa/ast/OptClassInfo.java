package cn.sefa.ast;

import java.util.ArrayList;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;

/**
 * @author Lionel
 *
 */
public class OptClassInfo extends ClassInfo {

	private Symbols methods , fields ;
	private FuncStmt[] methodDefs ;
	
	public OptClassInfo(ClassStmt classStmt, IEnvironment env,
						Symbols methods , Symbols fields) {
		super(classStmt, env);
		this.methodDefs = null ;
		this.fields = fields;
		this.methods = methods;
	}
	
	public int size(){
		return fields.size();
	}
	
	@Override 
	public OptClassInfo getSuperClass(){
		return (OptClassInfo)superClass;
	}
	
	public void copyTo(Symbols f , Symbols m , ArrayList<FuncStmt> mlist){
		f.append(this.fields);
		m.append(this.methods);
		for(FuncStmt fs : this.methodDefs){
			mlist.add(fs);
		}
	}
	
	public Integer fieldIndex(String name){
		return fields.find(name);
	}
	
	public Integer methodIndex(String name){
		return methods.find(name);
	}
	
	public Object getMethod(OptSefaObject self , int index){
		FuncStmt func = methodDefs[index];
		return new OptMethod(func.getParams() , func.getBody(),getEnv(),
				func.getSize(),self);
	}
	
	public void setMethod(ArrayList<FuncStmt> methods){
		methodDefs = methods.toArray(new FuncStmt[methods.size()]) ;
	}
	
	
	
}
