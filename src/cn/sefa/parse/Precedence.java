/**
 * 
 */
package cn.sefa.parse;

/**
 * @author Lionel
 *
 */
public class Precedence {

	public final int prec ;
	public final boolean leftAssoc ;
	public Precedence(int prec , boolean leftAssoc){
		this.prec = prec ;
		this.leftAssoc = leftAssoc ;
	}

}
