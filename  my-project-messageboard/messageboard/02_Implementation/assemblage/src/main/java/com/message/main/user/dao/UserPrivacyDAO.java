package com.message.main.user.dao;

import com.message.main.user.pojo.User;
import com.message.main.user.pojo.UserPrivacy;

/**
 * 用户隐私设置的DAO
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午09:11:04
 */
public interface UserPrivacyDAO {
	/**
	 * 根据用户获得其隐私配置
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	UserPrivacy getUserPrivacy(Long pkId) throws Exception;
	
	/**
	 * 保存用户隐私配置
	 * 
	 * @param userPrivacy
	 * @throws Exception
	 */
	void saveUserPrivacy(UserPrivacy userPrivacy) throws Exception;
	
	/**
	 * 删除用户隐私配置
	 * 
	 * @param userPrivacy
	 * @throws Exception
	 */
	void deleteUserPrivacy(UserPrivacy userPrivacy) throws Exception;
}
