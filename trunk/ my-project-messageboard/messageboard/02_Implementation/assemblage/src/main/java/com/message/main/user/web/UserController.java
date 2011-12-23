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
	 * 进入用户登录页面
	 * @param in
	 * @param out
	 * @param admin
	 * @return
	 */
	public ModelAndView inLoginJsp(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("user.login");
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public ModelAndView userLogin(HttpServletRequest request, HttpServletResponse response, User user){
		int status = 0;
		try {
			status = this.userService.userLogin(user);
			if(status == 0){
				//TODO:by sunhao 正确登录之后跳转的页面
			} else {
				if(status == 1){
					request.setAttribute("message", "用户名错误");
					request.setAttribute("status", status);
				} else if(status == 2){
					request.setAttribute("message", "密码错误");
					request.setAttribute("status", status);
				} else if(status == 3){
					request.setAttribute("message", "用户名密码必填");
					request.setAttribute("status", status);
				}
				return this.inLoginJsp(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = 3;		//网络有错误
			logger.error(e.getMessage(), e);
			request.setAttribute("status", status);
			return this.inLoginJsp(request, response);
		}
		return null;
	}

}

