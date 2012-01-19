package com.message.main.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.MD5Utils;
import com.message.utils.SqlUtils;
import com.message.utils.StringUtils;
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
			status = this.userService.userLogin(user, in);
			if(status == 0){
				//跳转到另外一个controller
				in.getSession().setAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION, this.userService.getUserByName(user.getUsername()));
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
				params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
			} else {
				params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
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
			status = this.userService.checkUser(user) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE;
			obj.put(ResourceType.AJAX_STATUS, status);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 进入用户信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView showUserInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(user != null){
			try {
				user = this.userService.getUserById(user.getPkId());
				user = this.userService.addLoginInfo(user);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			params.put("user", user);
		}
		return new ModelAndView("user.info", params);
	}
	
	/**
	 * 进入编辑用户信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inEditUserInfoJsp(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(user != null){
			try {
				params.put("user", this.userService.getUserById(user.getPkId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("user.edit.info", params);
	}
	
	/**
	 * 保存修改后的用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		JSONObject obj = new JSONObject();
		try {
			this.userService.saveEdit(user);
			obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			logger.error(e.getMessage(), e);
		}
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 进入修改密码页面
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public ModelAndView inChangePswJsp(HttpServletRequest request, HttpServletResponse response){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		User userSession = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		params.put("user", userSession);
		return new ModelAndView("user.password.change", params);
	}
	
	/**
	 * 检查原密码是否正确
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkPsw(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		String oldPassword = in.getString("oldPassword", StringUtils.EMPTY);
		JSONObject obj = new JSONObject();
		User userInSession = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(userInSession != null){
			String md5OldPsw = MD5Utils.MD5Encode(oldPassword);
			if(md5OldPsw.equals(userInSession.getPassword())){
				obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
			} else {
				obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			}
		} else {
			obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
		}
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 保存新密码
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ModelAndView savePassword(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		JSONObject obj = new JSONObject();
		if(user != null){
			try {
				obj.put(ResourceType.AJAX_STATUS, this.userService.savePassword(user) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			}
		}
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 登出系统
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject obj = new JSONObject();
		User userInSession = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(userInSession != null){
			in.getSession().removeAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		}
		obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 列出所有的用户
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listAllUser(HttpServletRequest request, HttpServletResponse response, User user){
		in = new WebInput(request);
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("paginationSupport", this.userService.listAllUser(start, num, user));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return new ModelAndView("user.list.alluser", params);
	}
	
	/**
	 * 删除用户(软删除)（可以删除一条也可批量删除）
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject obj = new JSONObject();
		String pkids = in.getString("pkIds", StringUtils.EMPTY);
		if(StringUtils.isNotEmpty(pkids)){
			try {
				obj.put(ResourceType.AJAX_STATUS, this.userService.deleteUser(pkids) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE );
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		} else {
			obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
		}
		out.toJson(obj);
		return null;
	}

}

