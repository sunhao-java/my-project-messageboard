package com.message.base.event.service;

import com.message.base.event.pojo.BaseEvent;
import com.message.base.pagination.PaginationSupport;

/**
 * 事件service
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface EventService {
	
	/**
	 * 发布事件
	 * @param baseEvent
	 * @throws Exception
	 */
	void publishEvent(BaseEvent baseEvent) throws Exception;
	
	/**
	 * 获取所有操作日志
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getEvents(int start, int num) throws Exception;
}
