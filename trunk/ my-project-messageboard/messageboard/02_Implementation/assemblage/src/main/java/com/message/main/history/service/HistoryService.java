package com.message.main.history.service;

import java.util.Date;

import com.message.base.pagination.PaginationSupport;
import com.message.base.web.WebInput;
import com.message.main.history.pojo.UserLoginHistory;
import com.message.main.user.pojo.User;

/**
 * 登录历史操作的DAO
 *
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface HistoryService {
    /**
     * 保存登录历史
     *
     * @param in
     * @param user
     * @throws Exception
     */
    public void saveLoginHistory(WebInput in, User user) throws Exception;

    /**
     * 获取某个用户登录的次数
     *
     * @param userPkId
     * @return
     * @throws Exception
     */
    public int getLoginCount(Long userPkId) throws Exception;

    /**
     * 获取某个用户上次登录的时间
     *
     * @param userPkId
     * @param view
     * @return
     * @throws Exception
     */
    public Date getLastLoginTime(Long userPkId, boolean view) throws Exception;

    /**
     * 获取某个用户登录历史的list集合
     *
     * @param start
     * @param num
     * @return
     * @throws Exception
     */
    public PaginationSupport getHistory(int start, int num, UserLoginHistory history) throws Exception;

    /**
     * 定时清除一个月前的登录日志
     *
     * @throws Exception
     */
    public void cleanLoginHistory() throws Exception;
}
