package com.message.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.MethodInvoker;

/**
 * object util class
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @createtime 2012-6-26 上午09:44:13
 */
public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {
	/**
	 * 默认方法名前缀
	 */
	private static final String DEFAULT_METHOD_PREFIX = "get";
	
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
	
	/**
	 * 获取对象中的所有字段
	 * getFields()与getDeclaredFields()区别:
	 * getFields()只能访问类中声明为公有的字段,私有的字段它无法访问.
	 * getDeclaredFields()能访问类中所有的字段,与public,private,protect无关 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Field[] getFields(Object obj) throws Exception {
		if(isEmpty(obj)){
			return null;
		}
		
		return obj.getClass().getDeclaredFields();
	}
	
	/**
	 * 获取对象中的所有字段(字段名)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String[] getFieldNames(Object obj) throws Exception {
		Field[] fields = getFields(obj);
		int len = fields.length;
		if(len < 1){
			return null;
		}
		String[] f = new String[len];
		for(int i = 0; i < len; i++){
			Field field = fields[i];
			f[i] = field.getName();
		}
		
		return f;
	}
	
	/**
	 * 通过反射根据字段名和前缀取得字段的值
	 * 
	 * @param obj				对象
	 * @param fieldName			字段名
	 * @param prefix			前缀
	 * @return
	 * @throws Exception
	 */
	public static Object getValue(Object obj, String fieldName, String prefix) throws Exception{
		String methodName = createMethodName(fieldName, prefix);
		
		return getValue(obj, methodName);
	}
	
	/**
	 * 通过反射根据字段名取得字段的值(前缀默认为"get")
	 * 
	 * @param fieldName			字段名
	 * @param obj				对象
	 * @return
	 * @throws Exception
	 */
	public static Object getValue(String fieldName, Object obj) throws Exception{
		return getValue(obj, fieldName, DEFAULT_METHOD_PREFIX);
	}
	
	/**
	 * 通过反射根据方法名取得字段的值
	 * 
	 * @param obj				对象
	 * @param methodName		方法名
	 * @return
	 * @throws Exception
	 */
	public static Object getValue(Object obj, String methodName) throws Exception {
		if(obj == null || StringUtils.isEmpty(methodName)){
			return null;
		}
		
		MethodInvoker methodInvoker = new MethodInvoker();
        methodInvoker.setTargetClass(getClazz(obj));
        //下来可以自己手工设置方法参数
        methodInvoker.setTargetMethod(methodName);
        methodInvoker.setTargetObject(obj);

        // 准备方法
        methodInvoker.prepare();

        return methodInvoker.invoke();
	}
	
	/**
	 * 根据字段名和方法前缀拼出getter方法名
	 * 
	 * @param fieldName		字段名
	 * @param prefix		前缀
	 * @return
	 */
	public static String createMethodName(String fieldName, String prefix){
		if(StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(prefix)){
			return StringUtils.EMPTY;
		}
		//判断fieldName第一位是否是小写
		String first = fieldName.substring(0, 1);
		if(!StringUtils.equals(first, first.toUpperCase())){
			//第一个字符小写的
			first = first.toUpperCase();
		}
		String methodName = prefix + first + fieldName.substring(1);
		
		return methodName;
	}
	
	/**
	 * 根据字段名得到实例的字段值
	 * 
	 * @param object		实例对象
	 * @param fieldName		字段名称
	 * @return 实例字段的值，如果没找到该字段则返回null
	 * @throws IllegalAccessException
	 */
	public static Object getFieldValue(Object object, String fieldName)
			throws IllegalAccessException {
		Set<Field> fields = new HashSet<Field>();
		// 本类中定义的所有字段
		Field[] tempFields = object.getClass().getDeclaredFields();
		for (Field field : tempFields) {
			field.setAccessible(true);
			fields.add(field);
		}
		// 所有的public字段，包括父类中的
		tempFields = object.getClass().getFields();
		for (Field field : tempFields) {
			fields.add(field);
		}

		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field.get(object);
			}
		}
		return null;
	}
}
