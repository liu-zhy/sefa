package cn.sefa.ast;

import java.util.Iterator;

/**
 * @author Lionel
 *
 */
public abstract class ASTree{

	//返回第i个子结点
	public abstract ASTree child(int i);
	//返回子结点的数目
	public abstract int numOfChildren();
	//返回一个便于遍历的迭代器 
	public abstract Iterator<ASTree>getChildren();
	public abstract String location();
	public abstract Object eval(IEnvironment env);

}
