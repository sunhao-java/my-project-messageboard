package com.message.main.reply.service.impl;

import com.message.main.reply.dao.ReplyDAO;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.utils.resource.ResourceType;

/**
 * 回复操作的serivce的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class ReplyServiceImpl implements ReplyService {
	private ReplyDAO replyDAO;

	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
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

}
