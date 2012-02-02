package com.message.main.history.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;
import com.message.main.history.dao.HistoryDAO;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.utils.SqlUtils;
import com.message.utils.StringUtils;

/**
 * 登录历史操作的DAO的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class HistoryDAOImpl extends GenericHibernateDAOImpl implements HistoryDAO {

	public void saveLoginHistory(UserLoginHistory history) throws Exception {
		this.saveObject(history);
	}

	@SuppressWarnings("unchecked")
	public int getLoginCount(Long userPkId) throws Exception {
		String hql = "select count(*) from UserLoginHistory t where t.loginUserPkId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(userPkId);
		
		List list = this.findByHQL(hql, params);
		if(CollectionUtils.isNotEmpty(list)){
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	public Date getLastLoginTime(Long userPkId) throws Exception {
		String hql = "select t.loginTime from UserLoginHistory t where t.loginUserPkId = ? order by t.loginTime desc";
		List<Object> params = new ArrayList<Object>();
		params.add(userPkId);
		
		List list = this.findByHQL(hql, params);
		if(list.size() >= 2){
			return (Date) list.get(1);
		}
		
		return null;
	}

	public PaginationSupport getHistoryByUserId(Long userPkId, int start, int num, UserLoginHistory history)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from UserLoginHistory t where t.loginUserPkId = :userId ";
		
		if(history != null){
			if(StringUtils.isNotEmpty(history.getLoginIP())){
				hql += " and t.loginIP like :ip ";
				params.put("ip", SqlUtils.makeLikeString(StringUtils.trim(history.getLoginIP())));
			}
			if(history.getBeginTime() != null){
				hql += " and t.loginTime >= :beginTime ";
				params.put("beginTime", history.getBeginTime());
			}
			if(history.getEndTime() != null){
				hql += " and t.loginTime <= :endTime ";
				params.put("endTime", history.getEndTime());
			}
		}
		
		hql += " order by t.loginTime desc";
		String countHql = "select count(*) " + hql;
		
		params.put("userId", userPkId);
		
		PaginationSupport paginationSupport = this.getPaginationSupport(hql, countHql, start, num, params);
		
		return paginationSupport;
	}

	public void deleteHistory(UserLoginHistory history) throws Exception {
		this.deleteObject(history);
	}

	@SuppressWarnings("unchecked")
	public List<UserLoginHistory> getAllHistory() throws Exception {
		String hql = "from UserLoginHistory order by pkId desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		return this.findByHQL(hql, params);
	}

}
