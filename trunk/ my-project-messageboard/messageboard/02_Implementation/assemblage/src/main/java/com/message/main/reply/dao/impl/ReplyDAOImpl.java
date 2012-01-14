package com.message.main.reply.dao.impl;

import com.message.main.reply.dao.ReplyDAO;
import com.message.main.reply.pojo.Reply;
import com.message.utils.base.utils.impl.GenericHibernateDAOImpl;

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

}
