/**
 * 
 */
package cn.sefa.exception;

import cn.sefa.ast.ASTree;

/**
 * @author Lionel
 *
 */
public class SefaException extends RuntimeException {
	
	public SefaException(String m){
		super(m);
	}

	public SefaException(String m , ASTree t){
		super(m+t.location());
	}
	
}
