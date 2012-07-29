package com.message.main.friend.po;

import java.io.Serializable;
import java.util.Date;

import com.message.main.user.pojo.User;

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
	private Long applyId;							//申请表ID
	private Long userId;							//我的ID
	private Long friendId;							//好友的ID
	private Date beFriendDate;						//成为好友的时间
	
	//VO
	private User friendUser;						//好友

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public Date getBeFriendDate() {
		return beFriendDate;
	}

	public void setBeFriendDate(Date beFriendDate) {
		this.beFriendDate = beFriendDate;
	}

	public User getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
	}

}
