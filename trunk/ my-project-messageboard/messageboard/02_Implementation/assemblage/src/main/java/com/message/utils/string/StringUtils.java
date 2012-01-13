package com.message.utils.string;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.message.utils.string.base.BaseStringUtils;

@SuppressWarnings("unchecked")
public class StringUtils extends BaseStringUtils {
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * 判断是否是非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isNotEmpty(str);
	}
	
	/**
	 * 传入的字符串是否在传入的数组中
	 * @param strings
	 * @param string
	 * @return
	 */
	public static boolean contains(String[] strings, String string){
		if(strings == null || strings.length == 0)
			return false;
		if(string == null)
			return false;
		
		for(int i = 0; i < strings.length; i++){
			if(strings[i].equals(string)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 字符串转成base64的
	 * @param baseString
	 * @return
	 */
	public static String encodeBase64(String baseString){
		String result = null;
		
		if(isNotBlank(baseString)){
			result = new String(Base64.encodeBase64(baseString.getBytes()));
		}
		
		return result;
	}
	
	/**
	 * base64的字符串转成正常字符串(解码)
	 * @param baseString
	 * @return
	 */
	public static String decodeBase64(String baseString){
		String result = null;
		
		if(isNotBlank(baseString)){
			result = new String(Base64.decodeBase64(baseString));
		}
		
		return result;
	}
	
	/**
	 * 将list中元素转化成字符串类型。list中值为null的元素将被删除
	 * @param list 要被转化的集合
	 * @return 包含字符串的集合
	 */
	public static List toStringList(List list){
		List result = new ArrayList(list.size());
		Object obj;
		for(int i = 0; i < list.size(); i++){
			obj = list.get(i);
			if(obj != null){
				result.add(obj.toString());
			}
		}
		return result;
	}
	
	
	
}
