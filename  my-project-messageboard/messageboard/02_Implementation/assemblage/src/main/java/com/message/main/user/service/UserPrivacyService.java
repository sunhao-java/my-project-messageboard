package com.message.main.user.service;

import com.message.main.user.pojo.User;
import com.message.main.user.pojo.UserPrivacy;

/**
 * 用户隐私设置的Service
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午09:10:40
 */
public interface UserPrivacyService {
	/**
	 * 根据用户获得其隐私配置
	 * 
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
}
