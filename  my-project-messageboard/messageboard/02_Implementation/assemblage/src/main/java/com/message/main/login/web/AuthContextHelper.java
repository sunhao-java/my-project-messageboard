package com.message.main.login.web;

/**
 * 往ThreadLocal放入取出当前登录者
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-4 上午09:09:29
 */
public class AuthContextHelper {
    private static ThreadLocal authContextHolder = new ThreadLocal();

    public static AuthContext getAuthContext() {
        return (AuthContext) authContextHolder.get();
    }

    public static void setAuthContext(AuthContext authContext) {
        authContextHolder.set(authContext);
    }

}
