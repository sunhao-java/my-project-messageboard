package com.message.main.history.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.history.service.HistoryService;
import com.message.main.message.web.MessageController;
import com.message.main.user.pojo.User;
import com.message.utils.SqlUtils;
import com.message.utils.WebInput;
import com.message.utils.resource.ResourceType;

public class HistoryController extends MultiActionController {
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
	 * @return
	 */
	public ModelAndView listLoginHistory(HttpServletRequest request, HttpServletResponse response){
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		if(user != null){
			try {
				params.put("paginationSupport", this.historyService.getHistoryByUserId(user.getPkId(), start, num));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
		return new ModelAndView("user.login.history.list", params);
	}
}
