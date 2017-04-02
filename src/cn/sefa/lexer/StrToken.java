/**
 * 
 */
package cn.sefa.lexer;

/**
 * @author Lionel
 *
 */
public class StrToken extends Token {

	public String literal;
	/**
	 * @param lineNumber
	 */
	public StrToken(String literal ,int lineNumber) {
		super(lineNumber);
		this.literal = literal ;

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

		return false;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#isString()
	 */
	@Override
	public boolean isString() {

		return true;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#getText()
	 */
	@Override
	public String getText() {

		return this.literal;
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
