package com.message.main.message.service;

import java.util.List;

import com.message.main.message.pojo.Message;

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
	List<Message> getAllMessages(int start, int num, Message message) throws Exception;

}
