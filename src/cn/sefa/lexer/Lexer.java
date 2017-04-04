/**
 * 
 */
package cn.sefa.lexer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sefa.exception.ParseException;

/**
 * @author Lionel
 *
 */
public class Lexer {

	public static String integerPat = "([0-9]+)";	//匹配整型常量
	/*
	 * 关于\\\\的解释说明
	 * java的字符串字面量里\为转义字符，所以需要\\才能转义为真正的\。
	 * 由于我们需要在正则表达式内部匹配\，而正则表达式里\也是用来转义其他字符的，所以也需要\\来匹配，
	 * 故综上所述，需要\\\\来匹配\
	 */
	public static String blaPat = "\\s*" ;
	public static String strPat = "(\"(\\\\\"|\\\\\\\\|\\n|[^\"])*\")" ;		//匹配字符串字面量 
	public static String idePat = "([a-z_A-Z][a-z_A-Z0-9]*";//匹配标识符
	public static String operPat = "==|<=|>=|&&|\\|\\||\\p{Punct})" ;	//匹配操作符 \p{Punct} 标点符号：!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ 
	public static String regexPat = blaPat+"((//.*)|"+integerPat+"|"
			+strPat+"|"+idePat+"|"+operPat+")?";
	
	private Pattern pattern = Pattern.compile(regexPat);
	private ArrayList<Token> tokenList =  new ArrayList<Token>();
	private boolean hasMore;
	private LineNumberReader reader;
	
	public Lexer(Reader r){
		hasMore = true ;
		reader = new LineNumberReader(r) ;
	}
	//从缓冲区中移出一个token，并将返回此token
	public Token read() throws ParseException{
		
		if(isTokenEnough(0)){
			return tokenList.remove(0);
		}
		
		return Token.EOF;
	}
	//从缓冲区中预取出一个token，但不移出。
	public Token peek(int i) throws ParseException{
		if(isTokenEnough(i)){
			return tokenList.get(i);
		}
		
		return Token.EOF;
	}
	
	private boolean isTokenEnough(int i) throws ParseException {
		
		while(i>=tokenList.size()){
			if(hasMore)
				readLine();
			else
				return false ;
		}
		return true ;
	}

	protected void readLine() throws ParseException{
		String line ;
		try {
			line = reader.readLine();
		} catch (IOException e) {

			throw new ParseException(e) ;
		}
		if(line == null){
			hasMore = false ;
			return ;
		}
		int lineNo = reader.getLineNumber();
		Matcher matcher = pattern.matcher(line);
		/*
		 * useTransparentBounds(true)是将边界透明，参照：
		 * http://guoruisheng-163-com.iteye.com/blog/1593604
		 * 
		 * useAnchoringBounds:如果匹配范围不等于整个目标字符串，可以设定是否将匹配范围的边界设置为"文本起始位置"和“文本结束位置”，
		 * 这会影响文本行边界元字符(\A ^ $ \z \Z)。这个标志位默认为true，参照：
		 * http://blog.csdn.net/claram/article/details/52875925
		 * 
		 */
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0 , endPos = line.length();
//		boolean isComment = true ;
		while(pos<endPos){
			matcher.region(pos, endPos);
			if(matcher.lookingAt()){
//				isComment = addToken(lineNo,matcher);
				addToken(lineNo,matcher);
				pos = matcher.end();
			}
			else{
				throw new ParseException("bad token at line "+lineNo);
			}
		}
		tokenList.add(new IdToken(Token.EOL,lineNo));
		/*if(!isComment)
			tokenList.add(new IdToken(Token.EOL,lineNo));*/
	}
	
	protected void addToken(int lineNo, Matcher matcher) {
		
		if(matcher.group(1)!=null){	//如果匹配的不是空白字符,匹配的是第二个大括号
			if(matcher.group(2) == null){	//如果匹配的是注释不做处理，不是注释的话进入下面的语句
				Token t = null ;
				if(matcher.group(3) != null){	//如果匹配的是整型
					String strNum = matcher.group(3);
					t = new NumToken(Integer.parseInt(strNum),lineNo);
				}
				else if(matcher.group(4)!=null){	//if matcher the literal of string
					t = new  StrToken(toLiteral(matcher.group(4)), lineNo);
				}
				/*else if(matcher.group(5)!=null){	//if matcher the Identifier
					t = new IdToken(matcher.group(5),lineNo);
				}*/
				else {
					t = new IdToken(matcher.group(1),lineNo);
				}
				
				tokenList.add(t);
			}
			/*//如果是注释的话，提醒调用者
			else
				return false;*/
			
		}
//		return true;
		
	}
	/*
	 * 处理字符串字面量中的转义字符
	 */
	protected String toLiteral(String s){
		
		StringBuilder sb = new StringBuilder()	;
		int len = s.length()-1;
		for(int i = 1 ; i < len ;++i){
			char c1 = s.charAt(i) ;
			if(c1 == '\\' && i+1<len){
				char c2 = s.charAt(i+1);
				if(c2 == '\\' || c2 == '"'){
					c1 = c2 ;
					i++;
				}
				else if(c2 == 'n'){
					c1 = '\n';
					i++;
				}
			}
			sb.append(c1);
			
		}
		
		return sb.toString() ;
	}
	
}




