/**
 * 
 */
package cn.sefa.test.combinator;

import org.junit.Test;

/**
 * @author Lionel
 *
 */
public class Combinator {

	@Test
	public void test(){
		
		Parser parser = new SAT(new IsDigit() , new Item());
		Result  r = parser.parse("1123");
		System.out.println(r.getRecognized());
	}
	
}
