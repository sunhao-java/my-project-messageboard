package com.message.utils.resource;

/**
 * 资源类型类
 * @author sunhao(sunhao.java@gmail.com)
 */
public final class ResourceType {
	
	/**
	 * 没有被删除
	 */
	public static final Long DELETE_NO = 0L;
	/**
	 * 已删除
	 */
	public static final Long DELETE_YES = 1L;
	
	/**
	 * 每页显示的条数
	 */
	public static final int PAGE_NUM = 10;
	
	/**
	 * session中登录者的key
	 */
	public static final String LOGIN_USER_KEY_IN_SESSION = "loginUser";
	
	/**
	 * 登录页面的URL
	 */
	public static final String LOGIN_PAGE_URL = "/user/inLogin.do";
	
	/**
	 * 是管理员的标识
	 */
	public static final Long IS_ADMIN_YES = 1L;
	/**
	 * 不是管理员的标识
	 */
	public static final Long IS_ADMIN_NO = 0L;
	
	public static final String AJAX_STATUS = "status";
	/**
	 * ajax请求成功
	 */
	public static final String AJAX_SUCCESS = "1";
	/**
	 * ajax请求失败
	 */
	public static final String AJAX_FAILURE = "0";
}
