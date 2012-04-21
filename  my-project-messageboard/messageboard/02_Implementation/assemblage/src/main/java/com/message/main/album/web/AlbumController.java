package com.message.main.album.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.SqlUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.album.pojo.Album;
import com.message.main.album.service.AlbumService;
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
		int num = in.getInt("num", 3);
		int start = SqlUtils.getStartNum(in, num);
		Long userId = in.getLong("userId");
		PaginationSupport pagination = this.albumService.getAlbumList(userId, start, num);
		
		params.put("pagination", pagination);
		return new ModelAndView("album.list", params);
	}
	
	/**
	 * 进入新建相册的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView createAlbum(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("album.new");
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
		
		params.put("album", album);
		return new ModelAndView("photo.list", params);
	}
}
