package com.message.main.reply.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

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

	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean deleteReplyById(Long pkId) throws Exception {
		Reply dbReply = this.replyDAO.getReplyByPkId(pkId);
		if(dbReply != null){
			dbReply.setDeleteFlag(ResourceType.DELETE_YES);
			this.replyDAO.updateReply(dbReply);
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

}
