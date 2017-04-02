/**
 * 
 */
package cn.sefa.lexer;

/**
 * @author Lionel
 *
 */
public abstract class Token {

	private int lineNumber ;
	
	public static final String EOL = "\\n" ;
	
	public final static Token EOF = new Token(-1) {
		
		@Override
		public boolean isIdentifier() {

			return false;
		}
		@Override
		public boolean isNumber() {

			return false;
		}
		@Override
		public boolean isString() {

			return false;
		}
		@Override
		public boolean isEOF() {

			return true;
		}

		@Override
		public boolean isEOL() {

			return false;
		}

		@Override
		public String getText() {
			
			return "end of file";
		}
	};
	
	protected Token(int lineNumber){
		this.lineNumber = lineNumber ;
	}
	public int getLineNumber() { 
		return lineNumber;
	}
	
	public abstract boolean isIdentifier();
	
	public abstract boolean isNumber();
	
	public abstract boolean isString();

	public abstract String getText();
	
	public abstract boolean isEOF() ;
	
	public abstract boolean isEOL() ;
	
	
}
	


