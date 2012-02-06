package com.message.base.i18n;

/**
 * 获取系统配置的工具类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class SystemConfig extends MessageUtils {
	private static final String TRUE_STRING = new String("true");
	private static final String FALSE_STRING = new String("false");
	
	/**
	 * 获得布尔型的配置
	 * @param key				the key of property
	 * @param defaultValue		the default value
	 * @return
	 */
	public static boolean getBooleanProperty(String key, boolean defaultValue){
		String resultStr = getMessage(key);
		if(TRUE_STRING.equals(resultStr)){
			return Boolean.TRUE;
		} else if(FALSE_STRING.equals(resultStr)) {
			return Boolean.FALSE;
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * 获得布尔型的配置(default value is <code>false</code>)
	 * @param key				the key of property
	 * @return
	 */
	public static boolean getBooleanProperty(String key){
		return getBooleanProperty(key, Boolean.FALSE);
	}
	
	/**
	 * 获得String型的配置
	 * @param key				the key of property
	 * @param defaultValue		the default value
	 * @return
	 */
	public static String getStringProperty(String key, String defaultValue){
		return getMessage(key, defaultValue);
	}
	
	/**
	 * 获得String型的配置<br/>
	 * if there is no value match the key given,then return <code>null</code>
	 * @param key				the key of property
	 * @return
	 */
	public static String getStringProperty(String key){
		return getStringProperty(key, null);
	}
}
