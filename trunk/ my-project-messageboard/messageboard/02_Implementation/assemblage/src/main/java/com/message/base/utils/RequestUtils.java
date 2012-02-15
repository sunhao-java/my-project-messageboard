package com.message.base.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest的工具类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-15 下午10:12:52
 */
public class RequestUtils {
	/**
	 * 判断是否是AJAX请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return StringUtils.isNotEmpty(header) && "XMLHttpRequest".equals(header);
	}
}
