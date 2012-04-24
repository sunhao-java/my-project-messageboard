package com.message.main.album.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartRequest;

import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.StringUtils;
import com.message.main.album.dao.AlbumDAO;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.AlbumPhoto;
import com.message.main.album.pojo.Photo;
import com.message.main.album.service.AlbumService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.upload.pojo.UploadFile;
import com.message.main.upload.service.GenericUploadService;
import com.message.resource.ResourceType;

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
	private GenericUploadService genericUploadService;

	public void setGenericUploadService(GenericUploadService genericUploadService) {
		this.genericUploadService = genericUploadService;
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public void uploadPhoto(MultipartRequest request, Map params) throws Exception {
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		List<UploadFile> files = this.genericUploadService.genericUploads(request, params);
		
		for(UploadFile file : files){
			Photo photo = new Photo();
			photo.setCreateDate(new Date());
			photo.setDeleteFlag(ResourceType.DELETE_NO);
			photo.setFileId(file.getPkId());
			photo.setOwerId(loginUser.getPkId());
			photo.setPhotoName(file.getFileName());
			photo.setSummary(StringUtils.EMPTY);
			
			photo = this.albumDAO.saveEntity(photo);
			
			AlbumPhoto ap = new AlbumPhoto();
			ap.setAlbumId((Long)params.get(ResourceType.MAP_KEY_RESOURCE_ID));
			ap.setPhotoId(photo.getPkId());
			ap.setDeleteFlag(ResourceType.DELETE_NO);
			this.albumDAO.saveEntity(ap);
		}
	}

	@SuppressWarnings("unchecked")
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
		
		Long fileId = p.getFileId();
		UploadFile file = this.genericUploadService.loadFile(fileId);
		p.setFile(file);
		return p;
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
		if(photo == null || photo.getFile() == null){
			logger.error("get photo is null or get photo has not UploadFile by given photoId:'{}'", photoId);
			return false;
		}
		
		String path = photo.getFile().getFilePath();
		int i = this.albumDAO.updateBySQL("t_message_album", "cover", path, albumId);
		
		return i == 1;
	}

}