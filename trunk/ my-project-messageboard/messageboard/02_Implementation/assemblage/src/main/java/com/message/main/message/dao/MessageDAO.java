package com.message.main.message.dao;

import com.message.base.pagination.PaginationSupport;
import com.message.main.message.pojo.Message;
import com.message.main.user.pojo.User;

/**
 * 留言操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface MessageDAO {
	/**
	 * 获取所有的留言
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getAllMessages(int start, int num, Message message) throws Exception;
	
	/**
	 * 保存留言
	 * @param message
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Long saveMessage(Message message) throws Exception;
	
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
	 * 更新留言
	 * @param message
	 * @throws Exception
	 */
	void updateMessage(Message message) throws Exception;
	
	/**
	 * 获取我的留言
	 * @param start
	 * @param num
	 * @param user
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getMyMessages(int start, int num, User user) throws Exception;
}
