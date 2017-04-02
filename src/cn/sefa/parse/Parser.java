/**
 * 
 */
package cn.sefa.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.sefa.ast.ASTLeaf;
import cn.sefa.ast.ASTList;
import cn.sefa.ast.ASTree;
import cn.sefa.ast.IdLeaf;
import cn.sefa.ast.StringLiteral;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;

/**
 * @author Lionel
 *
 */
public class Parser {
	
	protected Factory factory;
	
	protected List<Element> elements; 
	
	public Parser(Class<? extends ASTree> clazz){
		reset(clazz) ;
	}
	
	public Parser(Parser p){
		elements = p.elements;
		factory = p.factory ;
	}
	
	public static Parser rule(){
		
		return rule(null);
	}
	
	public static Parser rule(Class<? extends ASTree> clazz){
		return new Parser(clazz);
	}

	public Parser reset(Class<? extends ASTree> clazz) {
		elements = new ArrayList<Element>() ;
		factory = Factory.getForASTList(clazz);
		return this ;
	}
	
	public Parser reset(){
		elements = new ArrayList<Element>() ;
		return this ;
	}

	public ASTree parse(Lexer lexer) throws ParseException{
		ArrayList<ASTree> results = new ArrayList<ASTree>() ;
		for(Element e : elements)
			e.parse(lexer,results);
		return factory.make(results);
	}

	public boolean match(Lexer lexer) throws ParseException {
		if(elements.size() == 0)
			return true ;
		else{
			Element e = elements.get(0) ;
			return e.match(lexer);
		}
		
	}

	public Parser sep(String... pat) {
		elements.add(new Skip(pat));
		return this;
	}

	public Parser ast(Parser expr) {
		elements.add(new Tree(expr));
		return this;
	}

	public Parser or(Parser... p) {
		elements.add(new OrTree(p));
		return this;
	}
	
	public Parser option(Parser parser) {
		elements.add(new Repeat(parser,true)) ;
		return this;
	}
	
	public Parser repeat(Parser option) {
		elements.add(new Repeat(option,false));
		return this;
	}
	
	public  Parser number(){
		return number(null);
	}
	
	public Parser number(Class<? extends ASTLeaf> clazz) {
		
		elements.add(new NumToken(clazz));
		return this;
	}
	
	public Parser identifier(HashSet<String> r){
		elements.add(new IdToken(null,r)) ;
		return this ;
	}
	
	public Parser identifier(Class<IdLeaf> clazz, HashSet<String> r) {
		elements.add(new IdToken(clazz,r));
		return this;
	}
	
	public Parser string(Class<StringLiteral> clazz) {
		elements.add(new StrToken(clazz));
		return this;
	}
	
	public Parser string (){
		return string(null);
	}
	
	public Parser expression(Parser subexp , Operators operators){
		return expression(null,subexp , operators);
	}
	
	public Parser expression(Class<? extends ASTList> clazz, Parser factor, Operators operators) {
		elements.add(new Expr(clazz,factor,operators)) ;
		return this;
	}

}
