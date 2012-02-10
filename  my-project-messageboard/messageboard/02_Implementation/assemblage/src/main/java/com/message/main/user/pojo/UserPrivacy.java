package com.message.main.user.pojo;

import java.io.Serializable;

/**
 * 用户隐私设置
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午08:38:47
 */
public class UserPrivacy implements Serializable {
	private static final long serialVersionUID = 1406111338140470736L;

	private Long userPkId;		//对应的用户ID
	private Long username;		//用户名
	private Long sex;			//性别
	private Long truename;		//真实姓名
	private Long email;			//邮箱
	private Long phonenum;		//电话号码
	private Long qq;			//QQ
	private Long homepage;		//主页
	private Long address;		//地址

	public Long getUserPkId() {
		return userPkId;
	}

	public void setUserPkId(Long userPkId) {
		this.userPkId = userPkId;
	}

	public Long getUsername() {
		return username;
	}

	public void setUsername(Long username) {
		this.username = username;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getTruename() {
		return truename;
	}

	public void setTruename(Long truename) {
		this.truename = truename;
	}

	public Long getEmail() {
		return email;
	}

	public void setEmail(Long email) {
		this.email = email;
	}

	public Long getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(Long phonenum) {
		this.phonenum = phonenum;
	}

	public Long getQq() {
		return qq;
	}

	public void setQq(Long qq) {
		this.qq = qq;
	}

	public Long getHomepage() {
		return homepage;
	}

	public void setHomepage(Long homepage) {
		this.homepage = homepage;
	}

	public Long getAddress() {
		return address;
	}

	public void setAddress(Long address) {
		this.address = address;
	}
}
