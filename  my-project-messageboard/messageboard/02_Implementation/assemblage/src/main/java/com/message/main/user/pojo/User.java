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
	private String truename;	//真实姓名
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
	private Long isAdmin;		//是否是管理员的标识, 0不是管理员，1是管理员
	private Long isMailCheck;	//是否已经邮箱验证过？ 1已验证，0未验证

    //VO fileds
	private Date lastLoginTime;	//上次登录的时间
	private int loginCount;		//登录次数
	private int messageCount;	//留言数目
	private String loginIP;		//登录地的IP
	
	/**
	 * 默认构造方法
	 */
	public User(){
	}

    public User(User user) {
        this.pkId = user.getPkId();
        this.username = user.getUsername();
        this.truename = user.getTruename();
        this.password = user.getPassword();
        this.createDate = user.getCreateDate();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.phoneNum = user.getPhoneNum();
        this.qq = user.getQq();
        this.headImage = user.getHeadImage();
        this.address = user.getAddress();
        this.homePage = user.getHomePage();
        this.deleteFlag = user.getDeleteFlag();
        this.isAdmin = user.getIsAdmin();
        this.isMailCheck = user.getIsMailCheck();
    }

    public User(Long pkId){
		this.pkId = pkId;
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

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
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

	public Long getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Long isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getIsMailCheck() {
		return isMailCheck;
	}

	public void setIsMailCheck(Long isMailCheck) {
		this.isMailCheck = isMailCheck;
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
