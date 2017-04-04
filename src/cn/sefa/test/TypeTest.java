package cn.sefa.test;

import org.junit.Test;

/**
 * @author Lionel
 *
 */
public class TypeTest {

	@Test
	public void test(){
		
		boolean b = false ;
		Object obj = b ;
		if(obj instanceof Boolean){
			if((boolean)obj)
				System.out.println("True");
			else
				System.out.println("False");
		}
		else{
			System.out.println("wrong.");
		}
		
	}
	
}
