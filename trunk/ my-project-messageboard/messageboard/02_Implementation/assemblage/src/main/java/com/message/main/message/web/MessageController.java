package com.message.main.message.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.resource.ResourceType;

public class MessageController extends ExtMultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	/**
	 * 待审核的
	 */
	private static final boolean GET_TO_AUDIT = Boolean.TRUE;
	/**
	 * 审核未通过的
	 */
	private static final boolean GET_NO_AUDIT = Boolean.FALSE;
	
	private WebInput in = null;
	private WebOutput out = null;
	
	private MessageService messageService;
	
	private UserService userService;
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public ModelAndView inPublishMessageJsp(HttpServletRequest request, HttpServletResponse response, Message message) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
        Long pkId = this.messageService.getPkId();
		params.put("loginUser", user);
        params.put("pkId", pkId);
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
		User user = this.userService.getUserById(user_pkId);
		String ip = in.getClientIP();
		message.setIp(ip);
		user.setLoginIP(ip);
		Long pkId = null;
		try {
			pkId = this.messageService.saveMessage(message, user);
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
	 * @param message
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
		params.put("truename", in.getString("createUser.truename", StringUtils.EMPTY));
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
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		String pkIds = in.getString("pkIds", StringUtils.EMPTY);
		user.setLoginIP(in.getClientIP());
		try {
			this.messageService.deleteMessage(pkIds, user);
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
		String flag = in.getString("flag", StringUtils.EMPTY);
		try {
			Message dbMessage = this.messageService.getMessageByPkId(message.getPkId());
			params.put("message", dbMessage);
			params.put("loginUser", (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION));
			params.put("flag", flag);
			params.put("messageCount", this.messageService.getLoginUserMessageCount(dbMessage.getCreateUserId()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return new ModelAndView("message.detail.jsp", params);
	}
	
	/**
	 * 列出我的留言
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inListMyMessageJsp(HttpServletRequest request, HttpServletResponse response, Message message){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		Long viewWhoId = in.getLong("viewWhoId");
		try {
			if(viewWhoId != null){
				user = new User(viewWhoId);
				params.put("customer", "true");
				params.put("viewwhoname", this.userService.getUserById(viewWhoId).getTruename());
				params.put("viewWhoId", viewWhoId);
			}
			PaginationSupport paginationSupport = this.messageService.getMyMessages(start, num, user, message);
			params.put("paginationSupport", paginationSupport);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("message.list.mine", params);
	}
	
	/**
	 * 列出所有待审核的留言
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 */
	public ModelAndView listToAuditMessage(HttpServletRequest request, HttpServletResponse response, Message message){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		try {
			params.put("pagination", this.messageService.listToAuditMessage(start, num, message, GET_TO_AUDIT));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("message.list.to.audit", params);
	}
	
	/**
	 * 列出所有未审核通过的留言
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 */
	public ModelAndView listAuditNoMessage(HttpServletRequest request, HttpServletResponse response, Message message){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		try {
			params.put("pagination", this.messageService.listToAuditMessage(start, num, message, GET_NO_AUDIT));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("message.list.no.audit", params);
	}
	
	/**
	 * 对留言进行审批
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView setAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject params = new JSONObject();
		in = new WebInput(request);
		out = new WebOutput(request, response);
		Long messageId = in.getLong("messageId", 0L);
		String status = in.getString("status", StringUtils.EMPTY);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		try {
			this.messageService.setAudit(messageId, status, user);
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			logger.error(e.getMessage(), e);
		}
		out.toJson(params);
		return null;
	}
	
}
