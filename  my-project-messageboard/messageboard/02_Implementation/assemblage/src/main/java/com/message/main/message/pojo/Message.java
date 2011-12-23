package com.message.main.message.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private Date crateDate;								//留言时间
	private Set<Reply> replys = new HashSet<Reply>();	//对应的回复
	private Long createUserId;							//对应的留言者ID
	private Long deleteFlag;							//删除标识0未删除1已删除
	
	//VO Field
	private User createUser;							//VO字段，发表留言者

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

	public Date getCrateDate() {
		return crateDate;
	}

	public void setCrateDate(Date crateDate) {
		this.crateDate = crateDate;
	}

	public Set<Reply> getReplys() {
		return replys;
	}

	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
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

}
