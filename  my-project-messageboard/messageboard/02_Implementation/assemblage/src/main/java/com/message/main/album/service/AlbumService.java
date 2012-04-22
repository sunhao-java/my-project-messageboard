package com.message.main.album.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.album.pojo.Album;

/**
 * 相册service接口.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午01:29:14
 */
public interface AlbumService {
	
	/**
	 * 保存或者修改相册
	 * 
	 * @param album			相册
	 * @throws Exception
	 */
	void saveOrUpdateAlbum(Album album) throws Exception;
	
	/**
	 * 获取所有相册分页对象
	 * 
	 * @param userId		获取相册的用户
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getAlbumList(Long userId, int start, int num) throws Exception;
	
	/**
	 * 根据主键获得相册对象
	 * 
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	Album loadAlbum(Long pkId) throws Exception;
	
	/**
	 * 根据主键删除相册
	 * 
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	boolean deleteAlbum(Long pkId) throws Exception;

}
