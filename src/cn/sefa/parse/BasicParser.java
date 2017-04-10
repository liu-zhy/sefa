package cn.sefa.parse;

import java.util.HashSet;

import cn.sefa.ast.ASTree;
import cn.sefa.ast.Arguments;
import cn.sefa.ast.BinaryExpr;
import cn.sefa.ast.BlockStmt;
import cn.sefa.ast.ClassBody;
import cn.sefa.ast.ClassStmt;
import cn.sefa.ast.Closure;
import cn.sefa.ast.Dot;
import cn.sefa.ast.FuncStmt;
import cn.sefa.ast.IfStmt;
import cn.sefa.ast.NegativeExpr;
import cn.sefa.ast.NullStmt;
import cn.sefa.ast.ParameterList;
import cn.sefa.ast.PrimaryExpr;
import cn.sefa.ast.WhileStmt;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;

/**
 * 利用组合子构造自顶向下的翻译方案
 * @author Lionel
 *
 */
public class BasicParser {

	HashSet<String> reserved = new HashSet<String>() ;
	Operators operators = Operators.getInstance();
	Parser expr0 = Parser.rule() ;
	Parser args = Parser.rule(Arguments.class).ast(expr0).repeat(Parser.rule().sep(",").ast(expr0));
	Parser postfix = Parser.rule().or(Parser.rule(Dot.class).sep(".").identifier(reserved),
			Parser.rule().sep("(").maybe(args).sep(")")) ;
	
	Parser param = Parser.rule().identifier(reserved);
	
	Parser params = Parser.rule(ParameterList.class).ast(param).repeat(Parser.rule().sep(",").ast(param)) ;
	
	Parser ParamList = Parser.rule().sep("(").maybe(params).sep(")");
	
	Parser statement0 = Parser.rule();
	
	Parser block = Parser.rule(BlockStmt.class)
			.sep("{").option(statement0)
			.repeat(Parser.rule().sep(";",Token.EOL).option(statement0))
			.sep("}");
	
	Parser primary = Parser.rule(PrimaryExpr.class)
			.or(Parser.rule().sep("(").ast(expr0).sep(")") ,
					 Parser.rule().number(),
					 /*
					  * 人为的制造优先级，当closure放到identifier后面时出现二义性，会出现解释错误，因为closure也可以当做标识符
					  * 也可以在reserved里添加closure当做关键字来解决
					  * 
					  * */
					 Parser.rule(Closure.class).sep("closure").ast(ParamList).ast(block),
					 Parser.rule().identifier(reserved),
					 Parser.rule().string())
					.repeat(postfix);
			
	
	Parser factor = Parser.rule().or(Parser.rule(NegativeExpr.class).sep("-").ast(primary)
			,primary) ;
	
	Parser expr = expr0.expression(BinaryExpr.class,factor,operators);
	
	Parser function = Parser.rule(FuncStmt.class).sep("def")
			.identifier(reserved).ast(ParamList).ast(block);

	Parser statement = statement0.or(Parser.rule(IfStmt.class)
			.sep("if").ast(expr).ast(block)
			.option(Parser.rule().option(Parser.rule().sep(";",Token.EOL))
					.sep("else").ast(block)),
			Parser.rule(WhileStmt.class).sep("while").ast(expr).ast(block)	,
			expr);
	
	Parser member = Parser.rule().or(function,expr).repeat(Parser.rule().sep(";",Token.EOL)) ;
	
	Parser classBody = Parser.rule(ClassBody.class).sep("{")
			.option(member).repeat(Parser.rule().repeat(Parser.rule().sep(";",Token.EOL)).ast(member))
			.sep("}");
	
	Parser classStmt = Parser.rule(ClassStmt.class).sep("class")
			.identifier(reserved).option(Parser.rule().sep("extends").identifier(reserved))
			.ast(classBody);

//	Parser program = Parser.rule().option(statement).sep(";",Token.EOL);
	Parser program = Parser.rule()
			.or(classStmt,function,statement,Parser.rule(NullStmt.class)).repeat(Parser.rule().sep(";",Token.EOL));
	
	public BasicParser(){
		//标识符中不包含这些字符
		reserved.add(";");
		reserved.add("}");
		reserved.add(")");
		reserved.add("true");
		reserved.add("false");
		reserved.add("closure");
		reserved.add(Token.EOL);
		
		operators.add("=",1,Operators.RIGHT);
		operators.add("||",1,Operators.LEFT);
		operators.add("&&",2,Operators.LEFT);
		operators.add("==",3,Operators.LEFT);
		operators.add(">",3,Operators.LEFT);
		operators.add(">=",3,Operators.LEFT);
		operators.add("<",3,Operators.LEFT);
		operators.add("<=",3,Operators.LEFT);
		operators.add("+",4,Operators.LEFT);
		operators.add("-",4,Operators.LEFT);
		operators.add("*",5,Operators.LEFT);
		operators.add("/",5,Operators.LEFT);
		operators.add("%",5,Operators.LEFT);
		
		
	}
	public ASTree parse(Lexer lexer) throws ParseException{
		return program.parse(lexer);
	}
	
}
