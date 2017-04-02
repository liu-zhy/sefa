/**
 * 
 */
package cn.sefa.ast;

import java.util.Iterator;

/**
 * @author Lionel
 *
 */
public abstract class ASTree{

	//���ص�i���ӽ��
	public abstract ASTree child(int i);
	//�����ӽ�����Ŀ
	public abstract int numOfChildren();
	//����һ�����ڱ����ĵ����� 
	public abstract Iterator<ASTree>children();
	public abstract String location();
	

}