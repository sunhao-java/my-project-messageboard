package com.message.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class HomeController extends MultiActionController {
	
	public ModelAndView left(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.left");
	}
	
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.top");
	}
	
	public ModelAndView tail(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.tail");
	}
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("redirect:/message/listMessage.do");
	}
	
	public ModelAndView inMessageIndex(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.template");
	}
	
}
