package com.message.main.album.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.message.base.pagination.PaginationSupport;
import com.message.base.web.WebOutput;
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
	 * 根据主键获取照片
	 * 
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	Photo loadPhoto(Long pkId) throws Exception;
	
	/**
	 * 根据主键和获取类型取得照片
	 * 
	 * @param pkId			主键
	 * @param type			previous取上一张;next取下一张;''取本张
	 * @return
	 * @throws Exception
	 */
	Photo loadPhoto(Long pkId, String type) throws Exception;
	
	/**
	 * 更新照片的描述
	 * 
	 * @param summary		描述
	 * @param pkId			照片ID
	 * @throws Exception
	 */
	boolean updatePhotoSummary(String summary, Long pkId) throws Exception;
	
	/**
	 * 设置相册封面
	 * 
	 * @param photoId		照片ID
	 * @param albumId		相册ID
	 * @return
	 * @throws Exception
	 */
	boolean setCover(Long photoId, Long albumId) throws Exception;
	
	/**
	 * 根据照片主键和相册主键删除照片
	 * 
	 * @param photoId		照片主键
	 * @param albumId		相册主键
	 * @return
	 * @throws Exception
	 */
	boolean deletePhoto(Long photoId, Long albumId) throws Exception;
	
	/**
	 * 根据照片主键和相册主键删除照片
	 * 
	 * @param photoId		照片主键
	 * @param toAlbumId		目标相册主键
	 * @param fromAlbumId	原相册ID
	 * @return
	 * @throws Exception
	 */
	boolean movePhoto(Long photoId, Long toAlbumId, Long fromAlbumId) throws Exception;
	
	/**
	 * 导出相册
	 * 
	 * @param albumId		相册ID
	 * @param photos		此相册的所有图片
	 * @param out			输出
	 * @throws Exception
	 */
	void exportAlbum(Long albumId, List<Photo> photos, WebOutput out) throws Exception;
	
	/**
	 * 保存水印配置
	 * 
	 * @param markType
	 * @param content
	 * @param location
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	boolean saveConfig(String markType, String content, Integer location, MultipartFile multipartFile) throws Exception;
	
}
