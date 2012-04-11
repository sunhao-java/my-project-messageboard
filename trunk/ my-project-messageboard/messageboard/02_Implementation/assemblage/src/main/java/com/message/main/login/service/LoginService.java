package com.message.main.login.service;

import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-22 下午1:36
 */
public interface LoginService {
    /**
     * 登录的方法
     *
     * @param in
     * @param out
     * @param loginName     用户名
     * @param password      密码
     * @return  status 登录结果代码：
     *          0:成功        1:用户名错误      2:密码错误      3:用户名密码为空     4:未进行邮箱验证
     *          5:用户被删除     6:验证码输入错误
     * @throws Exception
     */
    Integer login(WebInput in, WebOutput out, String loginName, String password) throws Exception;

    /**
     * 登出系统的方法
     * 
     * @param in
     * @return  status 登出结果代码：
     *          0:成功 1:失败
     * @throws Exception
     */
    Integer logout(WebInput in) throws Exception;
}
