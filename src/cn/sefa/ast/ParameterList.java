package cn.sefa.ast;

import java.util.List;

/**
 * @author Lionel
 *
 */
public class ParameterList extends ASTList {

	public ParameterList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	
	public  String getName(int i){
		return ((ASTLeaf)child(i)).getToken().getText();
	}

	public int numOfParams(){
		return this.numOfChildren();
	}
	
	public void eval(IEnvironment env ,int index , Object value){
		env.putInCrtEnv(getName(index), value);
	}
	
}
