/**
 *
 */

class A {
	String a = "@#$";

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

}

class B {
	String b = "123";

	public String getB() {
		return b;
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
		int[] s;
		int i;
		Test t = new Test();
		
		t.arr[3] = 100;
		
		s = new int[5];
		for (i = 0; i < 5; i++) {
			s[i] = i;
		}
		// s[3] = 10;
		for (i = 8; i >= 0; i--) {
			System.out.println("" + t.arr[i]);
		}
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