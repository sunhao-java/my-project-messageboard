package com.message.main.album.pojo;

/**
 * 相册和图片的关系表.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午01:08:07
 */
public class AlbumPhoto {
	private Long pkId;				//主键
	private Long albumId;			//相册ID
	private Long photoId;			//照片ID
	private Long deleteFlag;		//删除标识，0未删除；1已删除（为单个相册做图片回收站准备）

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
