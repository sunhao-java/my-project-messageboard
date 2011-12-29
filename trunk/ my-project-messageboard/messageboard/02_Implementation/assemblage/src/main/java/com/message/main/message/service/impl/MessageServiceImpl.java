package com.message.main.message.service.impl;

import java.util.Date;
import java.util.List;

import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.resource.ResourceType;

/**
 * 留言操作的service 
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MessageServiceImpl implements MessageService {
	
	private MessageDAO messageDAO;
	
	private UserService userService;

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Message> getAllMessages(int start, int num, Message message) throws Exception {
		List<Message> messages = this.messageDAO.getAllMessages(start, num, message);
		for(Message msg : messages){
			User user = this.userService.getUserById(msg.getCreateUserId());
			if(user != null){
				msg.setCreateUser(user);
			}
		}
		return messages;
	}

	public Long saveMessage(Message message, Long userPkId) throws Exception {
		if(userPkId != null && message != null){
			message.setCreateUserId(userPkId);
			message.setCreateDate(new Date());
			message.setDeleteFlag(ResourceType.DELETE_NO);
		}
		return this.messageDAO.saveMessage(message);
	}

}
