package com.message.main.tweet.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.main.ResourceType;
import com.message.main.tweet.dao.TweetDAO;
import com.message.main.tweet.pojo.Tweet;

/**
 * 吐槽DAO实现.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-22 下午06:59:11
 */
public class TweetDAOImpl extends GenericHibernateDAOImpl implements TweetDAO {
	private GenericJdbcDAO genericJdbcDAO;

	public void setGenericJdbcDAO(GenericJdbcDAO genericJdbcDAO) {
		this.genericJdbcDAO = genericJdbcDAO;
	}

	public PaginationSupport listTweets(Tweet tweet, Long loginUserId, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_message_tweet t where t.delete_flag = :deleteFlag and t.creator_id = :creatorId ");
		if(tweet != null){
			//以后在这里写上查询条件语句
		}
		sql.append(" order by t.pk_id desc ");
		
		params.put("deleteFlag", ResourceType.DELETE_NO);
		params.put("creatorId", loginUserId);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql.toString(), null, start, num, params, Tweet.class);
	}
	
	public Tweet saveTweet(Tweet tweet) throws Exception {
		Tweet t = (Tweet) this.saveObject(tweet);
		
		return t;
	}

	public void updateTweet(Tweet tweet) throws Exception {
		this.updateObject(tweet);
	}

	public int deleteTweet(Long pkId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("update t_message_tweet t set t.delete_flag = :deleteFlag where t.pk_id = :pkId ");
		params.put("deleteFlag", ResourceType.DELETE_YES);
		params.put("pkId", pkId);
		
		return this.genericJdbcDAO.update(sql.toString(), params);
	}

	public Tweet loadTweet(Long pkId) throws Exception {
		return (Tweet) this.loadObject(Tweet.class, pkId);
	}

	public PaginationSupport listTweetByUserId(List<Long> userIds, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_message_tweet t where t.creator_id in (:creatorIds) and t.delete_flag = :deleteFlag order by t.pk_id desc");
		params.put("creatorIds", userIds);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql.toString(), null, start, num, params, Tweet.class);
	}

}
