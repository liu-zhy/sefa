package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class BinaryExpr extends ASTList {

	public BinaryExpr(List<ASTree> list) {
		super(list);
	}

	public ASTree getLeft(){
		return child(0);
	}
	
	public String getOperator(){
		return ((ASTLeaf)child(1)).getToken().getText();
	}
	
	public ASTree getRight(){
		return child(2);
	}
	
	@Override 
	public Object eval(Environment env){
		
		if("=".equals(getOperator())){
			return computeAssign(env);
		}
		else{
			return computeOp(env);
		}
	}
	
	private Object computeAssign(Environment env) {
		ASTree left = getLeft();
		if(left instanceof IdLeaf){
			ASTree right = getRight();
			Object obj = right.eval(env);
			
			env.put(((IdLeaf)left).getId(), obj);
			return obj;
		}
		else{
			throw new SefaException("lvalue is not a Identifer",this);
		}
		
	}
	
	private Object computeOp(Environment env) {
		Object left = getLeft().eval(env);
		Object right = getRight().eval(env);
		String op = getOperator();
		if(left instanceof Integer && right instanceof Integer){
			return computeNum(left,op,right);
		}
		//处理字符串运算
		else if(left instanceof String || right instanceof String){
			if(op.equals("="))
				return String.valueOf(left)+String.valueOf(right);
			else if(op.equals("=="))
			{
				if(left != null && right != null){
					return left.equals(right) ;
				}
				else
					return left == right ? true : false ;
			}
			//字符串连接操作。
			else if(op.equals("+")){
				StringBuilder sb = new StringBuilder();
				sb.append(left).append(right);
				return sb.toString();
			}
		}
		else{
			throw new SefaException("left expr and right expr are not same type.",this);
		}
		return null;
	}

	private Object computeNum(Object left, String op, Object right) {
		int a = (int)left;
		int b = (int)right ;
		if(op.equals("+")){
			return a+b;
		}
		else if(op.equals("-")){
			return a-b ;
		}
		else if(op.equals("*")){
			return a*b;
		}
		else if(op.equals("/")){
			return a/b;
		}
		else if(op.equals("%")){
			return a%b;
		}
		else if(op.equals("==")){
			return a==b;
		}
		else if(op.equals("<")){
			return a<b;
		}
		else if(op.equals("<=")){
			return a<=b;
		}
		else if(op.equals(">")){
			return a>b;
		}
		else if(op.equals(">=")){
			return a>=b;
		}
		else
			throw new SefaException("wrong operator:"+op,this);
	}
	
	
}
