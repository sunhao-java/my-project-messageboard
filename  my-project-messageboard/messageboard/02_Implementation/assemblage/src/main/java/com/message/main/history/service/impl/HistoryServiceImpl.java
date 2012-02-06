package com.message.main.history.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.i18n.MessageUtils;
import com.message.base.pagination.PaginationSupport;
import com.message.base.web.WebInput;
import com.message.main.event.job.CleanEventJob;
import com.message.main.history.dao.HistoryDAO;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.history.service.HistoryService;
import com.message.main.user.pojo.User;
import com.message.utils.DateUtils;
import com.message.utils.StringUtils;

/**
 * 登录历史操作的service的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class HistoryServiceImpl implements HistoryService {
	private static final Logger logger = LoggerFactory.getLogger(CleanEventJob.class);
	
	/**
	 * 31天的分钟数
	 */
	private static final long MONTH_AGO_MINUTES = 31 * 24 * 60;
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

	@SuppressWarnings("unchecked")
	public PaginationSupport getHistoryByUserId(Long userPkId, int start, int num, UserLoginHistory history1)
			throws Exception {
		PaginationSupport paginationSupport = this.historyDAO.getHistoryByUserId(userPkId, start, num, history1);
		List<UserLoginHistory> historys = paginationSupport.getItems();
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
		return paginationSupport;
	}

	public void cleanLoginHistory() throws Exception {
		List<UserLoginHistory> histories = this.historyDAO.getAllHistory();
		for(UserLoginHistory history : histories){
			if(DateUtils.getMinuteBewteenDates(history.getLoginTime(), new Date()) > MONTH_AGO_MINUTES){
				logger.debug("delete an history entity, id is {}", history.getPkId());
				this.historyDAO.deleteHistory(history);
			}
		}
	}

}
