package cn.sefa.vm;

/**
 * @author Lionel
 *
 */
public interface HeapMemory {

	Object read(int addr);;
	void write(int addr , Object v);
	
}
