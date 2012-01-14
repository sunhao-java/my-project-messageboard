package com.message.main.history.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.main.history.dao.HistoryDAO;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.history.service.HistoryService;
import com.message.main.user.pojo.User;
import com.message.utils.MessageUtils;
import com.message.utils.StringUtils;
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

	public List<UserLoginHistory> getHistoryByUserId(Long userPkId)
			throws Exception {
		List<UserLoginHistory> historys = this.historyDAO.getHistoryByUserId(userPkId);
		if(CollectionUtils.isNotEmpty(historys)){
			for(UserLoginHistory history : historys){
				if(StringUtils.isNotEmpty(history.getBrowser())){
					String browser = history.getBrowser();
					String browserStr = StringUtils.EMPTY;
					if(browser.indexOf("Firefox") != -1){
						browserStr = MessageUtils.getMessage("history.browser.firefox");
					} else if(browser.indexOf("360SE") != -1){
						browserStr = MessageUtils.getMessage("history.browser.360");
					} else if(browser.indexOf("Apple") != -1){
						browserStr = MessageUtils.getMessage("history.browser.safari");
					} else if(browser.indexOf("MSIE") != -1){
						//对IE的判断要放在最后，因为有很多浏览器是基于IE内核的
						browserStr = MessageUtils.getMessage("history.browser.ie");
					} else {
						browserStr = MessageUtils.getMessage("history.browser.other");
					}
					
					history.setBrowserStr(browserStr);
				}
			}
		}
		return historys;
	}

}
