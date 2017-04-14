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
public class ArrayTest {

	@Test
	public void test() throws ParseException, FileNotFoundException{
		Lexer lexer = Debug.getLexer("array1.sf");
		Debug.runTest(lexer ,false);
	}
	
}
