package com.message.main.message.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.user.pojo.User;
import com.message.utils.PaginationSupport;
import com.message.utils.SqlUtils;
import com.message.utils.StringUtils;
import com.message.utils.WebInput;
import com.message.utils.WebOutput;
import com.message.utils.resource.ResourceType;

public class MessageController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private WebInput in = null;
	private WebOutput out = null;
	
	private MessageService messageService;
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 列出所有留言
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listMessage(HttpServletRequest request, HttpServletResponse response, Message message){
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		int num = in.getInt("num", 3);
		int start = SqlUtils.getStartNum(in, num);
		PaginationSupport paginationSupport = null;
		try {
			paginationSupport = this.messageService.getAllMessages(start, num, message);
			User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
			params.put("loginUser", user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		params.put("paginationSupport", paginationSupport);
		return new ModelAndView("message.list", params);
	}
	
	/**
	 * 进入发表留言的页面
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 */
	public ModelAndView inPublishMessageJsp(HttpServletRequest request, HttpServletResponse response, Message message){
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		params.put("loginUser", user);
		return new ModelAndView("message.publish", params);
	}
	
	/**
	 * 保存留言
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveMessage(HttpServletRequest request, HttpServletResponse response, Message message) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONObject params = new JSONObject();
		Long user_pkId = in.getLong("user_id", 0L);
		String ip = in.getClientIP();
		message.setIp(ip);
		Long pkId = null;
		try {
			pkId = this.messageService.saveMessage(message, user_pkId);
			params.put(ResourceType.AJAX_STATUS, pkId == null ? ResourceType.AJAX_FAILURE : ResourceType.AJAX_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
		}
		out.toJson(params);
		return null;
	}
	
	/**
	 * 为管理员列出所有留言(简洁版的留言)
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public ModelAndView listMessageForAdmin(HttpServletRequest request, HttpServletResponse response, Message message){
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		out = new WebOutput(request, response);
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		PaginationSupport paginationSupport = null;
		try {
			paginationSupport = this.messageService.getAllMessages(start, num, message);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		params.put("paginationSupport", paginationSupport);
		return new ModelAndView("message.listMsg.forAdmin", params);
	}
	
	/**
	 * 查看单条留言
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 */
	public ModelAndView viewMessage(HttpServletRequest request, HttpServletResponse response, Message message){
		Map<String, Object> params = new HashMap<String, Object>();
		if(message.getPkId() != null){
			try {
				params.put("message", this.messageService.getMessageByPkId(message.getPkId()));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return new ModelAndView("message.viewMessage", params);
	}
	
	/**
	 * 删除留言（可以删除一条也可批量删除）
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView deleteMessage(HttpServletRequest request, HttpServletResponse response, Message message) throws Exception{
		JSONObject params = new JSONObject();
		in = new WebInput(request);
		out = new WebOutput(request, response);
		String pkIds = in.getString("pkIds", StringUtils.EMPTY);
		try {
			this.messageService.deleteMessage(pkIds);
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			e.printStackTrace();
		}
		out.toJson(params);
		return null;
	}
	
	/**
	 * 登录用户查看留言详情
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 */
	public ModelAndView inDetailJsp(HttpServletRequest request, HttpServletResponse response, Message message){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("message", this.messageService.getMessageByPkId(message.getPkId()));
			params.put("loginUser", (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return new ModelAndView("message.detail.jsp", params);
	}

}
