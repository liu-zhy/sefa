/**
 * 
 */
package cn.sefa.test.combinator;

/**
 * @author Lionel
 *
 */
public class Item implements Parser {

	@Override
	public Result parse(String target) {
		if(target.length()>0){
			return Result.succeed(target.substring(0,1), target.substring(1));
		}
		return Result.fail();
	}

}
