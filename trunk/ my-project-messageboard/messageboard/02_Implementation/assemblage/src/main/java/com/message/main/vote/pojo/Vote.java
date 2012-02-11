package com.message.main.vote.pojo;

import java.io.Serializable;
import java.util.Date;

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
	private Integer maxOption;				//最大选项数，如果是单选，为1；如果是多选，为选项数
	private Date endTime;				//投票截止时间，为空则永远有效，除非手动终止
	private Long createUserId;			//创建者ID
	private String createUsername;		//创建者姓名
	private Date createTime;			//创建时间
	private Long deleteFlag;			//删除标识：1已删除0未删除

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

}
