package com.message.main.friend.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.friend.po.FriendGroup;
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
		Long groupId = in.getLong("groupId", Long.valueOf(-1));
		params.put("current", "all");
		
		//每页显示10个用户
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		params.put("paginationSupport", this.friendService.listFriends(loginUser.getPkId(), groupId, start, num));
		params.put("groups", this.friendService.getFriendGroups(loginUser, -1, -1).getItems());
		params.put("noGroupFriendNum", this.friendService.getNoGroupFriendNum(loginUser));
		
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
		Long faid = in.getLong("faid", Long.valueOf(-1));							//好友申请的ID,如果为-1则是第一次申请,如果不为-1则是再次申请
		
		params.put(ResourceType.AJAX_STATUS, this.friendService.saveApplyFriends(selectedUserIds, applyMessage, isEmailNotify, loginUser, faid) ?
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
    
    /**
     * 删除好友
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView deleteFriend(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	Long friendId = in.getLong("fid", Long.valueOf(-1));
    	
    	params.put(ResourceType.AJAX_STATUS, this.friendService.deleteFriend(loginUser, friendId) ? 
    			ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	out.toJson(params);
    	return null;
    }
	
    /**
     * 保存用户的分组
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView saveGroup(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	String groupName = in.getString("groupName", StringUtils.EMPTY);
    	
    	Long groupId = this.friendService.saveGroup(groupName, loginUser);
    	params.put(ResourceType.AJAX_STATUS, groupId != null ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	params.put("groupId", groupId);
    	out.toJson(params);
    	return null;
    }
    
    /**
     * 好友分组的删除编辑操作
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView groupFun(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	Long groupId = in.getLong("groupId", Long.valueOf(-1));
    	String groupName = in.getString("groupName", StringUtils.EMPTY);
    	String action = in.getString("action", StringUtils.EMPTY);
    	
    	params.put(ResourceType.AJAX_STATUS, this.friendService.groupFunc(groupId, groupName, action) ? 
    			ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	out.toJson(params);
    	return null;
    }
    
    /**
     * 进入设置好友分组的页面
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView listGroup(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	Long fid = in.getLong("fid", Long.valueOf(-1));
    	params.put("groups", this.friendService.getFriendGroups(loginUser, -1, -1).getItems());
    	List<FriendGroup> fgs = this.friendService.getGroupByFriend(fid);
    	List<Long> friendGroups = new ArrayList<Long>();
    	for(FriendGroup fg : fgs){
    		friendGroups.add(fg.getPkId());
    	}
    	params.put("friendGroups", friendGroups);
    	
    	return new ModelAndView("friend.group.add", params);
    }
    
    /**
     * 保存用户好友分组配置
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception 
     */
    public ModelAndView saveFriendGroup(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	Long fid = in.getLong("fid");
    	Long[] groups = in.getLongObjects("group");
    	if(groups == null)
    		groups = new Long[]{};
    	
    	params.put(ResourceType.AJAX_STATUS, this.friendService.saveFriendGroup(loginUser, fid, groups) ? 
    			ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	out.toJson(params);
    	return null;
    }
}
