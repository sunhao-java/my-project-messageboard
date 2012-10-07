package com.message.main.album.service.impl;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.message.base.Constants;
import com.message.base.attachment.pojo.Attachment;
import com.message.base.attachment.service.AttachmentService;
import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.utils.ImageUtils;
import com.message.base.utils.ObjectUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.album.dao.AlbumDAO;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.AlbumConfig;
import com.message.main.album.pojo.AlbumPhoto;
import com.message.main.album.pojo.Photo;
import com.message.main.album.service.AlbumService;
import com.message.main.friend.service.FriendService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.user.service.UserService;

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
	/**
	 * 好友模块service.
	 */
	private FriendService friendService;
	/**
	 * 用户操作的service
	 */
	private UserService userService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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
		if(album.getOwerId() != null){
			album.setOwer(this.userService.getUserById(album.getOwerId()));
		}
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
            //水印
            makeWaterMark(attachment);
            
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

    private void makeWaterMark(Attachment attachment) throws Exception {
        if(attachment == null || StringUtils.isEmpty(attachment.getFilePath())) {
            logger.warn("given attachment is null!");
            return;
        }
        AlbumConfig albumConfig = this.getAlbumConfig();
        if(albumConfig == null){
            logger.debug("this user is not config water mark!");
            return;
        }
        String filePath = attachment.getFilePath();
        if(ResourceType.CHARACTER_MASK.equals(albumConfig.getMaskType())){
        	Color color = new Color(albumConfig.getRedColor(), albumConfig.getGreenColor(), albumConfig.getBlueColor());
            //文字
            ImageUtils.addStringMark(filePath, albumConfig.getCharacterMark(), color, albumConfig.getFontSize(), albumConfig.getLocation());
        } else {
            //图片
            ImageUtils.addImageMark(filePath, albumConfig.getAttachment().getFilePath(), albumConfig.getLocation());
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

	public void exportAlbum(Long albumId, List<Photo> photos, WebOutput out) throws Exception {
		String zipName = "album" + albumId + ".zip";
		byte[] read = new byte[1024 * 5];
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipName));
		
		for(Photo p : photos){
			Attachment attachment = p.getAttachment();
			File file = new File(attachment.getFilePath());
			InputStream photoStream = new FileInputStream(file);
			ZipEntry entry = new ZipEntry(attachment.getFileName());
			zipOut.putNextEntry(entry);
			int length;
			while((length = photoStream.read(read)) > 0){
				zipOut.write(read, 0, length);
			}
			
			photoStream.close();
			zipOut.closeEntry();
		}
		
		zipOut.close();
		
		out.setContentType("application/zip;charset=UTF-8");
		out.getResponse().setHeader("Content-Disposition", "attachment;filename=\"" + new String(zipName.getBytes("utf-8"), "iso8859-1") + "\"");
		File zipfile = new File(zipName);
		
		FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(zipfile)), new BufferedOutputStream(out.getResponse().getOutputStream()));
	}

	public boolean saveConfig(String markType, String content, String color, Integer size, Integer location, MultipartFile multipartFile) 
					throws Exception {
		if(StringUtils.isEmpty(markType)){
			logger.error("markType is required!");
			return false;
		}
		
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		AlbumConfig config = new AlbumConfig();
		config.setUserId(loginUser.getPkId());
		config.setLocation(location);
		if(ResourceType.CHARACTER_MARK_STRING.equals(markType)){
			config.setCharacterMark(content);
			config.setAttachmentId(Long.valueOf(-1));
			config.setMaskType(ResourceType.CHARACTER_MASK);
			config.setColor(color);
			int[] rgb = ImageUtils.converColorToRGB(color);
			if(rgb != null && rgb.length == 3){
				config.setRedColor(rgb[0]);
				config.setGreenColor(rgb[1]);
				config.setBlueColor(rgb[2]);
			}
			config.setFontSize(size);
			this.albumDAO.saveEntity(config);
			
			return config.getPkId() == null ? false : true;
		} else {
			config.setMaskType(ResourceType.IMAGE_MASK);
			config.setCharacterMark(StringUtils.EMPTY);
			
			this.albumDAO.saveEntity(config);
			if(config.getPkId() == null)
				return false;
			
			Map<String, Object> uploadParams = new HashMap<String, Object>();
			uploadParams.put(Constants.MAP_KEY_RESOURCE_ID, config.getPkId());
			uploadParams.put(Constants.MAP_KEY_RESOURCE_TYPE, ResourceType.RESOURCE_TYPE_MARK);
			uploadParams.put(Constants.MAP_KEY_UPLOAD_ID, loginUser.getPkId());
			
			Attachment attachment = this.attachmentService.genericUpload(multipartFile, uploadParams);
			
			config.setAttachmentId(attachment.getPkId());
			this.albumDAO.updateEntity(config);
			
			return true;
		}
	}

	public AlbumConfig getAlbumConfig() throws Exception {
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		if(loginUser == null){
			logger.error("maybe not login!");
			return null;
		}
		
		AlbumConfig albumConfig = this.albumDAO.getAlbumConfig(loginUser.getPkId());
        if(albumConfig != null){
            Attachment attachment = this.attachmentService.loadAttachment(albumConfig.getAttachmentId());
            albumConfig.setAttachment(attachment);
        }

        return albumConfig;
	}

    public boolean saveEdit(Long pkId, String markType, String content, String color, Integer size, Integer location, MultipartFile multipartFile)
    		throws Exception {
        if(pkId == null || Long.valueOf(-1).equals(pkId)){
            logger.error("pkId must be given!");
            return false;
        }
        AlbumConfig albumConfig = this.albumDAO.loadAlbumConfig(pkId);
        if(albumConfig == null) {
            logger.warn("can't get any config with pkId: '{}'", pkId);
            return false;
        }
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        albumConfig.setLocation(location);
        if(ResourceType.CHARACTER_MARK_STRING.equals(markType)){
            //文字水印
            albumConfig.setCharacterMark(content);
            albumConfig.setMaskType(ResourceType.CHARACTER_MASK);
            albumConfig.setColor(color);
            int[] rgb = ImageUtils.converColorToRGB(color);
			if(rgb != null && rgb.length == 3){
				albumConfig.setRedColor(rgb[0]);
				albumConfig.setGreenColor(rgb[1]);
				albumConfig.setBlueColor(rgb[2]);
			}
			albumConfig.setFontSize(size);
            this.albumDAO.updateEntity(albumConfig);
            return true;
        } else {
			Map<String, Object> uploadParams = new HashMap<String, Object>();
			uploadParams.put(Constants.MAP_KEY_RESOURCE_ID, albumConfig.getPkId());
			uploadParams.put(Constants.MAP_KEY_RESOURCE_TYPE, ResourceType.RESOURCE_TYPE_MARK);
			uploadParams.put(Constants.MAP_KEY_UPLOAD_ID, loginUser.getPkId());
			Attachment attachment = this.attachmentService.genericUpload(multipartFile, uploadParams);

            albumConfig.setMaskType(ResourceType.IMAGE_MASK);
			albumConfig.setAttachmentId(attachment.getPkId());
			this.albumDAO.updateEntity(albumConfig);
            return true;
        }
    }

	public boolean deleteMask(Long userId) throws Exception {
		if(ObjectUtils.equals(userId, Long.valueOf(-1))){
			LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
			if(loginUser == null){
				logger.error("maybe not login!");
				return false;
			} else {
				userId = loginUser.getPkId();
			}
		}
		
		return this.albumDAO.deleteMask(userId);
	}

	public PaginationSupport listMyFriendAlbums(LoginUser loginUser, int start, int num) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			return PaginationUtils.getNullPagination();
		}
		List<Long> friendIds = this.friendService.listFriendIds(loginUser.getPkId());
		
		List<Long> viewFlag = Arrays.asList(new Long[]{ResourceType.LOOK_ONLY_FRIENDS, ResourceType.LOOK_ALL_PROPLE});
		PaginationSupport paginationSupport = this.albumDAO.getAlbumList(friendIds, viewFlag, start, num);
		List<Album> albums = paginationSupport.getItems();
		
		for(int i = 0; i < albums.size(); i++){
			albums.set(i, this.loadAlbum(albums.get(i).getPkId()));
		}
		
		return paginationSupport;
	}

	public PaginationSupport getViewAlbums(LoginUser loginUser, Long uid, int start, int num) throws Exception {
		if(loginUser == null || uid == null || Long.valueOf(-1).equals(uid)){
			logger.debug("given params maybe has null data!");
			return PaginationUtils.getNullPagination();
		}
		//两人是好友
		boolean isFriend = this.friendService.isFriend(loginUser.getPkId(), uid);
		List<Long> viewFlag = new ArrayList<Long>();
		if(isFriend){
			//是好友,则"好友可见","所有人可见"
			viewFlag.clear();
			viewFlag.addAll(Arrays.asList(new Long[]{ResourceType.LOOK_ONLY_FRIENDS, ResourceType.LOOK_ALL_PROPLE}));
		} else {
			//不是好友,则仅可见"所有人可见"
			viewFlag.clear();
			viewFlag.add(ResourceType.LOOK_ALL_PROPLE);
		}
		PaginationSupport ps = this.albumDAO.getAlbumList(Arrays.asList(uid), viewFlag, start, num);
		List<Album> albums = ps.getItems();
		
		for(int i = 0; i < albums.size(); i++){
			albums.set(i, this.loadAlbum(albums.get(i).getPkId()));
		}
		
		return ps;
	}

}
