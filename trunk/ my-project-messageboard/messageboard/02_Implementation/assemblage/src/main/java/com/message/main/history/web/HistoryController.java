package com.message.main.history.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.web.WebInput;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.history.service.HistoryService;
import com.message.main.message.web.MessageController;

public class HistoryController extends SimpleController {
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
	public ModelAndView listLoginHistory(HttpServletRequest request, HttpServletResponse response, UserLoginHistory history) throws Exception {
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);

        params.put("paginationSupport", this.historyService.getHistory(start, num, history));

		return new ModelAndView("user.login.history.list", params);
	}
}
