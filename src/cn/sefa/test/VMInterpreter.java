package cn.sefa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;
import cn.sefa.parse.Natives;
import cn.sefa.symbol.ResizableArrayEnv;
import cn.sefa.symbol.SefaVMEnv;
import cn.sefa.util.Debug;

/**
 * @author Lionel
 *
 */
public class VMInterpreter {

	@Test
	public void test1() throws Exception{
		Lexer lexer = getLexer("function.sf");
		runTest(lexer,false);
	}

	@Test
	public void test2() throws Exception{
		Lexer lexer = getLexer("fib.sf");
		runTest(lexer,false);
	}
	
	@Test
	public void test3() throws Exception{
		Lexer lexer = getLexer("wif.sf");
		runTest(lexer,false);
	}
	
	@Test
	public void test4() throws Exception{
		Lexer lexer = getLexer("strcat.sf");
		runTest(lexer,true);
	}
	@Test
	public void test5() throws Exception{
		Lexer lexer = getLexer("mulfunc.sf");
		runTest(lexer,true);
	}
	
	@Test
	public void test6() throws Exception{
		Lexer lexer = getLexer("break.sf");
		runTest(lexer,true);
	}
	
	@Test
	public void test7() throws Exception{
		Lexer lexer = getLexer("funcInFunc.sf");
		runTest(lexer,true);
	}
	
	@Test
	public void test8() throws Exception{
		Lexer lexer = getLexer("mulWhile.sf");
		runTest(lexer,true);
	}
	
	@Test
	public void test9() throws Exception{
		Lexer lexer = getLexer("whileInFunc.sf");
		runTest(lexer,false);
	}
	
	@Test
	public void test10() throws Exception{
		Lexer lexer = getLexer("mulWhileInFunc.sf");
		runTest(lexer,false);
	}
	
	public static Lexer getLexer(String name) throws FileNotFoundException {
		File file = new File("src/cn/sefa/test/testFile/"+name);
		Reader reader = new InputStreamReader(new FileInputStream(file));
		return new Lexer(reader);
	}

	public static void runTest(Lexer lexer , boolean isDebug) throws ParseException {
		BasicParser bp = new BasicParser();
		ResizableArrayEnv env = new SefaVMEnv(10000,10000000,1000,isDebug);
		Natives nat = new Natives();
		nat.setEnv(env);
		while(lexer.peek(0)!=Token.EOF){
			if(lexer.peek(0)==null){
				System.out.println("null...");
			}
			ASTree t =bp.parse(lexer);
			t.lookup(env.getSymbols());
			if(isDebug){
				System.out.println("->>"+t.eval(env));
				Debug.printCode(env.code(), env.sefaVM());
			}
			else
				t.eval(env);
		}
		//Debug.printCode(env.code(), env.sefaVM());
	}
}
