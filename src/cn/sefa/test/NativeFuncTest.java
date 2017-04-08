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
public class NativeFuncTest {

	@Test
	public void test1() throws FileNotFoundException, ParseException{
		
		Lexer lexer = Debug.getLexer("nativeFunc.sf");
		Debug.runTest(lexer);
		
	}
	
}
