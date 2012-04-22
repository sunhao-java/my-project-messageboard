package com.message.main.album.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.pagination.PaginationSupport;
import com.message.main.album.dao.AlbumDAO;
import com.message.main.album.pojo.Album;
import com.message.main.album.service.AlbumService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
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

}
