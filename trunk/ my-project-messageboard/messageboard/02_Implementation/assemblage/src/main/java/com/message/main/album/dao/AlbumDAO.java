package com.message.main.album.dao;

import java.util.List;
import java.util.Map;

import com.message.base.pagination.PaginationSupport;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.AlbumConfig;
import com.message.main.album.pojo.Photo;


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
	 * @param entity			实体
	 * @throws Exception
	 */
	<T> T saveEntity(T entity) throws Exception;
	
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
	 * 获取多个用户的相册分页对象
	 * 
	 * @param userId		用户ID
	 * @param viewFlag
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getAlbumList(List<Long> friendIds, List<Long> viewFlag, int start, int num) throws Exception;
	
	/**
	 * 根据主键获得相册对象
	 * 
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	Album loadAlbum(Long pkId) throws Exception;
	
	/**
	 * 更新实体
	 * 
	 * @param entity		实体
	 * @throws Exception
	 */
	void updateEntity(Object entity) throws Exception;
	
	/**
	 * 根据原生SQL更新
	 * 
	 * @param table				表名
	 * @param columnParams		字段
	 * @param whereParams		条件
	 * @return				更新的数据库行数
	 * @throws Exception
	 */
	int updateBySQL(String table, Map<String, Object> columnParams, Map<String, Object> whereParams) throws Exception;
	
	/**
	 * 根据原生SQL更新
	 * 
	 * @param table			表名
	 * @param column		字段名
	 * @param value			更新后的值
	 * @param pkValue		主键（列名为pk_id）
	 * @return				更新的数据库行数
	 * @throws Exception
	 */
	int updateBySQL(String table, String column, Object value, Object pkValue) throws Exception;
	
	/**
	 * 获取某个相册中的照片张数
	 * 
	 * @param albumId
	 * @return
	 * @throws Exception
	 */
	int getPhotoCount(Long albumId) throws Exception;
	
	/**
	 * 获取某个相册中照片的分页对象
	 * 
	 * @param albumId
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getPhotosByAlbum(Long albumId, int start, int num) throws Exception;
	
	/**
	 * 根据主键获得照片对象
	 * 
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	Photo loadPhoto(Long pkId) throws Exception;
	
	/**
	 * 根据主键、相册ID、获取类型获得上一张或者下一张图片ID
	 * 
	 * @param pkId
	 * @param albumId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	Long loadPhoto(Long pkId, Long albumId, String type) throws Exception;
	
	/**
	 * 获取某个用户的相册配置
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	AlbumConfig getAlbumConfig(Long userId) throws Exception;

    /**
     * 根据主键获取配置
     * 
     * @param pkId
     * @return
     * @throws Exception
     */
    AlbumConfig loadAlbumConfig(Long pkId) throws Exception;
    
    /**
     * 删除水印
     * 
     * @param userId
     * @return
     */
    boolean deleteMask(Long userId) throws Exception;

}
