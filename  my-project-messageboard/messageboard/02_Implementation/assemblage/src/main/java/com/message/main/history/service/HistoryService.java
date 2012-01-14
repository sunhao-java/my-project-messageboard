package com.message.main.history.service;

import java.util.Date;
import java.util.List;

import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.user.pojo.User;
import com.message.utils.WebInput;

/**
 * 登录历史操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface HistoryService {
	/**
	 * 保存登录历史
	 * @param history
	 * @throws Exception
	 */
	public void saveLoginHistory(WebInput in, User user) throws Exception;
	
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
	
	/**
	 * 获取某个用户登录历史的list集合
	 * @param userPkId
	 * @return
	 * @throws Exception
	 */
	public List<UserLoginHistory> getHistoryByUserId(Long userPkId) throws Exception;
}
