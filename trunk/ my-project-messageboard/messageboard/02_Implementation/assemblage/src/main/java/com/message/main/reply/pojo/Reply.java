package com.message.main.reply.pojo;

import java.io.Serializable;
import java.util.Date;

import com.message.main.message.pojo.Message;

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
	private Message message;		//回复的留言
	private Long deleteFlag; 		//删除标识0未删除1已删除

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

}
