package com.message.main.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
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
	private UserPrivacyService userPrivacyService;

	public void setUserPrivacyService(UserPrivacyService userPrivacyService) {
		this.userPrivacyService = userPrivacyService;
	}

	@SuppressWarnings("unused")
	private WebInput in = null;
	private WebOutput out = null;

	/**
	 * 进入用户隐私设置界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inPrivacySetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("userPrivacy", this.userPrivacyService.getUserPrivacy(null));
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
		JSONObject params = new JSONObject();
        
        this.userPrivacyService.saveUserPrivacy(userPrivacy);
        params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
		return null;
	}
}
