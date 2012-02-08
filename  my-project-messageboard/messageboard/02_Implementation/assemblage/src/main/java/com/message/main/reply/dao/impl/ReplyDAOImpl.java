package com.message.main.reply.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.main.reply.dao.ReplyDAO;
import com.message.main.reply.pojo.Reply;
import com.message.resource.ResourceType;

/**
 * 回复操作的dao的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class ReplyDAOImpl extends GenericHibernateDAOImpl implements ReplyDAO {

	public void updateReply(Reply reply) throws Exception {
		this.updateObject(reply);
	}

	public Reply getReplyByPkId(Long pkId) throws Exception {
		return (Reply) this.loadObject(Reply.class, pkId);
	}

	@SuppressWarnings("unchecked")
	public List<Reply> getReplysByMessageId(Long messageId) throws Exception {
		String hql = "from Reply r where r.messageId = :messageId and r.deleteFlag = :deleteFlag order by r.pkId desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("messageId", messageId);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		
		return this.findByHQL(hql, params);
	}

	public Long saveReply(Reply reply) throws Exception {
		return ((Reply) this.saveObject(reply)).getPkId();
	}

}
