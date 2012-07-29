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
import com.message.main.login.pojo.LoginUser;
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
	 * @throws Exception 
	 */
	public ModelAndView index(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "all");
		
		//每页显示10个用户
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		params.put("paginationSupport", this.friendService.listFriends(loginUser.getPkId(), start, num));
		
		return new ModelAndView("friend.list", params);
	}
	
	/**
	 * 进入我发出的邀请页面
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView listMySendInvite(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "send");
		//每页显示10个用户
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		params.put("paginationSupport", this.friendService.getAllMyReceiveOrSend(loginUser, "send", start, num));
		
		return new ModelAndView("friend.my.send.invite", params);
	}
	
	/**
	 * 进入我收到的邀请页面
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	public ModelAndView listMyReceiveInvite(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "receive");
		//每页显示10个用户
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		params.put("paginationSupport", this.friendService.getAllMyReceiveOrSend(loginUser, "receive", start, num));
		
		return new ModelAndView("friend.my.receive.invite", params);
	}
	
	/**
	 * 进入邀请好友页面
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAddFriend(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "add");
		
		//每页显示10个用户
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		params.put("paginationSupport", this.userService.listAllUser(start, num, null, false));
		
		//已经是好友了
		params.put("myFriends", this.friendService.listFriendIds(loginUser.getPkId()));
		//申请遭拒绝的
		params.put("denyl", this.friendService.listApplyFriendIds(loginUser.getPkId(), ResourceType.AGREE_NO));
		//在申请中，对方未回应的
		params.put("applying", this.friendService.listApplyFriendIds(loginUser.getPkId(), ResourceType.AGREE_NOANSWER));
		
		return new ModelAndView("friend.add", params);
	}
	
	/**
	 * 保存用户好友申请
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView applyFriends(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		Long[] selectedUserIds = in.getLongObjects("selectedUserId");				//选择要申请的用户ID
		String applyMessage = in.getString("applyMessage", StringUtils.EMPTY);		//申请好友时的附言
		boolean isEmailNotify = in.getBoolean("isEmailNotify", false);				//是否用邮件通知此人
		
		params.put(ResourceType.AJAX_STATUS, this.friendService.saveApplyFriends(selectedUserIds, applyMessage, isEmailNotify, loginUser) ?
				ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 取消请求
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ajaxCancelRequest(WebInput in, WebOutput out) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		
		Long faid = in.getLong("faid", Long.valueOf(-1));
		params.put(ResourceType.AJAX_STATUS, this.friendService.cancelRequest(faid) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}

    /**
     * ajax处理同意还是拒绝好友请求
     * 
     * @param in
     * @param out
     * @return
     * @throws Exception
     */
    public ModelAndView ajaxHandleRequest(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        Long friendId = in.getLong("faid", Long.valueOf(-1));
        Integer agreeFlag = in.getInt("agreeFlag", Integer.valueOf(-1));
        String disAgreeMessage = in.getString("denylMsg", StringUtils.EMPTY);
        
        params.put("status", this.friendService.ajaxHandleRequest(loginUser, friendId, agreeFlag, disAgreeMessage) ?
                ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
    }
	
}
