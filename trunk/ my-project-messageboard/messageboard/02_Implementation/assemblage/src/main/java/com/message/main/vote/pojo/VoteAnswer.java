package com.message.main.vote.pojo;

import java.io.Serializable;
import java.util.Date;

import com.message.main.user.pojo.User;

/**
 * 投票的答案
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午11:24:33
 */
public class VoteAnswer implements Serializable {
	private static final long serialVersionUID = 5599834111340815189L;
	
	private Long pkId;					//主键
	private Long voteId;				//对应投票ID
	private Long answerUserId;			//回答者ID
	private String answerUserName;		//回答者NAME
	private Long answer;				//答案，voteOption的ID
	private Date answerDate;			//回答时间
	
	//VO Fields
	private User answerUser;			//回答者
	private String optionName;			//此条回答对应的选项
	private VoteComment comment;		//此条回答对应的评论

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

	public Long getAnswerUserId() {
		return answerUserId;
	}

	public void setAnswerUserId(Long answerUserId) {
		this.answerUserId = answerUserId;
	}

	public String getAnswerUserName() {
		return answerUserName;
	}

	public void setAnswerUserName(String answerUserName) {
		this.answerUserName = answerUserName;
	}

	public Long getAnswer() {
		return answer;
	}

	public void setAnswer(Long answer) {
		this.answer = answer;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	public User getAnswerUser() {
		return answerUser;
	}

	public void setAnswerUser(User answerUser) {
		this.answerUser = answerUser;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public VoteComment getComment() {
		return comment;
	}

	public void setComment(VoteComment comment) {
		this.comment = comment;
	}

}
