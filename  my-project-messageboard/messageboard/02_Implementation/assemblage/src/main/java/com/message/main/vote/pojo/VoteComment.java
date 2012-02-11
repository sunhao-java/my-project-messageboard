package com.message.main.vote.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 投票的评论
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午11:50:12
 */
public class VoteComment implements Serializable {
	private static final long serialVersionUID = -2790167385758574937L;

	private Long pkId;					//主键
	private Long voteId;				//对应投票的ID
	private String commentContent;		//评论内容
	private Long commentUserId;			//评论人ID
	private String commentUserName;		//评论人NAME
	private Date commentDate;			//评论日期
	private Long deleteFlag;			//删除标识：1已删除0未删除

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Long getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(Long commentUserId) {
		this.commentUserId = commentUserId;
	}

	public String getCommentUserName() {
		return commentUserName;
	}

	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
