package com.message.main.login.service.impl;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.properties.SystemConfig;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.history.service.HistoryService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.service.LoginService;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-22 下午1:53
 */
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private UserService userService;
    private HistoryService historyService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public Integer login(WebInput in, WebOutput out, String loginName, String password) throws Exception {
        Integer result = Integer.valueOf(-1);
        if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)){
            logger.warn("loginName or password is null!");
			result = 3;		//用户名为空
		}
		User user = this.userService.getUserByName(loginName);
		String loginPsw = MD5Utils.MD5Encode(password);
		if(user == null){
            logger.warn("can't get user from database with given loginName '{}'!", loginName);
			result = 1;		//用户名错误
		} else {
			if(ResourceType.DELETE_NO.equals(user.getDeleteFlag())){
				if(loginPsw.equals(user.getPassword())){
					if(!ResourceType.MAIL_CHECK_YES.equals(user.getIsMailCheck())){
                        logger.warn("this user '{}' is not mail check!", user);
						result = 4;	//未进行邮箱验证
					} else {
						this.historyService.saveLoginHistory(in, user);
						result = 0;	//正确
					}
				} else {
					result = 2;	//密码错误
				}
			} else {
                logger.warn("this user is delete already!");
				result = 5;		//用户被删除
			}
		}

        boolean isVerityCode = SystemConfig.getBooleanProperty("system.auth.verity.code", Boolean.TRUE);
        logger.debug("verityCode boolean is '{}'", isVerityCode);
        if(isVerityCode){
            String verityCode = in.getString("inputCode", StringUtils.EMPTY);
            String codeInSession = (String) in.getSession().getAttribute(ResourceType.VERITY_CODE_KEY);
            logger.debug("get from client code is '{}', get from session code is '{}'!", verityCode, codeInSession);
            if(!verityCode.equals(codeInSession)){
                result = 6;     //验证码输入错误
            }
        }

        if(Integer.valueOf(0).equals(result))
            this.setLoginUserInSession(in, user);

        return result;
    }

    /**
     * 将登录用户放入session中
     * 设置session的生命周期为1小时(即：用户登录后不做任何操作1小时后将被强制登出)
     * 
     * @param in
     * @param user
     * @throws Exception
     */
    private void setLoginUserInSession(WebInput in, User user) throws Exception {
        Integer loginCount = this.historyService.getLoginCount(user.getPkId());
	    Date lastLoginTime = this.historyService.getLastLoginTime(user.getPkId(), false);

        LoginUser loginUser = new LoginUser(user, lastLoginTime, loginCount, 0, in.getClientIP());
        
        HttpSession session = in.getSession();
        session.setAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION, loginUser);
        in.setMaxInactiveInterval(session, 1 * 60 * 60 * 1000);
    }

    public Integer logout(WebInput in) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        User userInSession = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
        HttpSession session = in.getSession();
        
        Integer result = Integer.valueOf(-1);
        if(session != null){
            if(userInSession != null && loginUser.getTruename().equals(userInSession.getTruename())){
                session.removeAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
                result = 0;
            }
        } else
            result = 1;
        
		return result;
    }
}
