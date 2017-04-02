package cn.sefa.parse;

import java.util.HashSet;

import cn.sefa.ast.ASTree;
import cn.sefa.ast.BinaryExpr;
import cn.sefa.ast.BlockStmt;
import cn.sefa.ast.IdLeaf;
import cn.sefa.ast.IfStmt;
import cn.sefa.ast.NegativeExpr;
import cn.sefa.ast.NullStmt;
import cn.sefa.ast.NumberLiteral;
import cn.sefa.ast.PrimaryExpr;
import cn.sefa.ast.StringLiteral;
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
	Parser primary = Parser.rule()
			.or(Parser.rule().sep("(").ast(expr0).sep(")") ,
				Parser.rule().number(NumberLiteral.class),
				Parser.rule().identifier(IdLeaf.class , reserved),
				Parser.rule().string(StringLiteral.class));
	
	Parser factor = Parser.rule().or(Parser.rule(NegativeExpr.class).sep("-").ast(primary)
										,primary) ;
	
	Parser expr = expr0.expression(BinaryExpr.class,factor,operators);
	
	Parser statement0 = Parser.rule();
	
	Parser block = Parser.rule(BlockStmt.class)
			.sep("{").option(statement0)
			.repeat(Parser.rule().sep(";",Token.EOL).option(statement0))
			.sep("}");
	
	Parser statement = statement0.or(Parser.rule(IfStmt.class)
			.sep("if").ast(expr).ast(block)
			.option(Parser.rule().sep("else").ast(block)),
			Parser.rule(WhileStmt.class).sep("while").ast(expr).ast(block)	,
			expr);
	
	
//	Parser program = Parser.rule().option(statement).sep(";",Token.EOL);
	Parser program = Parser.rule()
			.or(statement,Parser.rule(NullStmt.class)).sep(";",Token.EOL) ;
	
	public BasicParser(){
		//标识符中不包含这些字符
		reserved.add(";");
		reserved.add("}");
		reserved.add(Token.EOL);

		operators.add("=",1,Operators.RIGHT);
		operators.add("==",2,Operators.LEFT);
		operators.add(">",2,Operators.LEFT);
		operators.add("<",2,Operators.LEFT);
		operators.add("+",3,Operators.LEFT);
		operators.add("-",3,Operators.LEFT);
		operators.add("*",4,Operators.LEFT);
		operators.add("/",4,Operators.LEFT);
		operators.add("%",4,Operators.LEFT);
		
		
	}
	public ASTree parse(Lexer lexer) throws ParseException{
		return program.parse(lexer);
	}
	
}
