package com.message.main.friend.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.friend.service.FriendService;
import com.message.main.user.service.UserService;

/**
 * 好友模块的controller.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:50:28
 */
public class FriendController extends SimpleController {
	
	private FriendService friendService;
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	/**
	 * 进入好友列表页面
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	public ModelAndView index(WebInput in, WebOutput out){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "all");
		return new ModelAndView("friend.list", params);
	}
	
	/**
	 * 进入邀请提示页面
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	public ModelAndView listInvitePrompt(WebInput in, WebOutput out){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "invite");
		return new ModelAndView("friend.invite.prompt", params);
	}
	
	/**
	 * 进入邀请好友页面
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView toAddFriend(WebInput in, WebOutput out) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		//每页显示10个用户
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		params.put("current", "add");
		params.put("paginationSupport", this.userService.listAllUser(start, num, null, true));
		params.put("appliedIds", this.friendService.getAppliedIds());
		
		return new ModelAndView("friend.add", params);
	}
	
	/**
	 * 保存用户好友申请
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public ModelAndView applyFriends(WebInput in, WebOutput out) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		Long[] selectedUserIds = in.getLongObjects("selectedUserId");
		String applyMessage = in.getString("applyMessage", StringUtils.EMPTY);
		boolean isEmailNotify = in.getBoolean("isEmailNotify", false);
		boolean result = this.friendService.saveApplyFriends(selectedUserIds, applyMessage, isEmailNotify);
		params.put(ResourceType.AJAX_STATUS, result ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		
		out.toJson(params);
		return null;
	}
	
}
