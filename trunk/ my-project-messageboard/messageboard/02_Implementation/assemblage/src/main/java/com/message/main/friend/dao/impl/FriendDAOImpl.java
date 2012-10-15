package com.message.main.friend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.message.base.cache.utils.ObjectCache;
import com.message.base.hibernate.GenericHibernateDAO;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.main.ResourceType;
import com.message.main.friend.dao.FriendDAO;
import com.message.main.friend.po.Friend;
import com.message.main.friend.po.FriendApply;
import com.message.main.friend.po.FriendGroup;
import com.message.main.friend.po.FriendGroupUser;
import com.message.main.login.pojo.LoginUser;

/**
 * 好友模块DAO.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:50:00
 */
public class FriendDAOImpl extends GenericHibernateDAO implements FriendDAO {
	
	private GenericJdbcDAO genericJdbcDAO;
	private ObjectCache cache;

	public void setGenericJdbcDAO(GenericJdbcDAO genericJdbcDAO) {
		this.genericJdbcDAO = genericJdbcDAO;
	}

	public void setCache(ObjectCache cache) {
		this.cache = cache;
	}

	public PaginationSupport listFriends(Long userId, Long groupId, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select f.pk_id from t_message_friend f where f.user_id = :applyId ";
		if(Long.valueOf(0).equals(groupId)){
			//未分组的
			sql += " and f.pk_id not in (" +
					"select u.friend_id from t_message_friend_group_user u join t_message_friend_group g on " +
					" u.group_id = g.pk_id " +
					")";
		} else if(groupId != null && !Long.valueOf(-1).equals(groupId)){
			//分组的
			sql += " and f.pk_id in (" +
					"select u.friend_id from t_message_friend_group_user u join t_message_friend_group g on " +
					" u.group_id = g.pk_id where g.pk_id = :groupId " +
					")";
			params.put("groupId", groupId);
		}
		sql += " order by f.pk_id desc ";
		params.put("applyId", userId);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql, null, start, num, params, Friend.class);
	}
	
	public int getNoGroupFriendNum(Long loginUserId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select count(*) from t_message_friend ff where ff.user_id = :loginUserId and ff.pk_id not in " +
				"(select gu.friend_id from t_message_friend_group_user gu join t_message_friend_group fg on " +
				"gu.group_id = fg.pk_id )";
		params.put("loginUserId", loginUserId);
		
		return this.genericJdbcDAO.queryForInt(sql, params);
	}

	public Friend getFriend(Long fid) throws Exception {
		Friend f = (Friend) this.cache.get(Friend.class, fid);
		if(f == null){
			f = (Friend) this.loadObject(Friend.class, fid);
			this.cache.put(f, fid);
		}
		
		return f;
	}

	public <T> T saveEntity(T entity) throws Exception {
		this.saveObject(entity);
		if(entity instanceof Friend){
			this.cache.put(entity, ((Friend) entity).getPkId());
		} else if(entity instanceof FriendApply){
			this.cache.put(entity, ((FriendApply) entity).getPkId());
		} else if(entity instanceof FriendGroup){
			this.cache.put(entity, ((FriendGroup) entity).getPkId());
		} else if(entity instanceof FriendGroupUser){
			this.cache.put(entity, ((FriendGroupUser) entity).getPkId());
		}
		
		return entity;
	}

	public PaginationSupport listApplyFriends(Long userId, Integer result, Integer returnType, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select ";
		if(ResourceType.RETURN_OTHER.equals(returnType)){
			sql += " a.invite_user_id ";
		} else if(ResourceType.RETURN_PKID.equals(returnType)){
			sql += " a.pk_id ";
		} else {
			return null;
		}
		
		sql += " from t_message_friend_apply a where a.apply_user_id = :applyUserId ";
		if(result != null){
			sql += " and a.result = :result ";
			params.put("result", result);
		}
		params.put("applyUserId", userId);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql, null, start, num, params, FriendApply.class);
	}

	public FriendApply getFriendApply(Long faid) throws Exception {
		FriendApply fa = (FriendApply) this.cache.get(FriendApply.class, faid);
		if(fa == null){
			fa = (FriendApply) this.loadObject(FriendApply.class, faid);
			this.cache.put(fa, faid);
		}
		
		return fa;
	}

	public PaginationSupport getAllMyReceiveOrSend(LoginUser loginUser, String applyType, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select a.pk_id from t_message_friend_apply a where 1 = 1 ";
		if(StringUtils.equals("receive", applyType)){
			sql += " and a.invite_user_id = :userId and a.result = :result ";
			params.put("result", ResourceType.AGREE_NOANSWER);
		} else if(StringUtils.equals("send", applyType)){
			sql += " and a.apply_user_id = :userId and a.result <> :result ";
			params.put("result", ResourceType.AGREE_YES);
		} else {
			return null;
		}
		
		sql += " and a.result <> 1 ";
		sql += " order by a.pk_id desc";
		
		params.put("userId", loginUser.getPkId());
		return this.genericJdbcDAO.getBeanPaginationSupport(sql, null, start, num, params, FriendApply.class);
	}

	public Friend getFriend(Long friendApplyId, Long userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select f.pk_id from t_message_friend f where f.apply_id = :applyId and user_id = :userId ";
		params.put("applyId", friendApplyId);
		params.put("userId", userId);
		
		List list = this.genericJdbcDAO.queryForBeanList(sql, -1, -1, params, Friend.class);
		return (Friend) (list.isEmpty() ? null : list.get(0));
	}

	public int deleteObject(Long pkId, Class deleteClazz) throws Exception {
		String table = "";
		if(Friend.class.equals(deleteClazz)){
			table = "t_message_friend";
		} else if(FriendApply.class.equals(deleteClazz)){
			table = "t_message_friend_apply";
		} else if(FriendGroup.class.equals(deleteClazz)){
			table = "t_message_friend_group";
		} else if(FriendGroupUser.class.equals(deleteClazz)){
			table = "t_message_friend_group_user";
		} else {
			return 0;
		}
		
		String sql = "delete from " + table + " t where t.pk_id = " + pkId;
		int result = this.genericJdbcDAO.update(sql);
		
		if(result == 1){
			this.cache.remove(deleteClazz, pkId);
			return result;
		} else {
			return 0;
		}
	}

	public void updateEntity(Object entity) throws Exception {
		this.updateObject(entity);
		
		if(entity instanceof Friend){
			this.cache.remove(Friend.class, ((Friend) entity).getPkId());
			this.cache.put(entity, ((Friend) entity).getPkId());
		} else if(entity instanceof FriendApply){
			this.cache.remove(FriendApply.class, ((FriendApply) entity).getPkId());
			this.cache.put(entity, ((FriendApply) entity).getPkId());
		} else if(entity instanceof FriendGroup){
			this.cache.remove(FriendGroup.class, ((FriendGroup) entity).getPkId());
			this.cache.put(entity, ((FriendGroup) entity).getPkId());
		}
	}

	public boolean deleteFriend(Long userId, Long friendId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "delete from t_message_friend f where f.user_id = :userId and f.friend_id = :friendId ";
		params.put("userId", userId);
		params.put("friendId", friendId);
		
		return this.genericJdbcDAO.update(sql, params) == 1;
	}

	public PaginationSupport getFriendGroups(Long userId, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select g.pk_id from t_message_friend_group g where g.ower = :ower and g.delete_flag = :deleteFlag";
		params.put("ower", userId);
		params.put("deleteFlag", ResourceType.DELETE_NO_INTEGER);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql, null, start, num, params, FriendGroup.class);
	}

	public FriendGroup getFriendGroup(Long fgid) throws Exception {
		FriendGroup fg = (FriendGroup) this.cache.get(FriendGroup.class, fgid);
		if(fg == null){
			fg = (FriendGroup) this.loadObject(FriendGroup.class, fgid);
			this.cache.put(fg, fgid);
		}
		
		return fg;
	}

	public int getGroupUserNum(Long groupId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select count(gu.friend_id) from t_message_friend_group_user gu " +
				"join t_message_friend f on f.pk_id = gu.friend_id where gu.group_id = :groupId";
		params.put("groupId", groupId);
		
		return this.genericJdbcDAO.queryForInt(sql, params);
	}

	public List<Long> getGroupByFriend(Long friendId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select gu.group_id as groupid from t_message_friend_group_user gu where gu.friend_id = :friendId";
		params.put("friendId", friendId);
		
		return this.genericJdbcDAO.queryForList(sql, params, new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong("groupid");
			}});
	}

	public List<Long> getGroupUserByFriendId(Long friendId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select gu.pk_id as pkid from t_message_friend_group_user gu where gu.friend_id = :friendId";
		params.put("friendId", friendId);
		
		return this.genericJdbcDAO.queryForList(sql, params, new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong("pkid");
			}
		});
	}

	public boolean isFriend(Long fristUserId, Long secondUserId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select count(*) from t_message_friend f where f.user_id = :userId and f.friend_id = :friendId";
		params.put("userId", fristUserId);
		params.put("friendId", secondUserId);
		
		return this.genericJdbcDAO.queryForInt(sql, params) == 1;
	}


}
