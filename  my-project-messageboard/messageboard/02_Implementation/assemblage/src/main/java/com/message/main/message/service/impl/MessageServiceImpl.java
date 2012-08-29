package com.message.main.message.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.hibernate.GenericHibernateDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.properties.MessageUtils;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.event.pojo.BaseEvent;
import com.message.main.event.service.EventService;
import com.message.main.friend.service.FriendService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

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

    private GenericHibernateDAO genericHibernateDAO;
    
    private FriendService friendService;

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

    public void setGenericHibernateDAO(GenericHibernateDAO genericHibernateDAO) {
        this.genericHibernateDAO = genericHibernateDAO;
    }

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

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

	public Long saveMessage(Message message) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		if(loginUser != null && message != null){
			message.setCreateUserId(loginUser.getPkId());
			message.setCreateDate(new Date());
			message.setDeleteFlag(ResourceType.DELETE_NO);
			message.setCreateUsername(loginUser.getTruename());
			message.setIsAudit(ResourceType.AUDIT_NOAUDIT);
		}
		
		Long messageId = this.messageDAO.saveMessage(message);
		
		String eventMsg = MessageUtils.getProperties("event.message.add", new Object[]{message.getTitle(), messageId});
		this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_ADD, loginUser.getPkId(), ResourceType.MESSAGE_TYPE,
				messageId, loginUser.getLoginIP(), eventMsg));
		
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

	public void deleteMessage(String pkIds) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		if(StringUtils.isNotEmpty(pkIds)){
			String[] pkIdArr = pkIds.split(",");
			for(String pkId : pkIdArr){
				Message dbMessage = this.messageDAO.getMessageByPkId(Long.valueOf(pkId));
				if(dbMessage != null){
					dbMessage.setDeleteFlag(ResourceType.DELETE_YES);
					this.messageDAO.updateMessage(dbMessage);
					
					String eventMsg = MessageUtils.getProperties("event.message.delete", new Object[]{dbMessage.getTitle(), Long.valueOf(pkId)});
					this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_DELETE, dbMessage.getCreateUserId(),
							ResourceType.MESSAGE_TYPE, Long.valueOf(pkId), loginUser.getLoginIP(), eventMsg));
				}
			}
		}
	}

	public PaginationSupport getMyMessages(int start, int num, Long userId, Message message) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();

		PaginationSupport paginationSupport = this.messageDAO.getMyMessages(start, num,
                Long.valueOf(-1).equals(userId) ? loginUser.getPkId() : userId, message);
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

	public PaginationSupport listToAuditMessage(int start, int num, Message message, boolean flag) throws Exception {
		PaginationSupport pagination = this.messageDAO.listToAuditMessage(start, num, message, flag);
		List<Message> messages = pagination.getItems();
		if(CollectionUtils.isNotEmpty(messages)){
			for(Message msg : messages){
				User dbuser = this.userService.getUserById(msg.getCreateUserId());
				List<Reply> replys = this.replyService.getReplysByMessageId(msg.getPkId());
				
				msg.setCreateUser(dbuser);
				msg.setReplys(replys);
				
				if(msg.getAuditUserId() != null){
					User auditUser = this.userService.getUserById(msg.getAuditUserId());
					msg.setAuditUser(auditUser);
				}
			}
		}
		return pagination;
	}

	public void setAudit(Long messageId, String status) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		Message dbMessage = this.messageDAO.getMessageByPkId(messageId);
		String eventMsg = "";
		dbMessage.setAuditUserId(loginUser.getPkId());
		dbMessage.setAuditUsername(loginUser.getTruename());
		if(AUDIT_OK.equals(status)) {
			dbMessage.setIsAudit(ResourceType.AUDIT_YES);
			eventMsg = MessageUtils.getProperties("audit.yes", new Object[]{dbMessage.getTitle(), messageId});
		} else if(AUDIT_NO.equals(status)){
			dbMessage.setIsAudit(ResourceType.AUDIT_NO);
			eventMsg = MessageUtils.getProperties("audit.no", new Object[]{dbMessage.getTitle(), messageId});
		}
		
		this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_EDIT,
				dbMessage.getCreateUserId(), ResourceType.MESSAGE_TYPE, messageId, loginUser.getLoginIP(), eventMsg));
		this.messageDAO.updateMessage(dbMessage);
	}

    public Long getPkId() throws Exception {
        return this.genericHibernateDAO.genericId("SEQ_MESSAGE_MSG");
    }

	public PaginationSupport listFriendsMessage(LoginUser loginUser) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			return PaginationUtils.getNullPagination();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Long> friendIds = this.friendService.listFriendIds(loginUser.getPkId());
		
		String ids = "";
		for(Long id : friendIds){
			ids += "m.createUserId = " + id + " or ";
		}
		
		if(StringUtils.isNotEmpty(ids)){
			ids = ids.substring(0, ids.length() - 3);
		}
		
		String hql = "from Message m where (" + ids + ") and m.deleteFlag = :deleteFlag and m.isAudit = :isAudit" +
				" order by m.pkId desc";
		String countHql = "select count(*) " + hql;
		
		params.put("deleteFlag", ResourceType.DELETE_NO);
		params.put("isAudit", ResourceType.AUDIT_YES);
		PaginationSupport paginationSupport = this.genericHibernateDAO.getPaginationSupport(hql, countHql, 0, 10, params);
		
		List<Message> messages = paginationSupport.getItems();
		for(Message msg : messages){
			User user = this.userService.getUserById(msg.getCreateUserId());
			List<Reply> replys = this.replyService.getReplysByMessageId(msg.getPkId());
			
			msg.setCreateUser(user);
			msg.setReplys(replys);
		}
		
		return paginationSupport;
	}

}
