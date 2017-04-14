package cn.sefa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import cn.sefa.ast.ASTree;
import cn.sefa.ast.IEnvironment;
import cn.sefa.ast.NestedEnv;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;
import cn.sefa.util.Debug;

/**
 * @author Lionel
 *
 */
public class FunctionTest {

	@Test
	public void test1() throws ParseException, FileNotFoundException{
		Lexer lexer = Debug.getLexer("fib.sf");
		Debug.runTest(lexer , false);
	}
	
	@Test
	public void test2() throws FileNotFoundException, ParseException{
		Lexer lexer = getLexer("strcat.sf");
		runTest(lexer);
	}
	
	@Test
	public void test3() throws FileNotFoundException, ParseException{
		Lexer lexer = getLexer("function.sf");
		runTest(lexer);
	}

	private void runTest(Lexer lexer) throws ParseException {
		BasicParser bp = new BasicParser();
		IEnvironment env = new NestedEnv();
		while(lexer.peek(0)!=Token.EOF){
			if(lexer.peek(0)==null){
				System.out.println("null...");
			}
			ASTree t =bp.parse(lexer);
			System.out.println("->>"+t.eval(env));
		}
	}
	
	
	private Lexer getLexer(String name) throws FileNotFoundException {
		File file = new File("src/cn/sefa/test/testFile/"+name);
		Reader reader = new InputStreamReader(new FileInputStream(file));
		return new Lexer(reader);
	}
	
}
