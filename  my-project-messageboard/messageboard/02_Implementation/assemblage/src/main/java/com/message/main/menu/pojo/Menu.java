package com.message.main.menu.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.message.main.user.pojo.User;

/**
 * 菜单实体
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午08:54:20
 */
@SuppressWarnings("rawtypes")
public class Menu implements Serializable, Comparable {
	private static final long serialVersionUID = 8286203139252526426L;
	
	private Long pkId;					//主键
	private String menuName;			//菜单名称
	private String menuUrl;				//菜单链接
	private Long parentId;				//上级菜单
	private Long menuSort;				//菜单排序
	private Long menuStatus;			//菜单状态：1-激活；0-未激活
	private String menuIcon;			//菜单图标
	private Long deleteStatus;			//软删除标识：0-未删除，1-删除
	private Long createUserId;			//创建人ID
	private Date createDate;			//创建日期
	private String menuPerm;			//菜单权限：1管理员可见；0普通成员可见；2游客可见
										//(如管理员和游客可见，则为1,2)
										//(如管理员和普通成员可见，则1,0)
	
	//VO Fileds
	private User createUser;			//创建人
	private List<Menu> children;		//此菜单的所有子
	private List<Long> perms;			//权限VO字段

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Long menuSort) {
		this.menuSort = menuSort;
	}

	public Long getMenuStatus() {
		return menuStatus;
	}

	public void setMenuStatus(Long menuStatus) {
		this.menuStatus = menuStatus;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Long getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Long deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getMenuPerm() {
		return menuPerm;
	}

	public void setMenuPerm(String menuPerm) {
		this.menuPerm = menuPerm;
	}

	public List<Long> getPerms() {
		return perms;
	}

	public void setPerms(List<Long> perms) {
		this.perms = perms;
	}

	public int compareTo(Object o) {
		Menu menu = (Menu) o;
		if(this.getMenuSort() < menu.getMenuSort()){
			return -1;
		} else if(this.getMenuSort() == menu.getMenuSort()){
			return 0;
		} else {
			return 1;
		}
	}

}
