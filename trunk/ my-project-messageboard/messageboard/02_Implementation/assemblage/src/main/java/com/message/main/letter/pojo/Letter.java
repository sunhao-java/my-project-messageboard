package com.message.main.letter.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.message.main.user.pojo.User;

/**
 * 站内信主题部分信息.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:19:34
 */
@Table(name = "t_message_letter")
public class Letter implements Serializable {
	private static final long serialVersionUID = -8272309584150963399L;
	
	@Id
	@GeneratedValue(generator = "seq_message_letter")
	private Long pkId;								//主键
	@Column
	private String title;							//站内信标题
	@Column
	private String content;							//站内信内容
	@Column
	private Date sendTime;							//发送时间
	@Column
	private Long creatorId;							//站内信创建者ID
	@Column
	private Long isReply;							//是否是站内信回复(1站内信0站内信回复)
	@Column
	private Long deleteFlag;						//删除的标识(1删除0未删)
	
	//VO Fields
	private User creator;							//站内信创建者
	private List<LetterUserRelation> relations;		//站内信与站内信人员关系

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getIsReply() {
		return isReply;
	}

	public void setIsReply(Long isReply) {
		this.isReply = isReply;
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

	public List<LetterUserRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<LetterUserRelation> relations) {
		this.relations = relations;
	}
}
