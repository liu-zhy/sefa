package cn.sefa.vm;

/**
 * @author Lionel
 *
 */
public class Code {

	public int nextReg;
	public int frameSize;
	protected SefaVM svm ;
	protected int codeSize ;
	protected int numOfStrings;
	public Code(SefaVM svm) {
		this.svm = svm;
		codeSize =0;
		numOfStrings = 0;
	}


	public Code add(byte bcode) {
		svm.getCode()[codeSize++] = bcode;
		return this ;
	}

	public Code add(short scode) {
		add((byte)(scode>>>8));
		add((byte)scode);
		return this ;
	}
	public Code add(int icode) {
		add((byte)(icode>>>24));
		add((byte)(icode>>>16));
		add((byte)(icode>>>8));
		add((byte)icode);
		return this;
	}

	public int recode(String s) {
		svm.getStrings()[numOfStrings] = s;
		return numOfStrings++;
	}

	public int position() {
		return codeSize;
	}

	public void set(short value, int pos) {
		svm.getCode()[pos] = (byte)(value>>>8) ;
		svm.getCode()[pos+1] = (byte)value ;
	}

	public int getCodeSize() {
		
		return codeSize;
	}

	
}
