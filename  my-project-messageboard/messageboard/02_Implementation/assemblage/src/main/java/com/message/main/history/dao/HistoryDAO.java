package com.message.main.history.dao;

import com.message.main.history.pojo.UserLoginHistory;

/**
 * 登录历史操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface HistoryDAO {
	
	/**
	 * 保存登录历史
	 * @param history
	 * @throws Exception
	 */
	public void saveLoginHistory(UserLoginHistory history) throws Exception;
}
