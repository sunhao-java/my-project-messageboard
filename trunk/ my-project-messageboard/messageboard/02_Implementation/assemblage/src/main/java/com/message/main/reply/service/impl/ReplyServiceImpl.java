package com.message.main.reply.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.i18n.MessageUtils;
import com.message.main.event.pojo.BaseEvent;
import com.message.main.event.service.EventService;
import com.message.main.reply.dao.ReplyDAO;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.resource.ResourceType;

/**
 * 回复操作的serivce的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class ReplyServiceImpl implements ReplyService {
	private ReplyDAO replyDAO;
	
	private UserService userService;
	private EventService eventService;

	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public boolean deleteReplyById(Long pkId, User user) throws Exception {
		Reply dbReply = this.replyDAO.getReplyByPkId(pkId);
		if(dbReply != null){
			dbReply.setDeleteFlag(ResourceType.DELETE_YES);
			this.replyDAO.updateReply(dbReply);
			String eventMsg = MessageUtils.getMessage("event.message.reply.delete", new Object[]{dbReply.getTitle(), dbReply.getPkId()});
			this.eventService.publishEvent(new BaseEvent(user.getPkId(), ResourceType.EVENT_DELETE, dbReply.getReplyUserId(), 
					ResourceType.REPLY_TYPE, dbReply.getPkId(), user.getLoginIP(), eventMsg));
			return true;
		}
		return false;
	}

	public List<Reply> getReplysByMessageId(Long messageId) throws Exception {
		List<Reply> replys = this.replyDAO.getReplysByMessageId(messageId);
		if(CollectionUtils.isNotEmpty(replys)){
			for(Reply reply : replys){
				User replyUser = this.userService.getUserById(reply.getReplyUserId());
				if(replyUser != null){
					reply.setReplyUser(replyUser);
				}
			}
		}
		return replys;
	}

	public void saveReply(Reply reply, User user) throws Exception {
		if(reply != null){
			reply.setDeleteFlag(ResourceType.DELETE_NO);
			reply.setReplyDate(new Date());
			
			Long pkId = this.replyDAO.saveReply(reply);
			
			String eventMsg = MessageUtils.getMessage("event.message.reply.add", new Object[]{reply.getTitle(), pkId});
			this.eventService.publishEvent(new BaseEvent(user.getPkId(), ResourceType.EVENT_ADD, reply.getReplyUserId(), 
					ResourceType.REPLY_TYPE, pkId, user.getLoginIP(), eventMsg));
		}
	}

}
