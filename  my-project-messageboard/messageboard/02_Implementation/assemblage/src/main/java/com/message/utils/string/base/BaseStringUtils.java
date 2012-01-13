package com.message.utils.string.base;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class BaseStringUtils extends org.apache.commons.lang.StringUtils {
	
	public BaseStringUtils(){
	}
	
	/**
	 * 有待研究
	 * @param strcontent
	 * @param oldstr
	 * @param newstr
	 * @param len
	 * @return
	 */
	public static String replaceString(String strcontent, String oldstr,
			String newstr, int len){
		StringBuffer buffer = new StringBuffer();
		int pos = 0;
		int i = 0;
		for (i = strcontent.indexOf(oldstr, pos); i >= 0; i = strcontent
				.indexOf(oldstr, pos)) {
			buffer.append(strcontent.substring(pos, i));
			buffer.append(newstr);
			pos = i + len;
		}

		buffer.append(strcontent.substring(pos));
		return buffer.toString();
	}
	
	/**
	 * 按传入的字符分隔字符串
	 * @param str
	 * @param character
	 * @return
	 */
	public static String[] split(String str, String character){
		StringTokenizer stk = new StringTokenizer(str, character);
		List list = new ArrayList();
		for (; stk.hasMoreTokens(); list.add(stk.nextToken()))
			;
		String strs[] = new String[list.size()];
		list.toArray(strs);
		return strs;
	}
	
	/**
	 * 传入的字符串是否在传入的数组中
	 * @param string
	 * @param strings 
	 * @return
	 */
	public static boolean contain(String string, String strings[]) {
		if (strings == null)
			return false;
		for (int i = 0; i < strings.length; i++) {
			String s = strings[i];
			if (string.equals(s))
				return true;
		}

		return false;
	}
	
	/**
	 * list转成字符串数组
	 * @param list
	 * @return
	 */
	public static String[] convert(List list) {
		if (list == null)
			return null;
		String ss[] = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			ss[i] = (String) list.get(i);

		return ss;
	}
}
