package com.message.main.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.WebInput;
import com.message.utils.WebOutput;
import com.message.utils.resource.ResourceType;

/**
 * 用户操作的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class UserController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static WebInput in = null;
	private static WebOutput out = null;
	
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
		in = new WebInput(request);
		String view = "";
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(user != null) {
			view = "redirect:/home/inMessageIndex.do";
		} else {
			view = "user.login";
		}
		return new ModelAndView(view);
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public ModelAndView userLogin(HttpServletRequest request, HttpServletResponse response, User user){
		in = new WebInput(request);
		logger.debug("用户名是" + user.getUsername());
		int status = 0;
		try {
			status = this.userService.userLogin(user);
			if(status == 0){
				//跳转到另外一个controller
				in.getSession().setAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION, user);
				return new ModelAndView("redirect:/home/inMessageIndex.do");
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
	}
	
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		JSONObject params = new JSONObject();
		try {
			boolean status = this.userService.registerUser(user);
			if(status){
				params.put("status", 1);
			} else {
				params.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		out.toJson(params);
		return null;
	}
	
	/**
	 * 判断注册的用户是否已存在
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		JSONObject obj = new JSONObject();
		String status = "";
		try {
			status = this.userService.checkUser(user) ? "1" : "0";
			obj.put("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.toJson(obj);
		return null;
	}

}

