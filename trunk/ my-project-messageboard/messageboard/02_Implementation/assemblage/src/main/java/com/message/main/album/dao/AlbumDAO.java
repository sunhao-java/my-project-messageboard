package com.message.main.album.dao;

import com.message.base.pagination.PaginationSupport;
import com.message.main.album.pojo.Album;


/**
 * 相册DAO接口.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午01:29:38
 */
public interface AlbumDAO {
	
	/**
	 * 保存实体
	 * 
	 * @param obj			实体
	 * @throws Exception
	 */
	void saveEntity(Object entity) throws Exception;
	
	/**
	 * 获取某个用户的所有相册分页对象
	 * 
	 * @param userId		用户ID
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

}
