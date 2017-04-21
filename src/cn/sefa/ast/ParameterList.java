package cn.sefa.ast;

import java.util.List;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;
import cn.sefa.vm.SefaVM;

/**
 * @author Lionel
 *
 */
public class ParameterList extends ASTList {

	private int[] offsets = null ;
	
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
	
	@Override
	public void lookup(Symbols sym){
		int size = numOfParams() ;
		offsets = new int[size] ;
		for(int i = 0 ; i < size ; ++i){
			offsets[i] = sym.putNew(getName(i)) ;
		}
	}
	
	public void eval(IEnvironment env ,int index , Object value){
//		((ArrayEnv)env).put(0 , offsets[index], value);
		SefaVM vm = env.sefaVM();
		vm.getStack()[offsets[index]] = value ;
	}
	 
	
}
