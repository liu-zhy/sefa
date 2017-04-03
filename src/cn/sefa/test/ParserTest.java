/**
 * 
 */
package cn.sefa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;

/**
 * @author Lionel
 *
 */
public class ParserTest {

	@Test
	public void test1() throws FileNotFoundException, ParseException{
		File file = new File("src/cn/sefa/test/1.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		Lexer lexer = new Lexer(reader);
		BasicParser bp = new BasicParser();
		
		while(lexer.peek(0)!=Token.EOF){
			ASTree t = bp.parse(lexer);
			System.out.println(t);
		}
		
	}
	
	@Test
	public void test2() throws FileNotFoundException, ParseException{
		//GP1.0/src/cn/sefa/test/1.sf
		File file = new File("src/cn/sefa/test/2.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		Lexer lexer = new Lexer(reader);
		BasicParser bp = new BasicParser();
		
		while(lexer.peek(0)!=Token.EOF){
			ASTree t = bp.parse(lexer);
			System.out.println(t);
		}
		
	}
	@Test
	public void test3() throws FileNotFoundException, ParseException{
		//GP1.0/src/cn/sefa/test/1.sf
		File file = new File("src/cn/sefa/test/3.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		Lexer lexer = new Lexer(reader);
		BasicParser bp = new BasicParser();
		
		while(lexer.peek(0)!=Token.EOF){
			ASTree t = bp.parse(lexer);
			System.out.println(t);
		}
		
	}
	@Test
	public void test4() throws FileNotFoundException, ParseException{
		//GP1.0/src/cn/sefa/test/1.sf
		File file = new File("src/cn/sefa/test/4.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		Lexer lexer = new Lexer(reader);
		BasicParser bp = new BasicParser();
		
		while(lexer.peek(0)!=Token.EOF){
			ASTree t = bp.parse(lexer);
			System.out.println(t);
		}
		
	}
}
