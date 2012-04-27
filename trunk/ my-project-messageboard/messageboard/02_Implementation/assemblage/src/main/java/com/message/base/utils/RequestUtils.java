package com.message.base.utils;

import java.util.Iterator;
import java.util.Map;

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
	
	/**
	 * 获取此次请求的url(不带contextPath)
	 * 
	 * @param request		此次请求
	 * @param needParams	是否需要参数
	 * @return				URL
	 */
	public static String getRequestUrl(HttpServletRequest request, boolean needParams){
		StringBuffer requestUrl = new StringBuffer(request.getServletPath());
		if(!needParams){
			return requestUrl.toString();
		}
		
		//所带参数的MAP
		Map params = request.getParameterMap();
		Iterator it = params.keySet().iterator();
		if(it.hasNext())
			requestUrl.append("?");
		while(it.hasNext()){
			String key = (String) it.next();
			String[] values = (String[]) params.get(key);
			String value = StringUtils.EMPTY;
			if(values != null && values.length > 0){
				value = values[0];
			} else {
				value = StringUtils.EMPTY;
			}
			
			if(StringUtils.isNotEmpty(key)){
				//key必须不为空，value可以为空
				requestUrl.append(key).append("=").append(value).append("&");
			}
		}
		
		if(requestUrl.toString().endsWith("&")){
			return requestUrl.substring(0, requestUrl.length() - 1);
		}
		
		return requestUrl.toString();
	}
}
