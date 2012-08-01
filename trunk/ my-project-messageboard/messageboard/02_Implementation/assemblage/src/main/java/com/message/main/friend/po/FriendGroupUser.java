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
	
	private Long pkId;
	private Long groupId;
	private Long friendId;
}
