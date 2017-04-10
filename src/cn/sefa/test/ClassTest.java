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
public class ClassTest {
	@Test
	public void test() throws FileNotFoundException, ParseException{
		
		Lexer lexer = Debug.getLexer("class1.sf");
		Debug.runTest(lexer);
		
		
	}

}
