package com.message.main.user.service;

import com.message.base.pagination.PaginationSupport;
import com.message.base.web.WebInput;
import com.message.main.user.pojo.User;

/**
 * 用户操作的service 
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface UserService {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean registerUser(User user) throws Exception;
	
	/**
	 * 用户登录
	 * @param user
	 * @return 0:成功	1:用户名错误		2:密码错误		4:未进行邮箱验证
	 * @throws Exception
	 */
	int userLogin(User user, WebInput in) throws Exception;
	
	/**
	 * 通过ID获取用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserById(Long userId) throws Exception;
	
	/**
	 * 判断用户名是否存在
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean checkUser(User user) throws Exception;
	
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 * @throws Exception
	 */
	User getUserByName(String username) throws Exception;
	
	/**
	 * 往登录用户中添加历史登录信息
	 * @param user
     * @param view
	 * @return
	 * @throws Exception
	 */
	User addLoginInfo(User user, boolean view) throws Exception;
	
	/**
	 * 编辑修改的用户
	 * @param user
	 * @throws Exception
	 */
	void saveEdit(User user) throws Exception;
	
	/**
	 * 更新用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	void updateUser(User user) throws Exception;
	
	/**
	 * 保存密码
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean savePassword(User user) throws Exception;
	
	/**
	 * 获取所有用户
	 * @param start
	 * @param num
	 * @param user
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listAllUser(int start, int num, User user) throws Exception;
	
	/**
	 * 获取所有用户
	 * 
	 * @param start
	 * @param num
	 * @param user
	 * @param containSelf		是否包含登录用户
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listAllUser(int start, int num, User user, boolean containSelf) throws Exception;
	
	/**
	 * 删除用户(软删除)
	 * @param pkids
	 * @return
	 * @throws Exception
	 */
	boolean deleteUser(String pkids) throws Exception;
	
	/**
	 * 设置用户权限
	 * @param pkId
	 * @param opertion
	 * @return
	 * @throws Exception
	 */
	boolean managerPerm(long pkId, boolean opertion) throws Exception;
	
	/**
	 * 当注册人在验证激活用户邮件中点击链接时触发对用户进行激活
	 * @param in
	 * @return
	 * @throws Exception
	 */
	boolean emailConfirm(WebInput in) throws Exception;
	
	/**
	 * 获取用户头像的字节流
	 * 
	 * @param userId		用户ID
	 * @param headType		显示头像的类型:0普通头像1大头像2小头像
	 * @return
	 * @throws Exception
	 */
	byte[] getHeadImage(Long userId, Integer headType) throws Exception;

}
