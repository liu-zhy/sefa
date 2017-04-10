package cn.sefa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.StrToken;
import cn.sefa.lexer.Token;
import cn.sefa.util.Debug;

/**
 * @author Lionel
 *
 */
public class LexerTest {

	@Test
	public void test1() throws FileNotFoundException, ParseException{
		File file = new File("src/cn/sefa/test/testFile/factorial.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		Lexer lexer = new Lexer(reader);
		Token t = lexer.read();
		while(t!=Token.EOF){
			Debug.printToken(t);
			t = lexer.read();
		}
	}
	
	
}
