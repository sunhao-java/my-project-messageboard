package com.message.main.history.dao;

import java.util.Date;
import java.util.List;

import com.message.base.pagination.PaginationSupport;
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
     * @param view
	 * @return
	 * @throws Exception
	 */
	public Date getLastLoginTime(Long userPkId, boolean view) throws Exception;
	
	/**
	 * 获取某个用户登录历史的分页对象
	 * @param userPkId
	 * @return
	 * @throws Exception
	 */
	public PaginationSupport getHistoryByUserId(Long userPkId, int start, int num, UserLoginHistory history) throws Exception;
	
	/**
	 * 获取所有的登录历史
	 * @return
	 * @throws Exception
	 */
	public List<UserLoginHistory> getAllHistory() throws Exception;
	
	/**
	 * 删除指定登录历史
	 * @param history
	 * @throws Exception
	 */
	public void deleteHistory(UserLoginHistory history) throws Exception;
}
