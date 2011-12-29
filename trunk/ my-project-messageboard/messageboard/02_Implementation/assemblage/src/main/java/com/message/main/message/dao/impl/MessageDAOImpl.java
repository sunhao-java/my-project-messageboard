package com.message.main.message.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;
import com.message.utils.base.utils.impl.GenericHibernateDAOImpl;
import com.message.utils.resource.ResourceType;

/**
 * 留言操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MessageDAOImpl extends GenericHibernateDAOImpl implements MessageDAO {
	
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessages(int start, int num, Message message)
			throws Exception {
		String hql = "from Message where deleteFlag = :deleteFlag ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", ResourceType.DELETE_NO);
		List<Message> messages = this.getBeanPaginationSupport(hql, start, num, params);
		return messages;
	}

	public Long saveMessage(Message message) throws Exception {
		return ((Message)this.saveObject(message)).getPkId();
	}

}
