/**
 * 
 */
package cn.sefa.exception;

import java.io.IOException;

import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class ParseException extends Exception {
	
	
	public ParseException(Token t){
		this("",t);
	}
	public ParseException(String msg , Token t){
		super("syntax error around "+ location(t)+" . "+msg) ;
	}
	/**
	 * @param t
	 * @return
	 */
	private static String location(Token t) {
		if(t.isEOF()){
			return "the last line";
		}
		else{
			return t.getText()+" at line "+ t.getLineNumber();
		}

	}
	
	public ParseException(IOException e){
		super(e) ;
	}
	public ParseException(String msg){
		super(msg) ;
	}
	
	
}
