package com.message.main.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

public class UserController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 进入管理员列表页面
	 * @param in
	 * @param out
	 * @param admin
	 * @return
	 */
	public ModelAndView inLoginJsp(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("user.login");
	}
	
	public ModelAndView userLogin(HttpServletRequest request, HttpServletResponse response, User user){
		return new ModelAndView("");
	}

}

