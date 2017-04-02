/**
 * 
 */
package cn.sefa.parse;

import java.util.List;

import cn.sefa.ast.ASTree;
import cn.sefa.lexer.Token;

/**
 * @author Lionel
 *
 */
public class Skip extends Leaf{

	public Skip(String[] pat) {
		super(pat);
	}

	@Override
	protected void addLeaf(List<ASTree> res , Token t) {
	}


}
