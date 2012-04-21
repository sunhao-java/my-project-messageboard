package com.message.main.album.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 相册实体.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午12:51:13
 */
public class Album implements Serializable {
	private static final long serialVersionUID = -8922107934036282494L;
	
	private Long pkId;					//主键
	private String albumName;			//相册名称
	private Long owerId;				//拥有者ID
	private Date createTime;			//创建时间
	private String summary;				//相册描述
	private String cover;				//相册封面
	private Long viewFlag;				//可见标识(1:所有人可见；2:本人可见；3:注册的人可见)
	private Long deleteFlag;			//删除标识，0未删除；1已删除
	
	//VO Fields
	private Integer photoCount;			//此相册中照片数量

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public Long getOwerId() {
		return owerId;
	}

	public void setOwerId(Long owerId) {
		this.owerId = owerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(Long viewFlag) {
		this.viewFlag = viewFlag;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Integer photoCount) {
		this.photoCount = photoCount;
	}

}
