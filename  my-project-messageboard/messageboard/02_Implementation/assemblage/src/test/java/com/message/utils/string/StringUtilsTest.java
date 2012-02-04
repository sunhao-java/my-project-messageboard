package com.message.utils.string;

import com.message.utils.MD5Utils;
import com.message.utils.StringUtils;


public class StringUtilsTest {

	public static void main(String[] args) {
//		String str = BaseStringUtils.replaceString("sunhao", "sun", "asdas", 10);
//		System.out.println(str);
//		String[] argss = BaseStringUtils.split("1,1,1,1", ",");
//		System.out.println(argss.toString());
//		String str1 = StringUtils.decodeBase64("c3VuaGFv");
//		String str2 = StringUtils.encodeBase64("sunhao");
//		System.out.println(str1);
//		System.out.println(str2);
//		String username = "sunhao0550@sina.com.cn";
//		String domain = StringUtils.getBetweenTwoLetters(username, "@", ".");
//		System.out.println(domain);
		
		StringBuffer sb = new StringBuffer();
		String username_md5 = MD5Utils.MD5Encode("sunhao1");
		sb.append("请点击下面的链接验证用户！").append("<br/>");
		sb.append("<a href='").append("http://sunhao.wiscom.com.cn:8089/message/user/emailConfirm.do?")
		.append("usernameMD5Code=").append(username_md5).append("&userid=").append(41);
		sb.append("'>").append("点击这里").append("</a>");
		sb.append("<br/>");
		sb.append("如果没有成功，请复制下面链接到浏览器地址栏！").append("<br/>");
		sb.append("http://sunhao.wiscom.com.cn:8089/message/user/emailConfirm.do?")
		.append("usernameMD5Code=").append(username_md5).append("&userid=").append(41);
		
		System.out.println(sb.toString());
		
	}

}
