package com.message.main.friend.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.email.MailSend;
import com.message.base.properties.MessageUtils;
import com.message.base.utils.StringUtils;
import com.message.main.friend.dao.FriendDAO;
import com.message.main.friend.po.Friend;
import com.message.main.friend.service.FriendService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:49:04
 */
public class FriendServiceImpl implements FriendService {
	
	private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);
	private FriendDAO friendDAO;
	private UserService userService;
	private MailSend mailSend;

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMailSend(MailSend mailSend) {
		this.mailSend = mailSend;
	}

	public boolean saveApplyFriends(Long[] selectedUserIds, String applyMessage, boolean isEmailNotify) throws Exception {
		if(selectedUserIds == null || selectedUserIds.length <= 0){
			logger.debug("the selectedUserIds is null!");
			return false;
		}
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		
		for(Long id : selectedUserIds){
			Friend friend = new Friend();
			friend.setDescUserId(id);
			friend.setApplyUserId(loginUser.getPkId());
			friend.setApplyTime(new Date());
			friend.setApplyMessage(applyMessage);
			
			Long pkId = this.friendDAO.saveApplyFriends(friend);
			
			if(isEmailNotify){
				//发邮件通知
				User descUser = this.userService.getUserById(id);
				String email = descUser.getEmail();
				if(StringUtils.isNotEmpty(email)){
					String applyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(friend.getApplyTime());
					String mailTemplate = MessageUtils.getProperties("mail.friend.template", 
							new Object[]{loginUser.getTruename(), applyTime, applyMessage, 
							MessageUtils.getProperties("messageboard.home", "http://sunhao.wiscom.com.cn:8089/message")});
					this.mailSend.sendMail("好友申请", mailTemplate, email);
				}
			}
			if(pkId == null)
				return false;
		}
		
		return true;
	}

	public List<Long> getAppliedIds() throws Exception {
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		return this.friendDAO.getAppliedIds(loginUser.getPkId());
	}

}
