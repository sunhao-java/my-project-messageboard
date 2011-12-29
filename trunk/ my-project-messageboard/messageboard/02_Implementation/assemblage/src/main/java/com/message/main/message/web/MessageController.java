package com.message.main.message.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.message.pojo.Message;
import com.message.main.message.service.MessageService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.WebInput;
import com.message.utils.resource.ResourceType;

public class MessageController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private WebInput in = null;
	
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
		int start = in.getInt("start", 0);
		int num = ResourceType.PAGE_NUM;
		List<Message> messages = null;
		try {
			messages = this.messageService.getAllMessages(start, num, message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		params.put("messages", messages);
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

}
