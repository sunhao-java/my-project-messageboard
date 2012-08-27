package com.message.main.tweet.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.main.ResourceType;
import com.message.main.friend.service.FriendService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.tweet.dao.TweetDAO;
import com.message.main.tweet.pojo.Tweet;
import com.message.main.tweet.service.TweetService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * 吐槽service实现.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-22 下午06:58:20
 */
public class TweetServiceImpl implements TweetService {
	private static final Logger logger = LoggerFactory.getLogger(TweetServiceImpl.class);
	
	private TweetDAO tweetDAO;
	private UserService userService;
	private FriendService friendService;

	public void setTweetDAO(TweetDAO tweetDAO) {
		this.tweetDAO = tweetDAO;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	public PaginationSupport listTweets(Tweet tweet, LoginUser loginUser, int start, int num) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			logger.debug("not login....");
			return PaginationUtils.getNullPagination();
		}
		
		PaginationSupport ps = this.tweetDAO.listTweets(tweet, loginUser.getPkId(), start, num);
		List<Tweet> tweets = ps.getItems();
		for(Tweet t : tweets){
			handleTweet(t);
		}
		
		return ps;
	}

	public Long saveOrUpdateTweet(Long pkId, String content, LoginUser loginUser) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			logger.debug("not login.....");
			return Long.valueOf(-1);
		}
		
		if(pkId == null){
			//新建
			Tweet tweet = new Tweet();
			tweet.setContent(content);
			tweet.setCreateTime(new Date());
			tweet.setCreatorId(loginUser.getPkId());
			tweet.setDeleteFlag(ResourceType.DELETE_NO);
			
			tweet = this.tweetDAO.saveTweet(tweet);
			
			return tweet.getPkId() != null ? tweet.getPkId() : Long.valueOf(-1);
		} else {
			//编辑
			Tweet tweet = this.loadTweet(pkId);
			
			if(tweet != null)
				tweet.setContent(content);
			
			this.tweetDAO.updateTweet(tweet);
			
			return pkId;
		}
	}

	public Tweet loadTweet(Long pkId) throws Exception {
		if(pkId == null){
			logger.debug("the pkId is null.....");
			return null;
		}
		
		Tweet tweet = this.tweetDAO.loadTweet(pkId);
		handleTweet(tweet);
		
		return tweet;
	}
	
	/**
	 * 对从数据库取出的对象进行处理
	 * 
	 * @param tweet				吐槽
	 * @throws Exception
	 */
	private void handleTweet(Tweet tweet) throws Exception {
		if(tweet != null) {
			//放入创建者对象
			if(tweet.getCreatorId() != null){
				User user = this.userService.getUserById(tweet.getCreatorId());
				if(user != null)
					tweet.setCreator(user);
			}
		}
	}

	public boolean deleteTweet(Long pkId) throws Exception {
		if(pkId == null){
			logger.debug("the pkId is null.....");
			return false;
		}
		
		return this.tweetDAO.deleteTweet(pkId) == 1;
	}

	public PaginationSupport listFriendTweet(LoginUser loginUser, int start, int num) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			logger.debug("not login.....");
			return PaginationUtils.getNullPagination();
		}
		
		List<Long> friends = this.friendService.listFriendIds(loginUser.getPkId());
		PaginationSupport ps = this.tweetDAO.listTweetByUserId(friends, start, num);
		List<Tweet> tweets = ps.getItems();
		for(Tweet t : tweets){
			this.handleTweet(t);
		}
		
		return ps;
	}
}
