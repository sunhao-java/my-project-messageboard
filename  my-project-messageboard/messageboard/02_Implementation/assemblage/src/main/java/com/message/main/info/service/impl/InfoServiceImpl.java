package com.message.main.info.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.pagination.PaginationSupport;
import com.message.main.info.dao.InfoDAO;
import com.message.main.info.pojo.Info;
import com.message.main.info.service.InfoService;
import com.message.main.user.service.UserService;

/**
 * 留言板描述的service的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class InfoServiceImpl implements InfoService {
	private InfoDAO infoDAO;
	private UserService userService;
	
	public void setInfoDAO(InfoDAO infoDAO) {
		this.infoDAO = infoDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Long saveInfo(Info info) throws Exception {
		info.setModifyDate(new Date());
		return this.infoDAO.saveInfo(info);
	}

	public Info getNewestInfo() throws Exception {
		List<Info> infos = this.infoDAO.getAllInfo();
		if(CollectionUtils.isNotEmpty(infos)){
			return infos.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport getInfoHistroy(int start, int num, Info info)
			throws Exception {
		PaginationSupport paginationSupport = this.infoDAO.getInfoHistroy(start, num, info);
		List<Info> infos = paginationSupport.getItems();
		for(Info info_ : infos){
			if(info_ != null){
				info_.setModifyUser(this.userService.getUserById(info_.getModifyUserId()));
			}
		}
		return paginationSupport;
	}

}
