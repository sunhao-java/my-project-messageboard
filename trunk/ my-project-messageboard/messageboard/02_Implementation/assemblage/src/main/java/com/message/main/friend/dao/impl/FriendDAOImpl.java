package com.message.main.friend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.main.friend.dao.FriendDAO;
import com.message.main.friend.po.Friend;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:50:00
 */
public class FriendDAOImpl extends GenericHibernateDAOImpl implements FriendDAO {
	
	private GenericJdbcDAO genericJdbcDAO;

	public void setGenericJdbcDAO(GenericJdbcDAO genericJdbcDAO) {
		this.genericJdbcDAO = genericJdbcDAO;
	}

	public Long saveApplyFriends(Friend friend) throws Exception {
		this.saveObject(friend);
		return friend.getPkId();
	}

	public List<Long> getAppliedIds(Long applyUserId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select f.desc_user_id as userId from t_message_friend f where f.apply_user_id = :applyUserId";
		params.put("applyUserId", applyUserId);
		
		return this.queryByNativeSQL(sql, params);
	}

}
