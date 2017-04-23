package cn.sefa.symbol;

import cn.sefa.vm.Code;
import cn.sefa.vm.HeapMemory;
import cn.sefa.vm.SefaVM;
import cn.sefa.vm.SefaVMDebug;

/**
 * @author Lionel
 *
 */
public class SefaVMEnv extends ResizableArrayEnv implements HeapMemory {

	protected SefaVM svm ;
	protected Code code ;
	
	public SefaVMEnv(int codeSize , int stackSize , int stringsSize , boolean isDebug){
		if(isDebug){
			this.svm = new SefaVMDebug(codeSize , stackSize, stringsSize , this);
		}
		else{
			svm = new SefaVM(codeSize , stackSize, stringsSize , this);
		}
		code = new Code(svm) ;
	}
	
	@Override
	public SefaVM sefaVM(){
		return this.svm ;
	}
	
	@Override
	public Object read(int addr) {
		return values[addr];
	}

	@Override
	public void write(int addr, Object v) {
		values[addr] = v ;
	}
	@Override
	public Code code(){
		return this.code;
	}

}
