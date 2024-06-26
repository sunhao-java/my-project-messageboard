package com.message.main.vote.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.message.main.user.pojo.User;

/**
 * 投票的主题信息
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午11:24:06
 */
public class Vote implements Serializable {
	private static final long serialVersionUID = 8162840475707999550L;

	private Long pkId;					//主键
	private String question;			//投票问题
	private Long type;					//1单选2多选
	private Integer maxOption;			//最大选项数，如果是单选，为1；如果是多选，为选项数
	private Long setEndTime;			//是否设置截止时间，为1不设置2设置
	private Date endTime;				//投票截止时间，为空则永远有效，除非手动终止
	private Long createUserId;			//创建者ID
	private String createUsername;		//创建者姓名
	private Date createTime;			//创建时间
	private Long deleteFlag;			//删除标识：1已删除0未删除
	
	//VO Fields
	private List<VoteOption> voteOptions;		//投票的选项
	private List<VoteAnswer> answers;			//此投票的回答
	private User createUser;					//创建者
	private int participantNum;					//共有多少人参加
	private Long isVote;						//查看人是否已经投过票 1：投过票    2：没有
	private List<String> myAnswer;				//我的回答
	private VoteComment comment;				//我的投票的评论
	private List<VoteComment> comments;			//此投票的所有回答
	private String isOverTime;					//是否已经过期,0没过期，1过期

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Integer getMaxOption() {
		return maxOption;
	}

	public void setMaxOption(Integer maxOption) {
		this.maxOption = maxOption;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getSetEndTime() {
		return setEndTime;
	}

	public void setSetEndTime(Long setEndTime) {
		this.setEndTime = setEndTime;
	}

	public List<VoteOption> getVoteOptions() {
		return voteOptions;
	}

	public void setVoteOptions(List<VoteOption> voteOptions) {
		this.voteOptions = voteOptions;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public int getParticipantNum() {
		return participantNum;
	}

	public void setParticipantNum(int participantNum) {
		this.participantNum = participantNum;
	}

	public Long getIsVote() {
		return isVote;
	}

	public void setIsVote(Long isVote) {
		this.isVote = isVote;
	}

	public List<String> getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(List<String> myAnswer) {
		this.myAnswer = myAnswer;
	}

	public List<VoteAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<VoteAnswer> answers) {
		this.answers = answers;
	}

	public VoteComment getComment() {
		return comment;
	}

	public void setComment(VoteComment comment) {
		this.comment = comment;
	}

	public List<VoteComment> getComments() {
		return comments;
	}

	public void setComments(List<VoteComment> comments) {
		this.comments = comments;
	}

	public String getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(String isOverTime) {
		this.isOverTime = isOverTime;
	}

}
