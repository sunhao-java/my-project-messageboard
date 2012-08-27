package com.message.main.tweet.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.login.pojo.LoginUser;
import com.message.main.tweet.service.TweetService;

/**
 * 吐槽controller.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-22 下午06:57:41
 */
public class TweetController extends SimpleController {
	
	private TweetService tweetService;

	public void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	/**
	 * 获取当前登录人所有吐槽
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		PaginationSupport ps = this.tweetService.listTweets(null, loginUser, start, num);
		params.put("paginationSupport", ps);
		
		return new ModelAndView("", params);
	}
	
	/**
	 * 发布动弹
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView save(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		
		String content = in.getString("content", StringUtils.EMPTY);
		Long pkId = this.tweetService.saveOrUpdateTweet(null, content, loginUser);
		
		params.put(ResourceType.AJAX_STATUS, pkId != null ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 获取好友的动弹
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView listFriendsTweet(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		PaginationSupport ps = this.tweetService.listFriendTweet(loginUser, start, num);
		params.put("pagination", ps);
		
		return new ModelAndView("", params);
	}
	
}
