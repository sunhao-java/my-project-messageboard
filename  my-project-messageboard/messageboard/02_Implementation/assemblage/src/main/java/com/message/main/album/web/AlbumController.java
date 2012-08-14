package com.message.main.album.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.Constants;
import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.SimpleController;
import com.message.base.utils.RandomUtils;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.AlbumConfig;
import com.message.main.album.pojo.Photo;
import com.message.main.album.service.AlbumService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;

/**
 * 相册web控制层.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-20 上午01:28:47
 */
public class AlbumController extends SimpleController {
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
		AlbumConfig ac = this.albumService.getAlbumConfig();
		PaginationSupport pagination = this.albumService.getAlbumList(userId, start, num);
		
		params.put("paginationSupport", pagination);
		//水印是否存在,0不存在,1存在
		params.put("maskExist", ac == null ? 0 : 1);
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
		boolean visit = in.getBoolean("visit", false);
		
		//获取这个相册中所有图片
		//每页显示12张照片
		int num = in.getInt("num", 12);
		int start = SqlUtils.getStartNum(in, num);
		PaginationSupport ps = this.albumService.getPhotosByAlbum(albumId, start, num);
		LoginUser lu = AuthContextHelper.getAuthContext().getLoginUser();
		PaginationSupport albums = this.albumService.getAlbumList(lu.getPkId(), 0, 4);
		
		String randomTag = RandomUtils.randomString(5, true, true, true);
		
		params.put("randomTag", randomTag);
		params.put("paginationSupport", ps);
		params.put("albums", albums);
		params.put("album", album);
		params.put("resourceType", ResourceType.RESOURCE_TYPE_PHOTO);
		params.put("visit", visit);
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
	public ModelAndView uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
        Map<String, Object> params = new HashMap<String, Object>();
        
        /**
         * 从页面获取一些参数
         */
        Long uploadId = in.getLong(Constants.MAP_KEY_UPLOAD_ID, Long.valueOf(-1));					//上传者
        Long resourceId = in.getLong(Constants.MAP_KEY_RESOURCE_ID, Long.valueOf(-1));				//资源ID，即相册的ID
        Integer resourceType = in.getInt(Constants.MAP_KEY_RESOURCE_TYPE, Integer.valueOf(-1));		//资源类型
        
        /**
         * 将HttpServletRequest强制转换成spring的MultipartHttpServletRequest
         */
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        Map uploadParams = new HashMap();
        uploadParams.put(Constants.MAP_KEY_RESOURCE_ID, resourceId);
        uploadParams.put(Constants.MAP_KEY_RESOURCE_TYPE, resourceType);
        uploadParams.put(Constants.MAP_KEY_UPLOAD_ID, uploadId);
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
	 * 显示我所有的相册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showAllMine(HttpServletRequest request, HttpServletResponse response) throws Exception {
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject params = new JSONObject();
		
		List<Album> albums = this.albumService.getAlbumList(null, -1, -1).getItems();
		params.put("albums", albums);
		
		out.toJson(params);
		return null;
	}
	
	/**
	 * 照片详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView showDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		out = new WebOutput(request, response);
		in = new WebInput(request);
		Long photoId = in.getLong("photoId");
		Long albumId = in.getLong("albumId");
		String type = in.getString("type", StringUtils.EMPTY);
		boolean visit = in.getBoolean("visit", false);
		
		Photo photo = this.albumService.loadPhoto(photoId, type + "#" + albumId);
		Album album = this.albumService.loadAlbum(albumId);
		
		Long tmpId = null;
		if(StringUtils.isNotEmpty(type)){
			tmpId = photo.getPkId();
		} else {
			tmpId = photoId;
		}
		
		Photo previous = this.albumService.loadPhoto(tmpId, "previous#" + albumId);
		Photo next = this.albumService.loadPhoto(tmpId, "next#" + albumId);
		
		List<Album> albums = this.albumService.getAlbumList(null, -1, -1).getItems();
		
		params.put("album", album);
		params.put("photo", photo);
		params.put("previous", previous);
		params.put("next", next);
		params.put("albums", albums);
		params.put("visit", visit);
		
		return new ModelAndView("photo.detail", params);
	}
	
	/**
	 * 删除照片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject params = new JSONObject();
		out = new WebOutput(request, response);
		in = new WebInput(request);
		Long photoId = in.getLong("photoId");
		Long albumId = in.getLong("albumId");
		
		boolean result = this.albumService.deletePhoto(photoId, albumId);
		params.put(ResourceType.AJAX_STATUS, result ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 移动照片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView movePhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject params = new JSONObject();
		out = new WebOutput(request, response);
		in = new WebInput(request);
		Long photoId = in.getLong("photoId");
		Long toAlbumId = in.getLong("toAlbumId");
		Long fromAlbumId = in.getLong("fromAlbumId");
		
		params.put(ResourceType.AJAX_STATUS, this.albumService.movePhoto(photoId, toAlbumId, fromAlbumId) 
				? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 导出相册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		
		Long albumId = in.getLong("albumId");
		List<Photo> photos = this.albumService.getPhotosByAlbum(albumId, -1, -1).getItems();
		
		this.albumService.exportAlbum(albumId, photos, out);
		return null;
	}
	
	/**
	 * 进入配置水印的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView inConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		out = new WebOutput(request, response);
		
		AlbumConfig config = this.albumService.getAlbumConfig();
		if(config != null){
			params.put("albumConfig", this.albumService.getAlbumConfig());
		}
		
		return new ModelAndView("album.config", params);
	}
	
	/**
	 * 保存水印配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveConfig(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		Map<String, Object> params = new HashMap<String, Object>();
		String type = in.getString("mark", StringUtils.EMPTY);
		String content = in.getString("characterContent", StringUtils.EMPTY);
		Integer location = in.getInt("location", Integer.valueOf(3));
		String color = in.getString("color", "#000000");
		Integer size = in.getInt("fontSize", 20);
		
		boolean status = false;
		if(ResourceType.CHARACTER_MARK_STRING.equals(type)){
			//文字水印
			status = this.albumService.saveConfig(type, content, color, size, location, null);
		} else {
			//图片水印
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("imageMark");
			
			status = this.albumService.saveConfig(type, StringUtils.EMPTY, StringUtils.EMPTY, 0, location, multipartFile);
		}
		
		params.put(ResourceType.AJAX_STATUS, status ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}

    /**
     * 保存更新后的配置
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        in = new WebInput(request);
        out = new WebOutput(request, response);
        Map<String, Object> params = new HashMap<String, Object>();
        Long pkId = in.getLong("pkId");
        String type = in.getString("mark", StringUtils.EMPTY);
        String content = in.getString("characterContent", StringUtils.EMPTY);
        Integer location = in.getInt("location", Integer.valueOf(3));
        String color = in.getString("color", "#000000");
		Integer size = in.getInt("fontSize", 20);
        boolean status = false;
        if(ResourceType.CHARACTER_MARK_STRING.equals(type)){
            //文字水印
            status = this.albumService.saveEdit(pkId, type, content, color, size, location, null);
        } else {
            //图片水印
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("imageMark");

            status = this.albumService.saveEdit(pkId, type, StringUtils.EMPTY, StringUtils.EMPTY, 0, location, multipartFile);
        }

        params.put(ResourceType.AJAX_STATUS, status ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
        out.toJson(params);
        return null;
    }
    
    /**
     * 删除水印
     * 
     * @param in
     * @param out
     * @return
     * @throws Exception
     */
    public ModelAndView deleteWaterMaker(WebInput in, WebOutput out) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	Long userId = in.getLong("userId", Long.valueOf(-1));
    	
    	params.put(ResourceType.AJAX_STATUS, this.albumService.deleteMask(userId) ? 
    			ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	out.toJson(params);
    	return null;
    }
    
    /**
     * 列出我的好友的相册
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception 
     */
    public ModelAndView listFriendAlbums(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		PaginationSupport paginationSupport = this.albumService.listMyFriendAlbums(loginUser, start, num);

		params.put("paginationSupport", paginationSupport);
    	return new ModelAndView("album.friend.list", params);
    }
	
}
