/**
 * 
 */
package cn.sefa.util;

import cn.sefa.lexer.NumToken;
import cn.sefa.lexer.StrToken;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class Debug {

	public static void printToken(Token t){
			
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
	}
	
}
