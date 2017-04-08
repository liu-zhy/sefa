package cn.sefa.test;

import java.util.Scanner;

import javax.swing.JOptionPane;

import org.junit.Test;

/**
 * @author Lionel
 *
 */
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

}
