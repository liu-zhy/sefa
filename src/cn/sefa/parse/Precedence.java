/**
 * 
 */
package cn.sefa.parse;

/**
 * @author Lionel
 *
 */
public class Precedence {

	public final int value ;
	public final boolean leftAssoc ;
	public Precedence(int value , boolean leftAssoc){
		this.value = value ;
		this.leftAssoc = leftAssoc ;
	}

}
