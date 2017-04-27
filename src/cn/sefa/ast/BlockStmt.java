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
	public Object eval(IEnvironment env){
		Object res = 0 ;
		for(ASTree t : children){
			if(!(t instanceof NullStmt)){
				if(t instanceof Break)
					return t;
				else if( t instanceof Continue)
					return t;
				res = t.eval(env);
				if(res instanceof Break || res instanceof Continue)
					return res ;
			}
		}
		return res;
	}
	
}
