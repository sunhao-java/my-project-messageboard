package com.message.main.user.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author sunhao(sunhao.java@gmail.com)
 */
public class User implements Serializable{
	private static final long serialVersionUID = -4503810961138748070L;
	
	private Long pkId;			//主键，唯一标识
	private String username;	//用户名
	private String password;	//密码，MD5加密
	private Date createDate;	//用户创建时间
	private Long sex;			//性别0:不男不女;1: 男;2:女
	private String email;		//用户注册邮箱
	private String phoneNum;	//用户注册手机号码
	private String qq;			//注册用户的QQ号码
	private String headImage;	//注册用户的头像(记录头像图片的路径)
	private String address; 	//注册用户的地址
	private String homePage;	//注册用户的主页
	private Long deleteFlag;	//软删除，0未删除，1已删除
	
	//VO fileds
	private Date lastLoginTime;	//上次登录的时间
	private int loginCount;
	
	/**
	 * 默认构造方法
	 */
	public User(){
	}

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
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

}
