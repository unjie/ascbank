/**
 *
 */
package com.ascbank.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author jie
 *
 */
public class Regular {
	
	@Test
	public void test() {
		String str = "/ascbank/user/index.jsp";
		
		String pattern = "([\\w\\/]+)*";
		
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		
		// 现在创建 matcher 对象
		Matcher m = r.matcher(str);
		if (m.find()) {
			System.out.println("0 value: " + m.group(0));
			System.out.println("1 value: " + m.group(1));
			System.out.println("2 value: " + m.group(2));
		} else {
			System.out.println("NO MATCH");
		}
	}
	
}
