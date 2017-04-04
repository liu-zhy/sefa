package cn.sefa.ast;

import java.util.List;

import org.hamcrest.core.IsInstanceOf;

/**
 * @author Lionel
 *
 */
public class BlockStmt extends ASTList {

	public BlockStmt(List<ASTree> list) {
		super(list);
	}
	@Override
	public Object eval(Environment env){
		Object res = 0 ;
		for(ASTree t : children){
			if(!(t instanceof NullStmt))
				res = t.eval(env);
		}
		return res;
	}
	
}
