package com.message.main.history.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.main.history.dao.HistoryDAO;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.utils.base.utils.impl.GenericHibernateDAOImpl;

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

}
