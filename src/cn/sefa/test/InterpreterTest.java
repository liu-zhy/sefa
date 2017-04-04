package cn.sefa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import cn.sefa.ast.ASTree;
import cn.sefa.ast.Environment;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;

/**
 * @author Lionel
 *
 */
public class InterpreterTest {

	@Test
	public void test1() throws FileNotFoundException, ParseException{
		
		Lexer lexer = getLexer();
		BasicParser parser = new BasicParser();
		Environment env = new Environment();
		while(lexer.peek(0)!=Token.EOF){
			if(lexer.peek(0)==null){
				System.out.println("null...");
			}
			ASTree t =parser.parse(lexer);
			System.out.println("->>"+t.eval(env));
		}
		
	}
	private Lexer getLexer() throws FileNotFoundException {
		File file = new File("src/cn/sefa/test/factorial.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		return new Lexer(reader);
	}
	
}
