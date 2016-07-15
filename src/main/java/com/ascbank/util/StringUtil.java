package com.ascbank.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	/**************
	 * 首字母大写
	 */
	public static String capitalize(String str) {
		
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	/**
	 *
	 * Java属性名转数据库表列名
	 *
	 *
	 *
	 * @param propertyName
	 *
	 *            Java属性名
	 *
	 * @return 数据库表列名
	 *
	 */
	public static String convertColumnName(String propertyName) {
		final byte[] buff = propertyName.getBytes();
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buff.length; i++) {
			if (buff[i] >= 65 && buff[i] <= 89) {
				sb.append("_").append((char) buff[i]);
			} else {
				buff[i] -= 32;
				sb.append((char) buff[i]);
			}
		}
		return sb.toString();
	}
	
	/************
	 * 判断字符串是否为纯数字
	 */
	public static boolean isNumeric(String str) {
		final Pattern pattern = Pattern.compile("[0-9]*");
		final Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

}
