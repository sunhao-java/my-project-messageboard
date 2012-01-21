package com.message.main.message.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.pagination.PaginationSupport;
import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.StringUtils;
import com.message.utils.resource.ResourceType;

/**
 * 留言操作的service 
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MessageServiceImpl implements MessageService {
	
	private MessageDAO messageDAO;
	
	private UserService userService;
	
	private ReplyService replyService;

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport getAllMessages(int start, int num, Message message) throws Exception {
		PaginationSupport paginationSupport = this.messageDAO.getAllMessages(start, num, message);
		List<Message> messages = paginationSupport.getItems();
		for(Message msg : messages){
			User user = this.userService.getUserById(msg.getCreateUserId());
			List<Reply> replys = this.replyService.getReplysByMessageId(msg.getPkId());
			
			msg.setCreateUser(user);
			msg.setReplys(replys);
		}
		return paginationSupport;
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
		List<Reply> replys = this.replyService.getReplysByMessageId(message.getPkId());
		if(CollectionUtils.isNotEmpty(replys)){
			message.setReplys(replys);
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

	@SuppressWarnings("unchecked")
	public PaginationSupport getMyMessages(int start, int num, User user) throws Exception {
		PaginationSupport paginationSupport = this.messageDAO.getMyMessages(start, num, user);
		List<Message> messages = paginationSupport.getItems();
		if(CollectionUtils.isNotEmpty(messages)){
			for(Message msg : messages){
				User dbuser = this.userService.getUserById(msg.getCreateUserId());
				List<Reply> replys = this.replyService.getReplysByMessageId(msg.getPkId());
				
				msg.setCreateUser(dbuser);
				msg.setReplys(replys);
			}
		}
		return paginationSupport;
	}

}
