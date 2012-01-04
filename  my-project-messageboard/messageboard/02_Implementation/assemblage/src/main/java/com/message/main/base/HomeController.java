package com.message.main.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * home的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class HomeController extends MultiActionController {
	
	/**
	 * 左边菜单的请求
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView left(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.left");
	}
	
	/**
	 * 顶部frame的请求
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.top");
	}
	
	/**
	 * 底部frame的请求
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tail(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.tail");
	}
	
	/**
	 * 主frame的请求
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("redirect:/message/listMessage.do");
	}
	
	/**
	 * 未知
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inMessageIndex(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.template");
	}
	
}
