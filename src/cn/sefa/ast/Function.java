package cn.sefa.ast;

/**
 * @author Lionel
 *
 */
public class Function {

	private ParameterList params ;
	private BlockStmt body ;
	private IEnvironment env ;
	
	public ParameterList getParams() {
		return params;
	}

	public BlockStmt getBody() {
		return body;
	}

	public Function(ParameterList params, BlockStmt body, IEnvironment env) {
		
		this.params = params;
		this.body = body;
		this.env= env;
	}

	public IEnvironment makeEnv(){
		return new NestedEnv(env);
	}
	
	@Override
	public String toString(){
		return "<function:"+hashCode()+">";
	}
	
}
