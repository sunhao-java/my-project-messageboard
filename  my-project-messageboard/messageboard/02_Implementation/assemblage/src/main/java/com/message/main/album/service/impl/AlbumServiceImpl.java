package com.message.main.album.service.impl;

import java.util.Date;
import java.util.List;

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

}
