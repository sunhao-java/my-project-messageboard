package com.message.main.history.service;

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
}
