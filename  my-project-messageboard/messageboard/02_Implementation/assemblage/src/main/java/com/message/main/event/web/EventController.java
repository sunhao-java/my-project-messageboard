package com.message.main.event.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.SqlUtils;
import com.message.base.web.WebInput;
import com.message.main.event.service.EventService;
import com.message.main.message.web.MessageController;
import com.message.resource.ResourceType;

/**
 * 时间controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventController extends ExtMultiActionController {
private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private WebInput in = null;
	
	private EventService eventService;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	
	/**
	 * 列出所有事件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listEvent(HttpServletRequest request, HttpServletResponse response){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		try {
			params.put("paginationSupport", this.eventService.getEvents(start, num));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return new ModelAndView("message.list.event", params);
	}
}
