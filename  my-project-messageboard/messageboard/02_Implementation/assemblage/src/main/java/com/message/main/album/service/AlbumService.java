package com.message.main.album.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartRequest;

import com.message.base.pagination.PaginationSupport;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.Photo;

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
	
	/**
	 * 上传图片
	 * 
	 * @param request		上传图片的request
	 * @param params
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	void uploadPhoto(MultipartRequest request, Map params) throws Exception;
	
	/**
	 * 获取某个相册中照片的分页对象
	 * 
	 * @param albumId		相册主键
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getPhotosByAlbum(Long albumId, int start, int num) throws Exception;
	
	/**
	 * 根据主键删除照片
	 * 
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	Photo loadPhoto(Long pkId) throws Exception;

}
