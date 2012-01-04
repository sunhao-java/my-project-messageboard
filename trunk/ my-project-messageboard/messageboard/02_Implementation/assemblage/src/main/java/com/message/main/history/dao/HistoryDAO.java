package com.message.main.history.dao;

import java.util.Date;

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
	
	/**
	 * 获取某个用户登录的次数
	 * @param userPkId
	 * @return
	 * @throws Exception
	 */
	public int getLoginCount(Long userPkId) throws Exception;
	
	/**
	 * 获取某个用户上次登录的时间
	 * @param userPkId
	 * @return
	 * @throws Exception
	 */
	public Date getLastLoginTime(Long userPkId) throws Exception;
}
