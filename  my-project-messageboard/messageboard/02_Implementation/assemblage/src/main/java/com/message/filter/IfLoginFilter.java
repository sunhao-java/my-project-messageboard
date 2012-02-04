package com.message.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.message.utils.resource.ResourceType;

/**
 * 判断是否登录的filter，拦截一些未登录的非法URL
 * @author sunhao(sunhao.java@gmail.com)
 */
public class IfLoginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		String url = request.getServletPath();
		if(url.indexOf(".do") != -1){
			System.out.println("request process info:");
			System.out.println("begin-----------------");
			System.out.println("url=[" + url + "]");
			System.out.println("port=[" + request.getServerPort() + "]");
			System.out.println("client=[" + request.getRemoteAddr() + "]");
			System.out.println("method=[" + request.getMethod() + "]");
			System.out.println("end-------------------");
		}
		if(url.indexOf(".do") != -1 && !url.equals("/user/inLogin.do") && !url.equals("/user/login.do") && !url.equals("/user/check.do") &&
				!url.equals("/user/register.do") && !url.equals("/user/emailConfirm.do")){
			if(session.getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION) == null){
				response.sendRedirect(request.getContextPath() + "/user/inLogin.do");
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		
	}

}
