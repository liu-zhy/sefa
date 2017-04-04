package cn.sefa.lexer;

/**
 * @author Lionel
 *
 */
public class IdToken extends Token {

	private String text ;
	
	/**
	 * @param lineNumber
	 */
	public IdToken(String id,int lineNumber) {
		super(lineNumber);
		text = id ;
	}

	/* (non-Javadoc)
	 * @see cn.sefa.Token#isIdentifier()
	 */
	@Override
	public boolean isIdentifier() {

		return true;
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

		return false;
	}

	public String getText() {

		return text;
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

		return text.equals("\\n");
	}

}
