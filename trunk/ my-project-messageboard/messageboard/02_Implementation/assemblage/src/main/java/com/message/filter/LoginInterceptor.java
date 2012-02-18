package com.message.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-18 下午06:14:15
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 发起请求,进入拦截器链，运行所有拦截器的preHandle方法， 
	 * 1.当preHandle方法返回false时，从当前拦截器往回执行所有拦截器的afterCompletion方法，再退出拦截器链。
	 * 2.当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。
	 * 		然后进入拦截器链，运行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	 */

	/**
	 * 在Controller方法前进行拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println(1);
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println(2);
	}

	/**
	 * 在Controller方法后进行拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println(3);
	}

}
