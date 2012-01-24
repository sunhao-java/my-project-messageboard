package com.message.base.event.dao;

import com.message.base.event.pojo.BaseEvent;
import com.message.base.pagination.PaginationSupport;

/**
 * 事件dao
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface EventDAO {
	
	/**
	 * 发布事件
	 * @param baseEvent
	 * @throws Exception
	 */
	void saveEvent(BaseEvent baseEvent) throws Exception;
	
	/**
	 * 获取所有操作日志
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getEvents(int start, int num) throws Exception;
}
