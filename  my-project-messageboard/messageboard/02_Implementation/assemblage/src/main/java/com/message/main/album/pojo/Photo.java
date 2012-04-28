package com.message.main.album.pojo;

import java.io.Serializable;
import java.util.Date;

import com.message.base.attachment.pojo.Attachment;

/**
 * 照片实体.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午12:51:23
 */
public class Photo implements Serializable {
	private static final long serialVersionUID = 6741830230396950924L;
	
	private Long pkId;						//主键
	private String photoName;				//照片名称
	private Long fileId;					//对应文件的ID
	private Long owerId;					//拥有者ID
	private Date createDate;				//创建时间
	private String summary;					//照片描述
	private Long deleteFlag;				//删除标识，0未删除；1已删除
	
	//VO Fields
	private Attachment attachment;			//对应的上传文件

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getOwerId() {
		return owerId;
	}

	public void setOwerId(Long owerId) {
		this.owerId = owerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

}
