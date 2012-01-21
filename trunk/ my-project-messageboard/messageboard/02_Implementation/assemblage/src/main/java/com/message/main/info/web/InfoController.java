package com.message.main.info.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.info.pojo.Info;
import com.message.main.info.service.InfoService;
import com.message.main.user.pojo.User;
import com.message.utils.SqlUtils;
import com.message.utils.resource.ResourceType;

/**
 * 留言板描述的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class InfoController extends MultiActionController {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoController.class);
	
	private WebInput in = null;
	private WebOutput out = null;
	
	private InfoService infoService;
	
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}

	/**
	 * 进入查看留言板描述的页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inViewInfoJsp(HttpServletRequest request, HttpServletResponse response){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("info", this.infoService.getNewestInfo());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("message.view.info", params);
	}
	
	/**
	 * 进入编辑留言板描述的界面
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	public ModelAndView inEditInfoJsp(HttpServletRequest request, HttpServletResponse response, Info info){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		params.put("loginUser", user);
		return new ModelAndView("message.edit.info", params);
	}
	
	/**
	 * 保存留言板描述
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveInfo(HttpServletRequest request, HttpServletResponse response, Info info) throws Exception{
		out = new WebOutput(request, response);
		JSONObject obj = new JSONObject();
		try {
			obj.put(ResourceType.AJAX_STATUS, this.infoService.saveInfo(info) == null ? 
						ResourceType.AJAX_FAILURE : ResourceType.AJAX_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
		}
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 留言板描述修改历史的页面
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	public ModelAndView inListInfoHistoryJsp(HttpServletRequest request, HttpServletResponse response, Info info){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		try {
			params.put("paginationSupport", this.infoService.getInfoHistroy(start, num, info));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("message.list.info.history", params);
	}
	
}
