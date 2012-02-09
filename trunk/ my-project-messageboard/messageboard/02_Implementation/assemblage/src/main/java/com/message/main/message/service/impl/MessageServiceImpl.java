package com.message.main.message.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.i18n.MessageUtils;
import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.StringUtils;
import com.message.main.event.pojo.BaseEvent;
import com.message.main.event.service.EventService;
import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.resource.ResourceType;

/**
 * 留言操作的service 
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MessageServiceImpl implements MessageService {
	
	private static final String AUDIT_OK = "ok";
	private static final String AUDIT_NO = "no";
	
	private MessageDAO messageDAO;
	
	private UserService userService;
	
	private ReplyService replyService;
	
	private EventService eventService;

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
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

	public Long saveMessage(Message message, User user) throws Exception {
		if(user != null && message != null){
			message.setCreateUserId(user.getPkId());
			message.setCreateDate(new Date());
			message.setDeleteFlag(ResourceType.DELETE_NO);
			message.setCreateUsername(user.getTruename());
			message.setIsAudit(ResourceType.AUDIT_NOAUDIT);
		}
		
		Long messageId = this.messageDAO.saveMessage(message);
		
		String eventMsg = MessageUtils.getMessage("event.message.add", new Object[]{message.getTitle(), messageId});
		this.eventService.publishEvent(new BaseEvent(user.getPkId(), ResourceType.EVENT_ADD, user.getPkId(), ResourceType.MESSAGE_TYPE, 
				messageId, user.getLoginIP(), eventMsg));
		
		return messageId;
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

	public void deleteMessage(String pkIds, User user) throws Exception {
		if(StringUtils.isNotEmpty(pkIds)){
			String[] pkIdArr = pkIds.split(",");
			for(String pkId : pkIdArr){
				Message dbMessage = this.messageDAO.getMessageByPkId(Long.valueOf(pkId));
				if(dbMessage != null){
					dbMessage.setDeleteFlag(ResourceType.DELETE_YES);
					this.messageDAO.updateMessage(dbMessage);
					
					String eventMsg = MessageUtils.getMessage("event.message.delete", new Object[]{dbMessage.getTitle(), Long.valueOf(pkId)});
					this.eventService.publishEvent(new BaseEvent(user.getPkId(), ResourceType.EVENT_DELETE, dbMessage.getCreateUserId(), 
							ResourceType.MESSAGE_TYPE, Long.valueOf(pkId), user.getLoginIP(), eventMsg));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport getMyMessages(int start, int num, User user, Message message) throws Exception {
		PaginationSupport paginationSupport = this.messageDAO.getMyMessages(start, num, user, message);
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

	@SuppressWarnings("unchecked")
	public PaginationSupport listToAuditMessage(int start, int num, Message message, boolean flag) throws Exception {
		PaginationSupport pagination = this.messageDAO.listToAuditMessage(start, num, message, flag);
		List<Message> messages = pagination.getItems();
		if(CollectionUtils.isNotEmpty(messages)){
			for(Message msg : messages){
				User dbuser = this.userService.getUserById(msg.getCreateUserId());
				List<Reply> replys = this.replyService.getReplysByMessageId(msg.getPkId());
				
				msg.setCreateUser(dbuser);
				msg.setReplys(replys);
			}
		}
		return pagination;
	}

	public void setAudit(Long messageId, String status, User user) throws Exception {
		Message dbMessage = this.messageDAO.getMessageByPkId(messageId);
		String eventMsg = "";
		if(AUDIT_OK.equals(status)) {
			dbMessage.setIsAudit(ResourceType.AUDIT_YES);
			eventMsg = MessageUtils.getMessage("audit.yes", new Object[]{dbMessage.getTitle(), messageId});
		} else if(AUDIT_NO.equals(status)){
			dbMessage.setIsAudit(ResourceType.AUDIT_NO);
			eventMsg = MessageUtils.getMessage("audit.no", new Object[]{dbMessage.getTitle(), messageId});
		}
		
		this.eventService.publishEvent(new BaseEvent(user.getPkId(), ResourceType.EVENT_EDIT, dbMessage.getCreateUserId(), 
				ResourceType.MESSAGE_TYPE, messageId, user.getLoginIP(), eventMsg));
		this.messageDAO.updateMessage(dbMessage);
	}

}
