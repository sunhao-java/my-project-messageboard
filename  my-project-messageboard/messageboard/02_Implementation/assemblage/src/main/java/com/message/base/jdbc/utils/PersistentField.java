package com.message.base.jdbc.utils;

import java.io.Serializable;

/**
 * save bean class fields
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-10 上午02:00:37
 */
@SuppressWarnings("rawtypes")
public class PersistentField implements Serializable {
	private static final long serialVersionUID = -2932429421268936487L;

	/**
	 * 字段名
	 */
	private String fieldName;
	/**
	 * Java类型
	 */
	private Class javaType;
    /**
     * sql类型
     */
    private int sqlType;
    /**
     * setter方法名
     */
    private String writeName;
    /**
     * 短名?
     */
    private String shortName;
    /**
     * 类名
     */
    private String beanName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class getJavaType() {
		return javaType;
	}

	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

	public String getWriteName() {
		return writeName;
	}

	public void setWriteName(String writeName) {
		this.writeName = writeName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}
