package com.message.main.album.pojo;

import java.io.Serializable;

/**
 * 相册配置对应实体类.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-6-13 上午06:44:04
 */
public class AlbumConfig implements Serializable {
	private static final long serialVersionUID = 5152002453925292548L;
	
	private Long pkId;								//主键
	private Long userId;							//一个用户对应一个配置
	private Integer maskType;						//水印的类型，0无水印；1文字水印；2图片水印
	private String characterMask;					//文字水印内容
	private Long attachmentId;						//图片水印对应的附件ID
	private Integer location;						//位置

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMaskType() {
		return maskType;
	}

	public void setMaskType(Integer maskType) {
		this.maskType = maskType;
	}

	public String getCharacterMask() {
		return characterMask;
	}

	public void setCharacterMask(String characterMask) {
		this.characterMask = characterMask;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

}
