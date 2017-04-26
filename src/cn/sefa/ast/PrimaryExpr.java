package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;

/**
 * @author Lionel
 *
 */
public class PrimaryExpr extends ASTList {

	public PrimaryExpr(List<ASTree> list) {
		super(list);
	}
	
	public static ASTree create(List<ASTree> c){
		return c.size() == 1 ? c.get(0):new PrimaryExpr(c) ;
	}
	
	public ASTree operand(){
		return child(0);
	}
	//嵌套数从0开始
	public boolean hasPostfix(int nest){
		return numOfChildren()-nest>1;
	}
	
	public Postfix postfix(int nest){
		return (Postfix)child(numOfChildren()-nest-1);
	}
	
	public Object eval(IEnvironment env){
		return evalSubExpr(env,0);
	}

	public Object evalSubExpr(IEnvironment env, int nest) {
		if(hasPostfix(nest)){
			Object target = evalSubExpr(env ,nest+1) ;
			return postfix(nest).eval(env,target) ;
		}
		else{
			return operand().eval(env);
		}
	}
	
	@Override
	public void compile(Code c){
		compileSubExpr(c,0);
	}

	public void compileSubExpr(Code c, int nest) {
		if(hasPostfix(nest)){
			compileSubExpr(c,nest+1);
			postfix(nest).compile(c);
		}
		else{
			operand().compile(c);
		}
	}
	
}
