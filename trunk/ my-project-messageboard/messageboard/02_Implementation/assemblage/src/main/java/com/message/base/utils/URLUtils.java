package com.message.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.BitSet;

/**
 * 对url的处理工具类.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-30 下午01:48:01
 */
public class URLUtils {
	/**
	 * character which don't need encoding in url
	 */
	private static BitSet unNeedEncoding;
	
	static {
		unNeedEncoding = new BitSet(256);
		
		int i;
		/**
		 * [a-z],[A-Z],[0-9] in url not need encoding
		 */
		for(i = 'a'; i <= 'z'; i++){
			unNeedEncoding.set(i);
		}
		for(i = 'A'; i <= 'Z'; i++){
			unNeedEncoding.set(i);
		}
		for(i = '0'; i <= '9'; i++){
			unNeedEncoding.set(i);
		}
		
		/**for same special letter*/
		unNeedEncoding.set(' ');
		unNeedEncoding.set('-');
		unNeedEncoding.set('_');
		unNeedEncoding.set('.');
		unNeedEncoding.set('*');

		unNeedEncoding.set('+');
		unNeedEncoding.set('%');
	}
	
	/**
	 * 判断URL是否被encoding
	 * 
	 * @param url		url
	 * @return	true是	false否
	 */
	public static boolean isURLEncoding(String url){
		if(StringUtils.isEmpty(url)){
			return false;
		}
		
		char[] chars = url.toCharArray();
		boolean result = false;
		for(char ch : chars){
			if(Character.isWhitespace(ch)){
				return false;
			}
			if(!unNeedEncoding.get(ch)){
				return false;
			}
			if(ch == '%'){
				result = true;
			}
		}
		
		if(!result){
			return false;
		}
		return true;
	}
	
	/**
	 * encoding URL
	 * 
	 * @param url	url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeURL(String url) throws UnsupportedEncodingException {
		return encodeURL(url, "UTF-8");
	}
	
	/**
	 * encoding url with character encoding named local
	 * 
	 * @param url		url
	 * @param local		character encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeURL(String url, String local) throws UnsupportedEncodingException {
		return URLEncoder.encode(url, local);
	}
	
	/**
	 * decoding URL
	 * 
	 * @param url	url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURL(String url) throws UnsupportedEncodingException {
		return decodeURL(url, "UTF-8");
	}
	
	/**
	 * decoding url with character encoding named local
	 * 
	 * @param url		url
	 * @param local		character encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURL(String url, String local) throws UnsupportedEncodingException {
		return URLDecoder.decode(url, local);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLUtils.encodeURL("http%3A%2F%2Fsunhao.wiscom.com.cn%3A8089%2Fmessage%2Falbum%2FshowDetail.do%3FphotoId%3D101%26albumId%3D41"));
		System.out.println(URLUtils.encodeURL("http://sunhao.wiscom.com.cn:8089/message/album/showDetail.do?photoId=101&albumId=41"));
		System.out.println(URLUtils.isURLEncoding("http%3A%2F%2Fsunhao.wiscom.com.cn%3A8089%2Fmessage%2Falbum%2FshowDetail.do%3FphotoId%3D101%26albumId%3D41"));
		System.out.println(URLUtils.isURLEncoding("http://sunhao.wiscom.com.cn:8089/message/album/showDetail.do?photoId=101&albumId=41"));
	}
}
