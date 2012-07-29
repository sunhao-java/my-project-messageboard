package com.message.main.login.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.message.base.spring.BeanHandlerFactory;
import com.message.main.ResourceType;
import com.message.main.login.pojo.LoginUser;

/**
 * .
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-7-9 下午8:03
 */
public class LoginUserBeanHandlerFactory implements BeanHandlerFactory {
    public Object getValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LoginUser loginUser = (LoginUser) request.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
    	
        return loginUser;
    }
}
