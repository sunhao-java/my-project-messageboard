package com.message.main.history.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.history.service.HistoryService;
import com.message.main.message.web.MessageController;
import com.message.main.user.pojo.User;
import com.message.utils.SqlUtils;
import com.message.utils.resource.ResourceType;

public class HistoryController extends ExtMultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private WebInput in = null;
	//private WebOutput out = null;
	
	private HistoryService historyService;
	
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	/**
	 * 列出登录者的登录历史
	 * @param request
	 * @param response
	 * @param history
	 * @return
	 */
	public ModelAndView listLoginHistory(HttpServletRequest request, HttpServletResponse response, UserLoginHistory history){
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		if(user != null){
			try {
				params.put("paginationSupport", this.historyService.getHistoryByUserId(user.getPkId(), start, num, history));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
		return new ModelAndView("user.login.history.list", params);
	}
}