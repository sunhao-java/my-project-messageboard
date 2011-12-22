package com.message.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class HomeController extends MultiActionController {
	/**
	 * 进入登录页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inLoginJsp(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("user.login");
	}
	
}
