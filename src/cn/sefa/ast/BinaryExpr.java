package cn.sefa.ast;

import java.util.ArrayList;
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
	public Object eval(IEnvironment env){
		
		if("=".equals(getOperator())){
			return computeAssign(env);
		}
		else{
			return computeOp(env);
		}
	}
	
	private Object computeAssign(IEnvironment env) {
		ASTree left = getLeft();
		ASTree right = getRight();
		Object rval = right.eval(env);
		if(left instanceof PrimaryExpr){
			PrimaryExpr p = (PrimaryExpr) left ;
			if(p.hasPostfix(0) 
					&& (p.postfix(0) instanceof Dot
							|| p.postfix(0) instanceof ArrayRef)){
				Object t = ((PrimaryExpr)left).evalSubExpr(env , 1);
				if(t instanceof StoneObject){
					return setField((StoneObject)t , (Dot)p.postfix(0),rval);
				}
				else if(t instanceof ArrayList<?>){
					ArrayList<Object> list = (ArrayList<Object>) t ;
					int index = (int) ((ArrayRef) p.postfix(0)).getIndex().eval(env);
					if(index<0)
						throw new IndexOutOfBoundsException("index cannot equal 0. ");
					for(int i = list.size();index>=list.size(); ++i)
						list.add(0);
					list.set(index , rval);
					return rval;
					
				}
			}

		}
		else if(left instanceof IdLeaf){
			env.put(((IdLeaf)left).getId(), rval);
			return rval;
		}

		throw new SefaException("lvalue is not a Identifer",this);	
		
	}
	
	private Object setField(StoneObject so, Dot postfix, Object rval) {
		String name = postfix.getName();
		so.write(name,rval) ;
		return rval ;
	}

	private Object computeOp(IEnvironment env) {
		Object left = getLeft().eval(env);
		Object right = getRight().eval(env);
		String op = getOperator();
		if(left instanceof Integer && right instanceof Integer){
			return computeNum(left,op,right);
		}
		//处理字符串运算
		else if(left instanceof String || right instanceof String){
			return computerStr(left , op ,right);
			/*if(op.equals("="))
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
			}*/
		}
		else{
			throw new SefaException("left expr and right expr are not same type.",this);
		}

	}

	private Object computerStr(Object left, String op, Object right) {
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
