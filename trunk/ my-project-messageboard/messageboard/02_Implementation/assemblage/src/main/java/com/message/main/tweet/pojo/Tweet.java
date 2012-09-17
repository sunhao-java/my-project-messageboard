package com.message.main.tweet.pojo;

import java.io.Serializable;
import java.util.Date;

import com.message.main.user.pojo.User;

/**
 * 吐槽对象.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-22 下午06:57:04
 */
public class Tweet implements Serializable {
	private static final long serialVersionUID = -4255763094605618164L;

	private Long pkId;				//主键
	private String content;			//内容
	private Date createTime;		//创建时间
	private Long creatorId;			//创建者ID
	private Long deleteFlag;		//删除标识位(0未删除1已删除)
	
	//VO
	private User creator;			//创建者
    private int replyNum;          //回复条数

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
}
