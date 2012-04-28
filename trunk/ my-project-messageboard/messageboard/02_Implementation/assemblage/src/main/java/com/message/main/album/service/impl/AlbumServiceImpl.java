package com.message.main.album.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartRequest;

import com.message.base.Constants;
import com.message.base.attachment.pojo.Attachment;
import com.message.base.attachment.service.AttachmentService;
import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.album.dao.AlbumDAO;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.AlbumPhoto;
import com.message.main.album.pojo.Photo;
import com.message.main.album.service.AlbumService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;

/**
 * 相册service实现.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午01:30:22
 */
public class AlbumServiceImpl implements AlbumService {
	private static final Logger logger = LoggerFactory.getLogger(AlbumServiceImpl.class);
	
	/**
	 * 默认相册的封面
	 */
	private static final String DEFAULT_ALBUM_COVER = "/image/default.png";
	
	/**
	 * 相册DAO实现.
	 */
	private AlbumDAO albumDAO;
	/**
	 * 上传文件的通用类的接口
	 */
	private AttachmentService attachmentService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public void saveOrUpdateAlbum(Album album) throws Exception {
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		if(album.getPkId() == null){
			//新建
			album.setCreateTime(new Date());
			album.setDeleteFlag(ResourceType.DELETE_NO);
			album.setOwerId(loginUser.getPkId());
			album.setCover(DEFAULT_ALBUM_COVER);
			
			//开始保存
			this.albumDAO.saveEntity(album);
		} else {
			//编辑
			this.albumDAO.updateEntity(album);
		}
	}

	public PaginationSupport getAlbumList(Long userId, int start, int num) throws Exception {
		if(userId == null){
			LoginUser lu = AuthContextHelper.getAuthContext().getLoginUser();
			userId = lu.getPkId();
		}
		
		PaginationSupport ps = this.albumDAO.getAlbumList(userId, start, num);
		List<Album> albums = ps.getItems();
		
		for(int i = 0; i < albums.size(); i++){
			albums.set(i, this.loadAlbum(albums.get(i).getPkId()));
		}
		return ps;
	}

	public Album loadAlbum(Long pkId) throws Exception {
		Album album = this.albumDAO.loadAlbum(pkId);
		
		//相册中照片数量
		int photoCount = this.albumDAO.getPhotoCount(pkId);
		album.setPhotoCount(photoCount);
		return album;
	}

	public boolean deleteAlbum(Long pkId) throws Exception {
		if(pkId == null){
			logger.error("you give no album pkId!");
			return false;
		}
		
		int result = this.albumDAO.updateBySQL("t_message_album", "delete_flag", ResourceType.DELETE_YES, pkId);
		if(result == 1)
			return true;
		else
			return false;
	}

	public void uploadPhoto(MultipartRequest request, Map params) throws Exception {
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		List<Attachment> attachments = this.attachmentService.genericUploads(request, params);
		
		for(Attachment attachment : attachments){
			Photo photo = new Photo();
			photo.setCreateDate(new Date());
			photo.setDeleteFlag(ResourceType.DELETE_NO);
			photo.setFileId(attachment.getPkId());
			photo.setOwerId(loginUser.getPkId());
			String fileName = attachment.getFileName();
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			photo.setPhotoName(fileName);
			photo.setSummary(StringUtils.EMPTY);
			
			photo = this.albumDAO.saveEntity(photo);
			
			AlbumPhoto ap = new AlbumPhoto();
			ap.setAlbumId((Long)params.get(Constants.MAP_KEY_RESOURCE_ID));
			ap.setPhotoId(photo.getPkId());
			ap.setDeleteFlag(ResourceType.DELETE_NO);
			this.albumDAO.saveEntity(ap);
		}
	}

	public PaginationSupport getPhotosByAlbum(Long albumId, int start, int num) throws Exception {
		PaginationSupport ps = this.albumDAO.getPhotosByAlbum(albumId, start, num);
		List<Photo> photos = ps.getItems();
		
		for(int i = 0; i < photos.size(); i++){
			photos.set(i, this.loadPhoto(photos.get(i).getPkId()));
		}
		
		return ps;
	}

	public Photo loadPhoto(Long pkId) throws Exception {
		Photo p = this.albumDAO.loadPhoto(pkId);
		
		if(p != null){
			Long fileId = p.getFileId();
			Attachment attachment = this.attachmentService.loadAttachment(fileId);
			p.setAttachment(attachment);
		}
		return p;
	}
	
	public Photo loadPhoto(Long pkId, String type) throws Exception {
		if(pkId == null || StringUtils.isEmpty(type)){
			logger.error("pkId and type is required!");
			return null;
		}
		
		String tmp[] = type.split("#");
		if(tmp == null || tmp.length != 2){
			logger.error("can't get type and albumId! given type is '{}'", type);
			return null;
		}
		
		type = tmp[0];
		Long albumId = Long.valueOf(tmp[1]);
		Photo photo = null;
		if(StringUtils.isEmpty(type)){
			logger.debug("get photo pkId is '{}'", pkId);
			photo = this.loadPhoto(pkId);
		} else if(StringUtils.contain(type, new String[]{"previous", "next"})){
			logger.debug("get photo pkId is '{}' previous photo", pkId);
			photo = this.loadPhoto(this.albumDAO.loadPhoto(pkId, albumId, type));
		} else {
			logger.error("given type is only 'previous', 'next' or ''!you give type is '{}'", type);
		}
		
		return photo;
	}

	public boolean updatePhotoSummary(String summary, Long pkId) throws Exception {
		if(StringUtils.isEmpty(summary) || pkId == null){
			logger.error("params summary, pkId is required!");
			return false;
		}
		
		int result = this.albumDAO.updateBySQL("t_message_photo", "summary", summary, pkId);
		return result == 1;
	}

	public boolean setCover(Long photoId, Long albumId) throws Exception {
		if(photoId == null || albumId == null){
			logger.error("params photoId, albumId is required!");
			return false;
		}
		Photo photo = this.loadPhoto(photoId);
		if(photo == null || photo.getAttachment() == null){
			logger.error("get photo is null or get photo has not UploadFile by given photoId:'{}'", photoId);
			return false;
		}
		
		String path = photo.getAttachment().getFilePath();
		int i = this.albumDAO.updateBySQL("t_message_album", "cover", path, albumId);
		
		return i == 1;
	}

	public boolean deletePhoto(Long photoId, Long albumId) throws Exception {
		if(photoId == null || albumId == null){
			logger.error("you give no photo Id or no album Id!");
			return false;
		}
		
		Map<String, Object> columnParams = new HashMap<String, Object>();
		Map<String, Object> whereParams = new HashMap<String, Object>();
		columnParams.put("delete_flag", ResourceType.DELETE_YES);
		whereParams.put("photo_id", photoId);
		whereParams.put("album_id", albumId);
		
		int result = this.albumDAO.updateBySQL("t_message_album_photo", columnParams, whereParams);
		if(result == 1){
			whereParams.clear();
			whereParams.put("pk_id", photoId);
			
			result = this.albumDAO.updateBySQL("t_message_photo", columnParams, whereParams);
			
			return result == 1;
		}
		else
			return false;
	}

	public boolean movePhoto(Long photoId, Long toAlbumId, Long fromAlbumId) throws Exception {
		if(photoId == null || toAlbumId == null || fromAlbumId == null){
			logger.error("you give no photo Id or no toAlbum Id or no fromAlbum Id!");
			return false;
		}
		
		Map<String, Object> columnParams = new HashMap<String, Object>();
		Map<String, Object> whereParams = new HashMap<String, Object>();
		columnParams.put("album_id", toAlbumId);
		whereParams.put("photo_id", photoId);
		whereParams.put("album_id", fromAlbumId);
		
		int size = this.albumDAO.updateBySQL("t_message_album_photo", columnParams, whereParams);
		
		return size == 1;
	}

}
