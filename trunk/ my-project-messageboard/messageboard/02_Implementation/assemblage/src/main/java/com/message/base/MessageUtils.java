package com.message.base;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * 国际化资源文件工具类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MessageUtils {
	
	private static MessageSource messageSource = null;
	
	/**
	 * 获取国际化资源MessageSource
	 * @return
	 */
	private static MessageSource getMessageSource(){
		messageSource = (MessageSource) ApplicationContextUtil.getContext().getBean("messageSource");
		return messageSource;
	}
	
	/**
	 * 获取国际化资源
	 * @param code
	 * @param args array of arguments that will be filled in for params within
	 * @param defaultMessage String to return if the lookup fails
	 * @param locale
	 * @return
	 */
	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale){
		if(locale == null){
			locale = Locale.CHINA;
		}
		
		return getMessageSource().getMessage(code, args, defaultMessage, locale);
	}
	
	/**
	 * 获取国际化资源(指定key和locale)
	 * @param key
	 * @param locale
	 * @return
	 */
	public static String getMessage(String key, Locale locale){
		return getMessage(key, null, null, locale);
	}
	
	/**
	 * 获取国际化资源(指定key，locale默认是<code>Locale.CHINA</code>)
	 * @param key
	 * @return
	 */
	public static String getMessage(String key){
		Locale locale = Locale.CHINA;
		return getMessage(key, null, null, locale);
	}
	
	/**
	 * 获取国际化资源(指定key，locale默认是<code>Locale.CHINA</code>，并且如果查询失败返回默认值)
	 * @param key
	 * @param defaultMessage
	 * @return
	 */
	public static String getMessage(String key, String defaultMessage){
		return getMessage(key, null, defaultMessage, null);
	}
	
	/**
	 * 获取国际化资源(指定key和locale，并且如果查询失败返回默认值)
	 * @param key
	 * @param defaultMessage
	 * @return
	 */
	public static String getMessage(String key, String defaultMessage, Locale locale){
		return getMessage(key, null, defaultMessage, locale);
	}
	
	/**
	 * 获取国际化资源(指定code和参数)
	 * @param code
	 * @param args
	 * @return
	 */
	public static String getMessage(String code, Object[] args){
		return getMessage(code, args, null, null);
	}
	
	/**
	 * 获取国际化资源(指定code和参数，并且如果查询失败返回默认值)
	 * @param code
	 * @param args
	 * @return
	 */
	public static String getMessage(String code, Object[] args, String defaultMessage){
		return getMessage(code, args, defaultMessage, null);
	}
	
	/**
	 * 获取国际化资源(指定code和参数、locale)
	 * @param code
	 * @param args
	 * @return
	 */
	public static String getMessage(String code, Object[] args, Locale locale){
		return getMessage(code, args, null, locale);
	}
	
}
