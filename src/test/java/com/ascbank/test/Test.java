package com.ascbank.test;
/**
 *
 */

class A {
	String			a	= "@#$";
	final String[]	as	= { "as" };
	
	public String getA() {
		return a;
	}
	
	public String[] getAs() {
		return as;
	}

	public void setA(String a) {
		this.a = a;
	}
	
}

class B {
	String			b	= "123";
	final String[]	bs	= { "bs" };
	
	public String getB() {
		return b;
	}
	
	public String[] getBs() {
		return bs;
	}

	public void setB(String b) {
		this.b = b;
	}
	
}

/**
 * @author jie
 *
 */
public class Test {
	
	public static void main(String args[]) {
		A a = new A();
		System.out.println(a.getAs()[0]);
		a.getAs()[0] = "100000000000000";
		System.out.println(a.getAs()[0]);
		System.out.println(a.as[0]);
	}

	/*
	 * public static void main(String[] arges) { new Test().regex(); }
	 */
	private final int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	void add() {
		System.out.println(1 + 'l');
		A a = new A();
		B b = new B();
		
		new Test().add(a, b);
		System.out.println(" a = " + a.getA() + "\r\n b= " + b.getB());
	}
	
	void add(A a, B b) {
		String c = a.getA();
		a.setA(b.getB());
		b.setB(c);
	}
	
	void regex() {
		// TODO implement RegexStuff.main
		// String regex = "([\u4e00-\u9fa5]|\\w)+";
		// Pattern pattern = Pattern.compile(regex);
		String regex = "[\\w]+(?:(.\\w*))";
		
		String str1 = "abcF";
		String str2 = "as212.html";
		String str3 = "das你好1d.do";
		String str4 = "34D4H好";
		String str5 = "大家";
		String str6 = "asdasd.do";
		String str7 = "sdfsdf123.5646";
		System.out.println(str1.matches(regex)); // true
		System.out.println(str2.matches(regex)); // true
		System.out.println(str3.matches(regex)); // true
		System.out.println(str4.matches(regex)); // true
		System.out.println(str5.matches(regex)); // true
		System.out.println(str6.matches(regex)); // true
		System.out.println(str7.matches(regex)); // true
		
	}
}