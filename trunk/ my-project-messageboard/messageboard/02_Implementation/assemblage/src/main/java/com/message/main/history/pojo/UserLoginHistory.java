package com.message.main.history.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserLoginHistory implements Serializable {
	private static final long serialVersionUID = -9214792508667537046L;
	
	private Long pkId;				//主键
	private Long loginUserPkId;		//登录者的ID
	private String loginIP;			//登录地IP
	private Date loginTime;			//登录时间
	private String browser;			//登录所使用浏览器
	
	//VO Fields
	private String browserStr;		//登录所使用的浏览器

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getLoginUserPkId() {
		return loginUserPkId;
	}

	public void setLoginUserPkId(Long loginUserPkId) {
		this.loginUserPkId = loginUserPkId;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserStr() {
		return browserStr;
	}

	public void setBrowserStr(String browserStr) {
		this.browserStr = browserStr;
	}
}
