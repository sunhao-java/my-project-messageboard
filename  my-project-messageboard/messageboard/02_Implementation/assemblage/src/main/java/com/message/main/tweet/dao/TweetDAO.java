package com.message.main.tweet.dao;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.tweet.pojo.Tweet;

/**
 * 吐槽DAO.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-22 下午06:58:44
 */
public interface TweetDAO {
	
	/**
	 * 获取当前登录者所有吐槽
	 * 
	 * @param tweet				查询头
	 * @param loginUserId		当前登录者
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listTweets(Tweet tweet, Long loginUserId, int start, int num) throws Exception;
	
	/**
	 * 保存吐槽对象
	 * 
	 * @param tweet		吐槽对象
	 * @return
	 * @throws Exception
	 */
	Tweet saveTweet(Tweet tweet) throws Exception;
	
	/**
	 * 更新吐槽对象
	 * 
	 * @param tweet		吐槽对象
	 * @throws Exception
	 */
	void updateTweet(Tweet tweet) throws Exception;
	
	/**
	 * 删除吐槽对象
	 * 
	 * @param pkId		吐槽对象ID
	 * @return
	 * @throws Exception
	 */
	int deleteTweet(Long pkId) throws Exception;
	
	/**
	 * 获取吐槽对象
	 * 
	 * @param pkId		吐槽对象ID
	 * @return
	 * @throws Exception
	 */
	Tweet loadTweet(Long pkId) throws Exception;
	
	/**
	 * 根据用户ID获取吐槽
	 *  
	 * @param userIds	用户ID
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listTweetByUserId(List<Long> userIds, int start, int num) throws Exception;
	
}
