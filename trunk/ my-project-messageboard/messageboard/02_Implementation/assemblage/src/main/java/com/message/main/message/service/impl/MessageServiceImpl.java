package com.message.main.message.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.reply.pojo.Reply;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.resource.ResourceType;
import com.message.utils.string.StringUtils;

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

	public int getLoginUserMessageCount(Long pkId) throws Exception {
		return this.messageDAO.getLoginUserMessageCount(pkId);
	}

	public Message getMessageByPkId(Long pkId) throws Exception {
		Message message = this.messageDAO.getMessageByPkId(pkId);
		if(message != null){
			message.setCreateUser(this.userService.getUserById(message.getCreateUserId()));
		}
		if(CollectionUtils.isNotEmpty(message.getReplys())){
			for(Reply reply : message.getReplys()){
				reply.setReplyUser(this.userService.getUserById(reply.getReplyUserId()));
			}
		}
		return message;
	}

	public void deleteMessage(String pkIds) throws Exception {
		if(StringUtils.isNotEmpty(pkIds)){
			String[] pkIdArr = pkIds.split(",");
			for(String pkId : pkIdArr){
				Message dbMessage = this.messageDAO.getMessageByPkId(Long.valueOf(pkId));
				if(dbMessage != null){
					dbMessage.setDeleteFlag(ResourceType.DELETE_YES);
					this.messageDAO.updateMessage(dbMessage);
				}
			}
		}
	}

}
