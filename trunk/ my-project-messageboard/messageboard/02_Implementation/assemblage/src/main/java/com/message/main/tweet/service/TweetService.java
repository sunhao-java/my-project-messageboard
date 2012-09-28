package com.message.main.tweet.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.login.pojo.LoginUser;
import com.message.main.tweet.pojo.Tweet;

/**
 * 吐槽service.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-22 下午06:57:59
 */
public interface TweetService {
	
	/**
	 * 获取当前登录者所有吐槽
	 * 
	 * @param tweet				查询头
	 * @param loginUser			当前登录者
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listTweets(Tweet tweet, LoginUser loginUser, int start, int num) throws Exception;
	
	/**
	 * 保存或者删除吐槽
	 * 
	 * @param pkId				主键(如果是编辑,则有值,新增是null)
	 * @param content			内容
	 * @param loginUser			当前登录者
	 * @return
	 * @throws Exception
	 */
	Long saveOrUpdateTweet(Long pkId, String content, LoginUser loginUser) throws Exception;
	
	/**
	 * 获取当个吐槽
	 * 
	 * @param pkId			吐槽ID
	 * @return
	 * @throws Exception
	 */
	Tweet loadTweet(Long pkId) throws Exception;
	
	/**
	 * 删除吐槽
	 * 
	 * @param pkId			吐槽ID
	 * @return
	 * @throws Exception
	 */
	boolean deleteTweet(Long pkId) throws Exception;
	
	/**
	 * 获取好友的吐槽
	 * 
	 * @param loginUser
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listFriendTweet(LoginUser loginUser, int start, int num) throws Exception;
	
	/**
	 * 获取某个人的吐槽
	 * 
	 * @param uid
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getTweetsByUId(Long uid, int start, int num) throws Exception;
}
