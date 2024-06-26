package com.message.main.album.pojo;

import com.message.base.attachment.pojo.Attachment;

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
	private String characterMark;					//文字水印内容
	private String color;							//文字水印颜色(类似#000000)
	private Integer redColor;						//文字水印颜色(红red)
	private Integer greenColor;						//文字水印颜色(绿green)
	private Integer blueColor;						//文字水印颜色(蓝blue)
	private Integer fontSize;						//文字水印大小
	private Long attachmentId;				    	//图片水印对应的附件ID
	private Integer location;						//位置

    //VO
    private Attachment attachment;                 //附件

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

	public String getCharacterMark() {
		return characterMark;
	}

	public void setCharacterMark(String characterMark) {
		this.characterMark = characterMark;
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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

	public Integer getRedColor() {
		return redColor;
	}

	public void setRedColor(Integer redColor) {
		this.redColor = redColor;
	}

	public Integer getGreenColor() {
		return greenColor;
	}

	public void setGreenColor(Integer greenColor) {
		this.greenColor = greenColor;
	}

	public Integer getBlueColor() {
		return blueColor;
	}

	public void setBlueColor(Integer blueColor) {
		this.blueColor = blueColor;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
