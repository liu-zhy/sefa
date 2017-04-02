/**
 * 
 */
package cn.sefa.test;

import org.junit.Test;

/**
 * @author Lionel
 *
 */
public class InnerClassTest {

	protected int d = 4 ;
	protected class A{
		
		public int a = 1;
		protected int b = 2 ;
		private int c = 3;
		public void f(){
			System.out.println("Inner:"+d);
		}
		
	}
	
	@Test
	public void test(){
		
		A a = new A();
		System.out.println(a.a);
		System.out.println(a.b);
		System.out.println(a.c);
		a.f();
		
	}
	
}
