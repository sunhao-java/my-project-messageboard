package com.message.main.letter.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.main.ResourceType;
import com.message.main.letter.dao.LetterDAO;
import com.message.main.letter.pojo.LetterUserRelation;

/**
 * 站内信的DAO实现.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:21:38
 */
public class LetterDAOImpl extends GenericJdbcDAO implements LetterDAO {

	public <T> T save(T entity) throws Exception {
		return this.genericInsert(entity);
	}

	public PaginationSupport getInbox(Long userId, Integer read, int start, int num) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select * from t_message_letter_user lu where lu.receiver_id = :uid and " +
				" lu.delete_flag = :deleteFlag ";
		if(read != null){
			sql += " and lu.read = :read ";
			params.put("read", read);
		}
		sql += " order by lu.pk_id desc ";
		
		params.put("uid", userId);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		
		return this.getBeanPaginationSupport(sql, null, start, num, params, LetterUserRelation.class);
	}

	public <T> T loadObject(Class clazz, Serializable pkId) throws Exception {
		return (T) this.genericLoad(clazz, pkId);
	}

	public List<LetterUserRelation> getReleationByLetter(Long letterId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select * from t_message_letter_user lu where lu.delete_flag = :deleteFlag " +
				"and lu.letter_id = :letterId order by lu.pk_id desc";
		
		params.put("letterId", letterId);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		
		return this.queryForBeanList(sql, -1, -1, params, LetterUserRelation.class);
	}

	public LetterUserRelation getRelation(Long letterId, Long receiverId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select * from t_message_letter_user lu where lu.delete_flag = :deleteFlag " +
				" and lu.letter_id = :letterId and receiver_id = :receiverId ";
		
		params.put("letterId", letterId);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		params.put("receiverId", receiverId);
		
		return (LetterUserRelation) this.queryForBean(sql, params, LetterUserRelation.class);
	}
	
	public void updateObject(Object obj) throws Exception {
		this.genericUpdate(obj);
	}

	public boolean deleteInbox(List<Long> letterIds, Long receiverId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "update t_message_letter_user lu set lu.delete_flag = :deleteFlag where lu.letter_id " +
				" in (:letterIds) and receiver_id = :receiverId";
		
		params.put("letterIds", letterIds);
		params.put("deleteFlag", ResourceType.DELETE_YES);
		params.put("receiverId", receiverId);
		
		return this.update(sql, params) == letterIds.size();
	}

}
