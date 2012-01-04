package com.message.main.history.dao.impl;

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

}
