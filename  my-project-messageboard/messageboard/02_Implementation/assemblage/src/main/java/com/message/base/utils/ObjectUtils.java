package com.message.base.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * object util class
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @createtime 2012-6-26 上午09:44:13
 */
public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {
	public ObjectUtils() {
        super();
    }
	
	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) throws Exception {
		return obj == null;
	}
	
	/**
	 * 判断是否为非空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) throws Exception{
		return !isEmpty(obj);
	}
	
	/**
	 * 获取object的class名(包名+类名)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getClassName(Object obj) throws Exception{
		return isNotEmpty(obj) ? obj.getClass().getName() : StringUtils.EMPTY; 
	}
	
	/**
	 * 获取object的class名(仅类名)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getSimpleClassName(Object obj) throws Exception {
		return isNotEmpty(obj) ? obj.getClass().getSimpleName() : StringUtils.EMPTY;
	}
	
	/**
	 * 获取object的所有方法
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Method[] getMethods(Object obj) throws Exception{
		if(isEmpty(obj)){
			return null;
		}
		
		return obj.getClass().getMethods();
	}
	
	/**
	 * 获取object的所有方法(方法名)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String[] getMethodNames(Object obj) throws Exception{
		Method[] methods = getMethods(obj);
		int len = methods.length;
		if(len < 1){
			return null;
		}
		String[] names = new String[len];
		for(int i = 0; i < len; i++){
			Method m = methods[i];
			names[i] = m.getName();
		}
		
		return names;
	}
	
	/**
	 * 获取object的class
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Class getClazz(Object obj) throws Exception{
		return isEmpty(obj) ? null : obj.getClass();
	}
	
	public static void main(String[] args) throws Exception {
		Map map = new HashMap();
		System.out.println(getClazz(map));
	}
}
