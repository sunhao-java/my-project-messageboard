package com.message.main.event.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.web.WebInput;
import com.message.main.ResourceType;
import com.message.main.event.service.EventService;

/**
 * 时间controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventController extends SimpleController {
	
	private WebInput in = null;
	
	@Autowired
	private EventService eventService;

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
