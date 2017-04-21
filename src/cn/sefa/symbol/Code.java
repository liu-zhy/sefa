package cn.sefa.symbol;

import cn.sefa.vm.SefaVM;

/**
 * @author Lionel
 *
 */
public class Code {

	public int nextReg;
	public int frameSize;

	public Code(SefaVM svm) {
		// TODO Auto-generated constructor stub
	}

	public int begin() {
		
		return 0;
	}

	public Code add(byte c) {
		
		return this ;
	}

	public Code add(int v) {
	
		return this;
	}

	/**
	 * @param value
	 * @return
	 */
	public int recode(String value) {
		
		return 0;
	}

	
}
