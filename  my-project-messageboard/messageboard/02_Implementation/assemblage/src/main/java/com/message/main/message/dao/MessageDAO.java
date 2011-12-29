package com.message.main.message.dao;

import java.util.List;

import com.message.main.message.pojo.Message;

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
	List<Message> getAllMessages(int start, int num, Message message) throws Exception;
	
	/**
	 * 保存留言
	 * @param message
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Long saveMessage(Message message) throws Exception;
}
