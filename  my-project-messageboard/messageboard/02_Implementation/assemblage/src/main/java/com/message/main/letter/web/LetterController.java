package com.message.main.letter.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.login.pojo.LoginUser;

/**
 * 站内信的controller.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:20:19
 */
public class LetterController {
	
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
}
