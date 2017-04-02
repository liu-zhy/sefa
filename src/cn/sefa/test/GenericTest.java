/**
 * 
 */
package cn.sefa.test;

import org.junit.Test;

/**
 * @author Lionel
 *
 */
public class GenericTest {

	@Test
	public void test1(){
		
		f(null);
	}
	private void f(Class<? extends Person>clazz){
		System.out.println("? extends Person");
	}
	/*private void f(Student t){
		System.out.println("Student t");
	}*/
}

class Person{
	public int age ;
	public String name ;
	
	
	public Person(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}
	public Person() {
		super();
	}
	public void print() {
//		System.out.println("age="+age+",name="+name);
		System.out.println("Person");
		
	}
	
}

class Student extends Person{
	public int score = 10;
	public void print(){
		System.out.println("Student");
	}
}

