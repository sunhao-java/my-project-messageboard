package com.message.main.letter.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.letter.pojo.Letter;
import com.message.main.letter.service.LetterService;
import com.message.main.login.pojo.LoginUser;

/**
 * 站内信的controller.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:20:19
 */
public class LetterController {
	
	@Autowired
	private LetterService letterService;
	
	/**
	 * 进入收件箱
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 */
	public ModelAndView inbox(WebInput in, WebOutput out, LoginUser loginUser){
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("current", "inbox");
		return new ModelAndView("letter.inbox", params);
	}
	
	/**
	 * 进入写站内信页面
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 */
	public ModelAndView compose(WebInput in, WebOutput out, LoginUser loginUser){
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("current", "compose");
		return new ModelAndView("letter.compose", params);
	}
	
	/**
	 * 发送站内信
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView send(WebInput in, WebOutput out, LoginUser loginUser, Letter letter) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		String receiverIds = in.getString("receiverIds", StringUtils.EMPTY);
		
		boolean res = this.letterService.send(receiverIds, letter, loginUser);
		
		params.put(ResourceType.AJAX_STATUS, !res ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
}
