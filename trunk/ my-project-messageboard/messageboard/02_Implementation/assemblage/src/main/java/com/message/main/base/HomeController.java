package com.message.main.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.history.service.HistoryService;
import com.message.main.user.pojo.User;
import com.message.utils.WebInput;
import com.message.utils.resource.ResourceType;

/**
 * home的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class HomeController extends MultiActionController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private HistoryService historyService;
	
	private WebInput in = null;
	
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

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
		in = new WebInput(request);
		int loginCount = 0;
		Date lastLoginTime = null;
		Map<String, Object> params = new HashMap<String, Object>();
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		if(user != null){
			logger.debug("login user's name is {}", user.getUsername());
			try {
				loginCount = this.historyService.getLoginCount(user.getPkId());
				lastLoginTime = this.historyService.getLastLoginTime(user.getPkId());
				params.put("lastLoginTime", lastLoginTime);
				params.put("loginCount", loginCount);
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
