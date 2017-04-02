/**
 * 
 */
package cn.sefa.parse;

import java.util.HashMap;

/**
 * @author Lionel
 *
 */
public class Operators extends HashMap<String , Precedence>{

	private static final Operators oper = new Operators();
	public static boolean LEFT = true ;
	public static boolean RIGHT = false ;
	public void add(String name , int prec , boolean leftAssoc){
		put(name,new Precedence(prec,leftAssoc));
		
	}
	private Operators(){
		super();
	}
	public static Operators getInstance(){
		return oper;
	}
}
