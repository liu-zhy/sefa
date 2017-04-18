package cn.sefa.test;

import java.io.FileNotFoundException;

import org.junit.Test;

import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.util.Debug;

/**
 * @author Lionel
 *
 */
public class OptEnvTest {

	@Test
	public void test1() throws FileNotFoundException, ParseException{
		
		Lexer lexer = Debug.getLexer("fib.sf");
		Debug.runTest(lexer);
		
	}
	
	@Test
	public void test2() throws FileNotFoundException, ParseException{
		Lexer lexer = Debug.getLexer("classFib.sf") ;
		Debug.runTest(lexer,false);
	}
	
}
