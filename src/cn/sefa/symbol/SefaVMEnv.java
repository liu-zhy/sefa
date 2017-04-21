package cn.sefa.symbol;

import cn.sefa.vm.HeapMemory;
import cn.sefa.vm.SefaVM;

/**
 * @author Lionel
 *
 */
public class SefaVMEnv extends ResizableArrayEnv implements HeapMemory {

	protected SefaVM svm ;
	protected Code code ;
	
	public SefaVMEnv(int codeSize , int stackSize , int stringsSize){
		svm = new SefaVM(codeSize , stackSize, stringsSize , this);
		code = new Code(svm) ;
	}
	
	public SefaVM getStoneVm(){
		return this.svm ;
	}
	
	public Code getCode(){
		return this.code;
	}
	
	@Override
	public Object read(int addr) {
		return values[addr];
	}

	@Override
	public void write(int addr, Object v) {
		values[addr] = v ;
	}

}
