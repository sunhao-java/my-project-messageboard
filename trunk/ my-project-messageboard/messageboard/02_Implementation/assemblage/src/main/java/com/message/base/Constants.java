package com.message.base;

import com.message.base.properties.SystemConfig;

/**
 * 资源类型类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-14 下午11:56:13
 */
public class Constants {
	/**
	 * 标准的日期格式
	 */
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 上传路径
     */
    public static final String DEFAULT_UPLOAD_PAYH = SystemConfig.getProperty("message.upload.dir", "/opt/data/");

    /**
     * 缓存中默认存在时间(30天)30 * 24 * 60 * 60 = 2592000
     * 单位：秒
     */
    public static final Integer DEFAULT_EXPIRE_TIME = 2592000;
}