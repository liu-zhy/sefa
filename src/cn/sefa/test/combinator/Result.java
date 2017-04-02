/**
 * 
 */
package cn.sefa.test.combinator;

/**
 * @author Lionel
 *
 */
public class Result {

	private String recognized ;
	
	private String remaining;
	private boolean succeeded ;
	
	private Result(String recognized , String remaining , boolean succeeded){
		
		this.recognized = recognized;
		this.remaining = remaining;
		this.succeeded = succeeded; 
		
	}

	/**
	 * @return the recognized
	 */
	public String getRecognized() {
		return recognized;
	}

	/**
	 * @param recognized the recognized to set
	 */
	public void setRecognized(String recognized) {
		this.recognized = recognized;
	}

	/**
	 * @return the remaining
	 */
	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}
	
	public static Result succeed(String recognized , String remaining){
		return new Result(recognized,remaining,true);
	}
	
	public static Result fail(){
		return new Result("","",false);
	}
	
	
	
	
	
}
