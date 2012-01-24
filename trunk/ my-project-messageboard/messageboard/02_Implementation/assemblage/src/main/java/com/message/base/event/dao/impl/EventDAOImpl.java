package com.message.base.event.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.message.base.event.dao.EventDAO;
import com.message.base.event.pojo.BaseEvent;
import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;

/**
 * 事件dao实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventDAOImpl extends GenericHibernateDAOImpl implements EventDAO {

	public void saveEvent(BaseEvent baseEvent) throws Exception {
		this.saveObject(baseEvent);
	}

	public PaginationSupport getEvents(int start, int num) throws Exception {
		String hql = "from BaseEvent where 1=1 order by pkId desc";
		String countHql = "select count(*) " + hql;
		Map<String, Object> params = new HashMap<String, Object>();
		return this.getBeanPaginationSupport(hql, countHql, start, num, params);
	}

}
