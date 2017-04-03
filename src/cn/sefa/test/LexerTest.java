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

import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.StrToken;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class LexerTest {

	@Test
	public void test1() throws FileNotFoundException, ParseException{
		File file = new File("src/cn/sefa/test/1.sf");
		Reader reader = new InputStreamReader(new FileInputStream(file));
		Lexer lexer = new Lexer(reader);
		Token t = lexer.read();
		while(t!=Token.EOF){
			
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
			t = lexer.read();
		}
	}
	
	
}
