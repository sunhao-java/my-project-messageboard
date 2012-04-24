package com.message.main.album.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.RandomUtils;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.album.pojo.Album;
import com.message.main.album.service.AlbumService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.resource.ResourceType;

/**
 * 相册web控制层.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午01:28:47
 */
public class AlbumController extends ExtMultiActionController {
//	private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);
	private WebInput in = null;
	private WebOutput out = null;
	
	/**
	 * 相册service接口.
	 */
	private AlbumService albumService;
	
	public void setAlbumService(AlbumService albumService) {
		this.albumService = albumService;
	}

	/**
	 * 获取当前登录者所有相册列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		//每页显示10个相册
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		Long userId = in.getLong("userId");
		PaginationSupport pagination = this.albumService.getAlbumList(userId, start, num);
		
		params.put("paginationSupport", pagination);
		return new ModelAndView("album.list", params);
	}
	
	/**
	 * 进入新建相册的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView formbackAlbum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		Long albumId = in.getLong("albumId");
		
		if(albumId != null){
			Album album = this.albumService.loadAlbum(albumId);
			params.put("album", album);
		}
		return new ModelAndView("album.new", params);
	}
	
	/**
	 * 保存相册
	 * 
	 * @param request
	 * @param response
	 * @param album
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveAlbum(HttpServletRequest request, HttpServletResponse response, Album album) throws Exception{
		out = new WebOutput(request, response);
		
		this.albumService.saveOrUpdateAlbum(album);
		JSONObject params = new JSONObject();
		params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 列出某个相册的所有图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView listPhotos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		Long albumId = in.getLong("albumId");
		Album album = this.albumService.loadAlbum(albumId);
		
		//获取这个相册中所有图片
		//每页显示12张照片
		int num = in.getInt("num", 12);
		int start = SqlUtils.getStartNum(in, num);
		PaginationSupport ps = this.albumService.getPhotosByAlbum(albumId, start, num);
		LoginUser lu = AuthContextHelper.getAuthContext().getLoginUser();
		PaginationSupport albums = this.albumService.getAlbumList(lu.getPkId(), 0, 4);
		
		String randomTag = RandomUtils.genRandomString(5);
		
		params.put("randomTag", randomTag);
		params.put("paginationSupport", ps);
		params.put("albums", albums);
		params.put("album", album);
		params.put("resourceType", ResourceType.RESOURCE_TYPE_PHOTO);
		return new ModelAndView("photo.list", params);
	}
	
	/**
	 * 删除相册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView deleteAlbum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONObject params = new JSONObject();
		Long albumId = in.getLong("albumId");
		boolean result = this.albumService.deleteAlbum(albumId);
		
		params.put(ResourceType.AJAX_STATUS, result ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 上传图片的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
        Map<String, Object> params = new HashMap<String, Object>();
        
        /**
         * 从页面获取一些参数
         */
        Long uploadId = in.getLong(ResourceType.MAP_KEY_UPLOAD_ID, Long.valueOf(-1));					//上传者
        Long resourceId = in.getLong(ResourceType.MAP_KEY_RESOURCE_ID, Long.valueOf(-1));				//资源ID，即相册的ID
        Integer resourceType = in.getInt(ResourceType.MAP_KEY_RESOURCE_TYPE, Integer.valueOf(-1));		//资源类型
        
        /**
         * 将HttpServletRequest强制转换成spring的MultipartHttpServletRequest
         */
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        Map uploadParams = new HashMap();
        uploadParams.put(ResourceType.MAP_KEY_RESOURCE_ID, resourceId);
        uploadParams.put(ResourceType.MAP_KEY_RESOURCE_TYPE, resourceType);
        uploadParams.put(ResourceType.MAP_KEY_UPLOAD_ID, uploadId);
        //上传文件
        this.albumService.uploadPhoto(multipartRequest, uploadParams);
        
        params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 保存照片的描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveSummary(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject params = new JSONObject();
		
		String summary = in.getString("summary", StringUtils.EMPTY);
		Long pkId = in.getLong("photoId");
		params.put(ResourceType.AJAX_STATUS, this.albumService.updatePhotoSummary(summary, pkId) ? 
				ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 设置相册封面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView setCover(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject params = new JSONObject();
		
		Long photoId = in.getLong("photoId");
		Long albumId = in.getLong("albumId");
		params.put(ResourceType.AJAX_STATUS, this.albumService.setCover(photoId, albumId) ? 
				ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView showAllMine(HttpServletRequest request, HttpServletResponse response) throws Exception {
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject params = new JSONObject();
		
		List<Album> albums = this.albumService.getAlbumList(null, -1, -1).getItems();
		params.put("albums", albums);
		
		out.toJson(params);
		return null;
	}
}
