package com.message.main.friend.po;

import java.io.Serializable;

/**
 * 好友和分组的对应关系.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-31 下午11:09:49
 */
public class FriendGroupUser implements Serializable {
	private static final long serialVersionUID = 3001397294896550538L;

	private Long pkId;									//主键
	private Long groupId;								//分组的ID
	private Long friendId;								//好友的ID

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
}
