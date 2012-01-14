package com.message.main.reply.dao;

import com.message.main.reply.pojo.Reply;

/**
 * 回复操作的dao
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface ReplyDAO {
	
	/**
	 * 更新回复
	 * @param reply
	 */
	public void updateReply(Reply reply) throws Exception;
	
	/**
	 * 根据ID获得回复
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	public Reply getReplyByPkId(Long pkId) throws Exception;
}
