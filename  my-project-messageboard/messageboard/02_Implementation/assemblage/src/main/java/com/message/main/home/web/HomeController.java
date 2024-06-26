package com.message.main.home.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ModelAndMethod;
import com.message.base.spring.SimpleController;
import com.message.base.web.WebInput;
import com.message.main.ResourceType;
import com.message.main.history.service.HistoryService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.menu.pojo.Menu;
import com.message.main.menu.service.MenuService;
import com.message.main.user.pojo.User;

/**
 * home的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class HomeController extends SimpleController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HistoryService historyService;
	@Autowired
    private MenuService menuService;
	
	private WebInput in = null;
	
    /**
	 * 左边菜单的请求
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView left(HttpServletRequest request, HttpServletResponse response) throws Exception {
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(user != null){
			params.put("user", user);
		}
        List<Menu> menus = this.menuService.getMenuTree();
        params.put("menus", menus);
		return new ModelAndView("message.left", params);
	}
	
	/**
	 * 顶部frame的请求
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response){
		in = new WebInput(request);
		int loginCount = 0;
		Date lastLoginTime = null;
		Map<String, Object> params = new HashMap<String, Object>();
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(user != null){
			logger.debug("login user's name is {}", user.getUsername());
			try {
				loginCount = this.historyService.getLoginCount(user.getPkId());
				lastLoginTime = this.historyService.getLastLoginTime(user.getPkId(), false);
				params.put("lastLoginTime", lastLoginTime);
				params.put("loginCount", loginCount);
				params.put("user", user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return new ModelAndView("message.top", params);
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
	public ModelAndMethod inMessageIndex(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){
		return new ModelAndMethod("user", "profile");
//		return new ModelAndView("redirect:/user/profile.do?uid=" + loginUser.getPkId());
	}
	
	/**
	 * 未知
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("message.template");
	}
	
}
