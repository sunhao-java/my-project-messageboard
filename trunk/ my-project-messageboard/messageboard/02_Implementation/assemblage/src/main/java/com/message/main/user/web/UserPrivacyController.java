package com.message.main.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.user.pojo.User;
import com.message.main.user.pojo.UserPrivacy;
import com.message.main.user.service.UserPrivacyService;
import com.message.resource.ResourceType;

/**
 * 用户隐私设置的controller
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午09:00:45
 */
public class UserPrivacyController extends ExtMultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(UserPrivacyController.class);

	private UserPrivacyService userPrivacyService;

	public void setUserPrivacyService(UserPrivacyService userPrivacyService) {
		this.userPrivacyService = userPrivacyService;
	}

	private WebInput in = null;
	private WebOutput out = null;

	/**
	 * 进入用户隐私设置界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inPrivacySetting(HttpServletRequest request, HttpServletResponse response) {
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		try {
			params.put("userPrivacy", this.userPrivacyService.getUserPrivacyByUser(user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("user.privacy.setting", params);
	}
	
	/**
	 * 保存用户隐私配置
	 * 
	 * @param request
	 * @param response
	 * @param userPrivacy
	 * @return
	 * @throws Exception
	 */
	public ModelAndView savePrivacy(HttpServletRequest request, HttpServletResponse response, UserPrivacy userPrivacy) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		JSONObject params = new JSONObject();
		try {
			this.userPrivacyService.saveUserPrivacy(userPrivacy, user);
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