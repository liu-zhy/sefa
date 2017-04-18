package cn.sefa.parse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

import cn.sefa.ast.IEnvironment;
import cn.sefa.ast.NativeFunction;
import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class Natives {

	public IEnvironment setEnv(IEnvironment env){
		appendNatives(env);
		return env;
	}

	public void appendNatives(IEnvironment env) {
		append(env,"print" ,"print",Object.class);
		append(env,"read" ,"read");
		append(env,"length" ,"length",Object.class);
		append(env,"toInt" ,"toInt",Object.class);
		append(env,"currentTime" ,"currentTime");
	}
	/*
	 * 将callerName 映射到calledName ,即调用callerName所代表的函数时，转为调用java本地的方法
	 */
	private void append(IEnvironment env, String callerName, String calledName, Class<?>... params) {
		
		Method m ; 
		try {
			m = Natives.class.getMethod(calledName, params) ;
		} catch (Exception e) {
			throw new SefaException("cannot find that native method. ");
		}
		
		env.put(calledName, new NativeFunction(calledName, m));
		
	}
	
	public static int print(Object obj){
		System.out.print(obj);
		return 0;
	}
	
	private static Scanner scan = new Scanner(System.in);
	
	public static String read(){
		return scan.next() ;
//		return JOptionPane.showInputDialog(null);
	}
	
	public static int length(Object s){
		if(s instanceof String)
			return ((String)s).length();
		else if(s instanceof ArrayList<?>)
			return ((ArrayList<?>)s).size();
		throw new IndexOutOfBoundsException();
	}
	
	public static int toInt(Object num){
		if(num instanceof String){
			return Integer.parseInt((String) num);
		}
		else if(num instanceof Integer){
			return ((Integer) num).intValue();
		}
		else
			throw new SefaException("the object cannot convert to number. ");
	}
	
	public static long startTime = System.currentTimeMillis();
	
	public static int currentTime(){
		return (int) (System.currentTimeMillis()-startTime);
	}
	
}
