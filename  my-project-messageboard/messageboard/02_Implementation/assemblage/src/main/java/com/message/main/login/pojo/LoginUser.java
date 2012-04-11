package com.message.main.login.pojo;

import com.message.main.user.pojo.User;

import java.util.Date;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-22 下午1:36
 */
public class LoginUser extends User {
	private static final long serialVersionUID = 6157248265535981747L;
	
	private Date lastLoginTime;	//上次登录的时间
	private int loginCount;		//登录次数
	private int messageCount;	//留言数目
	private String loginIP;		//登录地的IP

    public LoginUser(User user, Date lastLoginTime, Integer loginCount, Integer messageCount, String loginIP) {
        super(user);
        this.lastLoginTime = lastLoginTime;
        this.loginCount = loginCount;
        this.messageCount = messageCount;
        this.loginIP = loginIP;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }
}
