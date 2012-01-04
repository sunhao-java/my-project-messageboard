package com.message.main.history.service.impl;

import java.util.Date;

import com.message.main.history.dao.HistoryDAO;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.history.service.HistoryService;
import com.message.main.user.pojo.User;
import com.message.utils.WebInput;

/**
 * 登录历史操作的service的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class HistoryServiceImpl implements HistoryService {
	private HistoryDAO historyDAO;

	public void setHistoryDAO(HistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}

	public void saveLoginHistory(WebInput in, User user) throws Exception {
		if(in != null){
			UserLoginHistory loginHistory = new UserLoginHistory();
			loginHistory.setLoginIP(in.getClientIP());
			loginHistory.setLoginUserPkId(user.getPkId());
			loginHistory.setLoginTime(new Date());
			loginHistory.setBrowser(in.getRequest().getHeader("User-Agent"));
			this.historyDAO.saveLoginHistory(loginHistory);
		}
	}

	public int getLoginCount(Long userPkId) throws Exception {
		return this.historyDAO.getLoginCount(userPkId);
	}

	public Date getLastLoginTime(Long userPkId) throws Exception {
		return this.historyDAO.getLastLoginTime(userPkId);
	}

}
