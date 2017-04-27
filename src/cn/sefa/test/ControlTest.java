package cn.sefa.test;

import java.io.FileNotFoundException;

import org.junit.Test;

import cn.sefa.lexer.Lexer;
import cn.sefa.util.Debug;

/**
 * @author Lionel
 *
 */
public class ControlTest {

	@Test
	public void test1() throws Exception{
		Lexer lexer = Debug.getLexer("mulWhile.sf");
		Debug.runTest(lexer,false);
	}
	
	@Test
	public void test2() throws Exception{
		Lexer lexer = Debug.getLexer("mulWhileInFunc.sf");
		Debug.runTest(lexer,false);
	}
	
	@Test
	public void test3() throws Exception{
		Lexer lexer = Debug.getLexer("whileInFunc.sf");
		Debug.runTest(lexer,false);
	}
	
	@Test
	public void test4() throws Exception{
		Lexer lexer = Debug.getLexer("arrInFunc.sf");
		Debug.runTest(lexer,false);
	}
	
}
