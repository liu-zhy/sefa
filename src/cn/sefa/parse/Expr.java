package cn.sefa.parse;

import java.util.ArrayList;
import java.util.List;

import cn.sefa.ast.ASTLeaf;
import cn.sefa.ast.ASTList;
import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class Expr extends Element {

	private Factory factory ;
	private Operators opers;
	private Parser factor;
	
	/**
	 * @param clazz
	 * @param factor2
	 * @param operators
	 */
	public Expr(Class<? extends ASTList> clazz, Parser factor, Operators operators) {
		
		this.factory = Factory.getForASTList(clazz);
		this.opers = operators;
		this.factor = factor;
		
	}
	
	@Override
	public void parse(Lexer lexer, List<ASTree> res) throws ParseException {
		
		ASTree left = factor.parse(lexer);
		Precedence prec ;
		while((prec = nextOperator(lexer)) != null){
			
			left = doShift(lexer,left,prec.value);
			
		}
		res.add(left);
	}

	//递归的解决运算符优先性
	private ASTree doShift(Lexer lexer, ASTree left, int value) 
			throws ParseException {
		
		ArrayList<ASTree> list = new ArrayList<ASTree>();
		list.add(left);
		list.add((new ASTLeaf(lexer.read())));
		
		ASTree right = factor.parse(lexer);
		Precedence next ;
		while((next = nextOperator(lexer))!=null
				&& rightIsExpr(value,next)){
			right = doShift(lexer,right,value);
		}
		list.add(right) ;
		return factory.make(list);
	}

	private boolean rightIsExpr(int pre , Precedence next) {
		if(next.leftAssoc)
			return pre<next.value;
		return pre<=next.value;
	}

	private  Precedence nextOperator(Lexer lexer) throws ParseException{
		
		Token t = lexer.peek(0);
		if(t.isIdentifier()){
			return opers.get(t.getText());
		}
		else{
			return null ;
		}
		
	}
	
	@Override
	public boolean match(Lexer lexer) throws ParseException {

		return factor.match(lexer);
	}

}
