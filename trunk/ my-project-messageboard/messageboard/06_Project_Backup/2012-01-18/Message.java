package com.message.main.message.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.message.main.reply.pojo.Reply;
import com.message.main.user.pojo.User;

/**
 * 留言实体
 * @author sunhao(sunhao.java@gmail.com)
 */
public class Message implements Serializable {
	private static final long serialVersionUID = -4074801287320043496L;
	
	private Long pkId;									//唯一标识
	private String title;								//标题
	private String ip;									//IP地址
	private String content;								//留言内容
	private Date createDate;							//留言时间
	private Long createUserId;							//对应的留言者ID
	private String createUsername;						//对应的留言者truename
	private Long deleteFlag;							//删除标识0未删除1已删除
	
	//VO Field
	private User createUser;							//VO字段，发表留言者
	private List<Reply> replys;							//VO字段，此条留言的回复
	private Date beginTime;
	private Date endTime;
	
	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
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

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
