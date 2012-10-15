package com.message.main.event.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.hibernate.GenericHibernateDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.main.event.dao.EventDAO;
import com.message.main.event.pojo.BaseEvent;

/**
 * 事件dao实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class EventDAOImpl extends GenericHibernateDAO implements EventDAO {

	public void saveEvent(BaseEvent baseEvent) throws Exception {
		this.saveObject(baseEvent);
	}

	public PaginationSupport getEvents(int start, int num) throws Exception {
		String hql = "from BaseEvent where 1=1 order by pkId desc";
		String countHql = "select count(*) " + hql;
		Map<String, Object> params = new HashMap<String, Object>();
		return this.getPaginationSupport(hql, countHql, start, num, params);
	}

	public void deleteEvent(BaseEvent baseEvent) throws Exception {
		this.deleteObject(baseEvent);
	}

	public List<BaseEvent> getAllEvent() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from BaseEvent order by pkId desc ";
		return this.findByHQL(hql, params);
	}

}
