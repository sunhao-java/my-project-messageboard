package com.message.base.event.service.impl;

import java.util.Date;
import java.util.List;

import com.message.base.event.dao.EventDAO;
import com.message.base.event.pojo.BaseEvent;
import com.message.base.event.service.EventService;
import com.message.base.pagination.PaginationSupport;
import com.message.main.user.service.UserService;

/**
 * 事件service实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventServiceImpl implements EventService {
	
	private EventDAO eventDAO;
	
	private UserService userService;

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void publishEvent(BaseEvent baseEvent) throws Exception {
		if(baseEvent != null){
			baseEvent.setOperationTime(new Date());
			this.eventDAO.saveEvent(baseEvent);
		}
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport getEvents(int start, int num) throws Exception {
		PaginationSupport paginationSupport = this.eventDAO.getEvents(start, num);
		List<BaseEvent> events = paginationSupport.getItems();
		for(BaseEvent event : events){
			if(event.getOperatorId() != null && event.getOwnerId() != null){
				event.setOperator(this.userService.getUserById(event.getOperatorId()));
				event.setOwner(this.userService.getUserById(event.getOwnerId()));
			}
		}
		return paginationSupport;
	}

}
