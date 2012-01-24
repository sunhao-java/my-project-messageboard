package com.message.main.reply.dao;

import java.util.List;

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
	
	/**
	 * 根据留言id获取回复
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public List<Reply> getReplysByMessageId(Long messageId) throws Exception;
	
	/**
	 * 保存回复
	 * @param reply
	 * @return
	 * @throws Exception
	 */
	public Long saveReply(Reply reply) throws Exception;
}
