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
			System.out.println("----------start----------");
			System.out.println("url:" + url);
			System.out.println("port:" + request.getServerPort());
			System.out.println("client:" + request.getRemoteAddr());
			System.out.println("-----------end-----------");
		}
		
		chain.doFilter(req, res);
	}

	public void init(FilterConfig config) throws ServletException {
		
	}

}
