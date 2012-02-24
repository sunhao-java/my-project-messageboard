package com.message.main.event.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.SqlUtils;
import com.message.base.web.WebInput;
import com.message.main.event.service.EventService;
import com.message.resource.ResourceType;

/**
 * 时间controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventController extends ExtMultiActionController {
	
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
	 * @throws Exception 
	 */
	public ModelAndView listEvent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		params.put("paginationSupport", this.eventService.getEvents(start, num));
		return new ModelAndView("message.list.event", params);
	}
}
