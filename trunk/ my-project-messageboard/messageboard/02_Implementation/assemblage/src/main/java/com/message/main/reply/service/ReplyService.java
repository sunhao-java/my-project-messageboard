package com.message.main.reply.service;

/**
 * 回复操作的serivce
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface ReplyService {
	
	/**
	 * 根据id删除回复
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteReplyById(Long pkId) throws Exception;
}
