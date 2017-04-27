/**
 * 
 */
package cn.sefa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import cn.sefa.ast.ASTree;
import cn.sefa.ast.IEnvironment;
import cn.sefa.ast.NestedEnv;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.StrToken;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;
import cn.sefa.parse.Natives;

/**
 * @author Lionel
 *
 */
public class Debug {

	public static void printToken(Token t){
			
		if(t.isEOF()){
			System.out.println("the token is EOF");
		}
		else if(t.isIdentifier()){
			System.out.println("the token is Indentifier :"+t.getText());
		}
		else if(t.isNumber()){
			System.out.println("the token is number :"+((NumToken)t).getNumber());
		}
		else if(t.isString()){
			System.out.println("the token is string :"+((StrToken)t).getText());
		}
		else{
			System.out.println("other token:"+t.getText());
		}
	}
	

	public static void runTest(Lexer lexer , boolean isDebug) throws ParseException {
		BasicParser bp = new BasicParser();
		IEnvironment env = new NestedEnv();
		Natives nat = new Natives();
		nat.setEnv(env);
		while(lexer.peek(0)!=Token.EOF){
			if(lexer.peek(0)==null){
				System.out.println("null...");
			}
			ASTree t =bp.parse(lexer);
			if(isDebug)
				System.out.println("->>"+t.eval(env));
			else
				t.eval(env);
			//t.eval(env);
		}
	}
	public static void runTest(Lexer lexer) throws ParseException {
		runTest(lexer,true);
	}
	
	
	public static Lexer getLexer(String name) throws FileNotFoundException {
		File file = new File("src/cn/sefa/test/testFile/"+name);
		Reader reader = new InputStreamReader(new FileInputStream(file));
		return new Lexer(reader);
	}
	
}
