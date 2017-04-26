package cn.sefa.test;

import java.math.BigDecimal;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.junit.Test;

/**
 * @author Lionel
 *
 */

enum Color{
	RED,GREEN,BLANK,YELLO
}

public class OtherTest {

	@Test
	public void scanTest(){
		Scanner scan = new Scanner(System.in);
		System.out.println(scan.next());
		scan.close();
	}
	
	@Test
	public void test2(){
		
		String str = JOptionPane.showInputDialog(null) ;
		System.out.println(str);
		
	}
	
	@Test
	public void test3(){
		System.out.println(Color.RED);
	}
	
	public Color f(){
		return Color.RED;
	}
	
	@Test
	public void test4(){
		BigDecimal d = new BigDecimal("12.302000");
		System.out.println(d.toString());
		
	}
	
	@Test
	public void test5(){
		System.out.println(Color.values()[1]);
	}
	
}
