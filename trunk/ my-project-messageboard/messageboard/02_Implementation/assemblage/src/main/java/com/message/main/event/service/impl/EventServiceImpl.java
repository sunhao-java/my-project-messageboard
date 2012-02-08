package com.message.main.event.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.DateUtils;
import com.message.main.event.dao.EventDAO;
import com.message.main.event.job.CleanEventJob;
import com.message.main.event.pojo.BaseEvent;
import com.message.main.event.service.EventService;
import com.message.main.user.service.UserService;

/**
 * 事件service实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventServiceImpl implements EventService {
	private static final Logger logger = LoggerFactory.getLogger(CleanEventJob.class);
	
	/**
	 * 一周的分钟数
	 */
	private static final long WEEK_AGO_MINUTES = 1 * 7 * 24 * 60;
	
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

	public void cleanEventWeekAgo() throws Exception {
		List<BaseEvent> events = this.eventDAO.getAllEvent();
		for(BaseEvent event : events){
			if(DateUtils.getMinuteBewteenDates(event.getOperationTime(), new Date()) >= WEEK_AGO_MINUTES){
				logger.debug("delete an event entity, id is {}", event.getPkId());
				this.eventDAO.deleteEvent(event);
			}
		}
	}

}
