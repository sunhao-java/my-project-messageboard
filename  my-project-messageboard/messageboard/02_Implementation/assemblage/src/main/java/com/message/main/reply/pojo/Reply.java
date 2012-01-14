package com.message.main.reply.pojo;

import java.io.Serializable;
import java.util.Date;

import com.message.main.message.pojo.Message;
import com.message.main.user.pojo.User;

/**
 * 回复实体
 * @author sunhao(sunhao.java@gmail.com)
 */
public class Reply implements Serializable{
	private static final long serialVersionUID = -8666303216648235947L;
	
	private Long pkId;				//唯一标识
	private String title;			//标题
	private String replyContent;	//回复内容
	private Date replyDate;			//回复时间
	private Long replyUserId;		//回复者ID
	private Long deleteFlag; 		//删除标识0未删除1已删除
	private Long messageId;			//此条回复对应的留言ID
	
	//VO Fileds
	private User replyUser;			//回复的用户
	private Message message;		//此条回复对应的留言实体

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public Long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public User getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

}
