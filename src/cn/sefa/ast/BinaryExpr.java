package cn.sefa.ast;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.exception.AccessException;
import cn.sefa.exception.SefaException;
import cn.sefa.symbol.Code;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;
import cn.sefa.vm.Opcode;

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
	
	public void lookup(Symbols sym){
		ASTree left = getLeft();
		if("=".equals(getOperator())){
			if(left instanceof IdLeaf){
				((IdLeaf)left).lookupForAssign(sym);
				getRight().lookup(sym);
				return ;
			}
		}
		left.lookup(sym);
		getRight().lookup(sym);
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
				if(t instanceof OptSefaObject){
					return setField((OptSefaObject)t , (Dot)p.postfix(0),rval);
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
			((IdLeaf)left).evalForAssign(env, rval);;
					
			return rval;
		}

		throw new SefaException("lvalue is not a Identifer",this);	
	}
	
	private Object setField(OptSefaObject so, Dot postfix, Object rval) {
		String name = postfix.getName();
		try {
			so.write(name,rval) ;
			return rval ;
		} catch (AccessException e) {
			throw new SefaException("cannot access :"+name,this);
		}
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
	
	@Override
	public void compile(Code c){
		
		String op = getOperator() ;
		if(op.equals("=")){
			ASTree lval = getLeft() ;
			if(lval instanceof IdLeaf){
				getRight().compile(c);
				((IdLeaf) lval).compileAssign(c);
			}
			else{
				throw new SefaException(" there is an error in assignment. ");
			}
			
		}
		else{
			getLeft().compile(c);
			getRight().compile(c);
			c.add(getOpcode(op));
			c.add(Opcode.encodeRegister(c.nextReg-2));
			c.add(Opcode.encodeRegister(c.nextReg-1));
		}
		
	}

	private byte getOpcode(String op) {
		
		switch(op){
		case "+": return Opcode.ADD;
		case "-": return Opcode.SUB;
		case "*": return Opcode.MUL;
		case "/": return Opcode.DIV;
		case "%": return Opcode.REM;
		case "==": return Opcode.EQUAL;
		case ">": return Opcode.MORE;
		case "<": return Opcode.LESS;
		case "<=": return Opcode.LEQ;
		case ">=": return Opcode.MEQ;
		default: throw new SefaException("Finding a wrong operator in BinaryExpr when compiling") ;
		}
		
	}
	
	
}
