/**
 * 
 */
package cn.sefa.lexer;

/**
 * @author Lionel
 *
 */
public class NumToken extends Token {

	private int value  ;
	
	/**
	 * @param lineNumber
	 */
	public NumToken(int value ,int lineNumber) {
		super(lineNumber);
		this.value = value ;
	}

	
	/* (non-Javadoc)
	 * @see cn.sefa.Token#isIdentifier()
	 */
	@Override
	public boolean isIdentifier() {
			
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#isNumber()
	 */
	@Override
	public boolean isNumber() {

		return true;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#isString()
	 */
	@Override
	public boolean isString() {

		return false;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#getNumber()
	 */
	public int getNumber() {

		return value;
	}


	public String getText() {

		return value+"";
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#isEOF()
	 */
	@Override
	public boolean isEOF() {

		return false;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#isEOL()
	 */
	@Override
	public boolean isEOL() {

		return false;
	}

}
