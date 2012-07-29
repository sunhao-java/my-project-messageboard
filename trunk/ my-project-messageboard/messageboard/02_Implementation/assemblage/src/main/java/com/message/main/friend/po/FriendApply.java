package com.message.main.friend.po;

import java.io.Serializable;
import java.util.Date;

import com.message.main.user.pojo.User;

/**
 * 好友申请.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-28 下午05:37:54
 */
public class FriendApply implements Serializable {
	private static final long serialVersionUID = -6965213840499532836L;

	private Long pkId;							//主键
	private Long applyUserId;					//申请人ID
	private Long inviteUserId;					//被邀请人ID
	private Date applyDate;						//申请时间
	private String message;						//申请时的附言
	private Integer result;						//被申请人的回复结果(0未回答1同意2拒绝)
	private String ip;							//申请人所在地IP
	private String remark;						//被申请人作出回答时的备注(拒绝时有备注)
	
	//VO
	private User applyUser;						//申请人
	private User inviteUser;					//被邀请人
	private Friend friend;						//如果此条申请已经被通过，则把friend放入

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

	public Long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(User applyUser) {
		this.applyUser = applyUser;
	}

	public User getInviteUser() {
		return inviteUser;
	}

	public void setInviteUser(User inviteUser) {
		this.inviteUser = inviteUser;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}
}
