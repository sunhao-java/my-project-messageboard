package com.message.main.message.service;

import com.message.main.message.pojo.Message;
import com.message.utils.PaginationSupport;

/**
 * 留言操作的service 
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface MessageService {
	
	/**
	 * 获取所有的留言
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getAllMessages(int start, int num, Message message) throws Exception;
	
	/**
	 * 保存留言
	 * @param message
	 * @param userPkId
	 * @return
	 * @throws Exception
	 */
	Long saveMessage(Message message, Long userPkId) throws Exception;
	
	/**
	 * 获取某个用户的留言条数
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	int getLoginUserMessageCount(Long pkId) throws Exception;
	
	/**
	 * 根据ID获取某个留言
	 * @param pkId
	 * @return
	 */
	Message getMessageByPkId(Long pkId) throws Exception;
	
	/**
	 * 根据pkId的集合批量删除留言
	 * @param pkIds
	 * @throws Exception
	 */
	void deleteMessage(String pkIds) throws Exception;
	
}
