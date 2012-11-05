package com.message.main.letter.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.message.main.user.pojo.User;

/**
 * 站内信与人员之间的信息.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:34:49
 */
@Table(name = "t_message_letter_user")
public class LetterUserRelation implements Serializable {
	private static final long serialVersionUID = -3908832930879022375L;
	
	@Id
	@GeneratedValue(generator = "seq_message_letter")
	private Long pkId;					//主键
	@Column
	private Long receiverId;			//接收者ID
	@Column
	private Date acceptTime;			//查看时间
	@Column
	private Integer read;				//是否是已读(1已读0未读)
	@Column
	private Long deleteFlag;			//删除的标识(1删除0未删)
	@Column
	private Long letterId;				//站内信主题信息ID
	
	//VO
	private Letter letter;				//对应的站内信主体信息
	private User receiver;				//接收者

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
}
