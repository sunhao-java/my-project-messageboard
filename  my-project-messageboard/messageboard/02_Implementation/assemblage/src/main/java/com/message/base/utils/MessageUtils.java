package com.message.base.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 国际化资源文件工具类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-14 下午07:59:37
 */
@SuppressWarnings("rawtypes")
public class MessageUtils {
	private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	
	private static Map configProperties;
	
	public void setProperties(Map properties){
		configProperties = properties;
	}
	
	/**
	 * 获取i18n文件的value
	 * 
	 * @param key			properties中的key
	 * @param defaultValue	取不到值时的默认值
	 * @return
	 */
	public static String getProperties(String key, String defaultValue){
		if(configProperties == null){
			logger.error("the configProperties is null, please check the config xml file is right?");
			return defaultValue;
		}
		String result = (String) configProperties.get(key);
		
		return result == null ? defaultValue : result;
	}
	
	/**
	 * 获取i18n文件的value
	 * 
	 * @param key		properties中的key
	 * @return
	 */
	public static String getProperties(String key){
		return getProperties(key, key);
	}
	
	/**
	 * 获取i18n文件的value(带有{0}{1}...的参数)
	 * 
	 * @param key		properties中的key
	 * @param args		需要替换的参数
	 * @return
	 */
	public static String getProperties(String key, Object[] args){
		return getProperties(key, args, key);
	}
	
	/**
	 * 获取i18n文件的value(带有{0}{1}...的参数)
	 * 
	 * @param key				properties中的key
	 * @param args				需要替换的参数
	 * @param defaultValue		替换失败或者找不到值时，返回的参数
	 * @return
	 */
	public static String getProperties(String key, Object[] args, String defaultValue){
		String result = getProperties(key);
		
		if(StringUtils.isEmpty(result)){
			return defaultValue;
		} else {
			//进行{0}{1}...替换
			if(!(result.indexOf("{") == -1 || result.indexOf("}") == -1)){
				result = ReplaceStringUtils.replace(result, args, defaultValue);
			} else {
				if(logger.isErrorEnabled()){
					logger.error("this key mapped value is not need args!");
				}
				return defaultValue;
			}
		}
		
		return result;
	}
}
