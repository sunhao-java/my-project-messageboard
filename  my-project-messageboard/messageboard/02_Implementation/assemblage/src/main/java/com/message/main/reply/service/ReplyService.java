package com.message.main.reply.service;

import java.util.List;

import com.message.main.reply.pojo.Reply;
import com.message.main.user.pojo.User;

/**
 * 回复操作的serivce
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface ReplyService {
	
	/**
	 * 根据id删除回复
	 * @param pkId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean deleteReplyById(Long pkId, User user) throws Exception;
	
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
	 * @param user
	 * @throws Exception
	 */
	public void saveReply(Reply reply, User user) throws Exception;
}
