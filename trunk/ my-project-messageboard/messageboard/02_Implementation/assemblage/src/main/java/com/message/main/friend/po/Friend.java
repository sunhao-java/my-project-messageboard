package com.message.main.friend.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友实体.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:48:24
 */
public class Friend implements Serializable {
	private static final long serialVersionUID = -4966800270641046452L;
	
	private Long pkId;								//主键
	private Long applyUserId;						//申请者ID
	private Long descUserId;						//目标用户ID
	private Date applyTime;							//申请时间
	private Integer agree;							//目标用户是否同意(0未回答1同意2拒绝)
	private Date beFriendTime;						//成为好友的时间

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}

	public Long getDescUserId() {
		return descUserId;
	}

	public void setDescUserId(Long descUserId) {
		this.descUserId = descUserId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getAgree() {
		return agree;
	}

	public void setAgree(Integer agree) {
		this.agree = agree;
	}

	public Date getBeFriendTime() {
		return beFriendTime;
	}

	public void setBeFriendTime(Date beFriendTime) {
		this.beFriendTime = beFriendTime;
	}
}
