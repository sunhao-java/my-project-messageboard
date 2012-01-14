package com.message.utils.string;

import com.message.utils.StringUtils;


public class StringUtilsTest {

	public static void main(String[] args) {
//		String str = BaseStringUtils.replaceString("sunhao", "sun", "asdas", 10);
//		System.out.println(str);
//		String[] argss = BaseStringUtils.split("1,1,1,1", ",");
//		System.out.println(argss.toString());
		String str1 = StringUtils.decodeBase64("c3VuaGFv");
		String str2 = StringUtils.encodeBase64("sunhao");
		System.out.println(str1);
		System.out.println(str2);
	}

}
