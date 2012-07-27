package com.message.main.friend.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.friend.dao.FriendDAO;
import com.message.main.friend.po.Friend;
import com.message.main.login.pojo.LoginUser;

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

	public PaginationSupport getFriendsByCustom(int start, int num, String inviteType, Integer agreeFlag, LoginUser loginUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select * from t_message_friend f where 1 = 1";
		if(StringUtils.isNotEmpty(inviteType)){
			if("send".equals(inviteType)){
				//我发出的
				sql += " and f.apply_user_id = :applyUserId ";
				params.put("applyUserId", loginUser.getPkId());
			} else if("receive".equals(inviteType)){
				//我收到的
				sql += " and f.desc_user_id = :descUserId";
				params.put("descUserId", loginUser.getPkId());
			}
		}
		if((agreeFlag != null) && (ResourceType.AGREE_NOANSWER.equals(agreeFlag) || ResourceType.AGREE_YES.equals(agreeFlag) || 
				ResourceType.AGREE_NO.equals(agreeFlag))){
			sql += " and f.agree = :agree";
			params.put("agree", agreeFlag);
		}
		sql += " order by f.pk_id desc ";
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql, null, start, num, params, Friend.class);
	}

	public boolean cancelRequest(Long pkId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "delete from t_message_friend f where f.pk_id = :pkId";
		params.put("pkId", pkId);
		
		return this.genericJdbcDAO.update(sql, params) == 1;
	}

}
