package com.message.main.friend.po;

import java.io.Serializable;
import java.util.Date;

import com.message.main.user.pojo.User;

/**
 * 好友分组.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-31 下午11:06:12
 */
public class FriendGroup implements Serializable {
	private static final long serialVersionUID = -2689710043637050249L;
	
	private Long pkId;							//主键
	private String name;						//组名
	private Long ower;							//是谁创建的
	private Date createTime;					//创建时间
	private Integer deleteFlag;					//是否删除的标识0未删除，1已删除
	
	//VO
	private User owner;							//创建者

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOwer() {
		return ower;
	}

	public void setOwer(Long ower) {
		this.ower = ower;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
