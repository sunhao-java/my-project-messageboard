package com.message.main.friend.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.email.MailSend;
import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.properties.MessageUtils;
import com.message.base.utils.ObjectUtils;
import com.message.base.utils.StringUtils;
import com.message.base.utils.ValidateUtils;
import com.message.main.ResourceType;
import com.message.main.friend.dao.FriendDAO;
import com.message.main.friend.po.Friend;
import com.message.main.friend.po.FriendApply;
import com.message.main.friend.po.FriendGroup;
import com.message.main.friend.po.FriendGroupUser;
import com.message.main.friend.service.FriendService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * 好友模块service.
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

	public PaginationSupport listFriends(Long userId, Long groupId, int start, int num) throws Exception {
		PaginationSupport ps = this.friendDAO.listFriends(userId, groupId, start, num);
		List<Friend> friends = ps.getItems();
		for(int i = 0; i < friends.size(); i++){
			Friend f = friends.get(i);
			Friend tmp = null;
			if(f != null && f.getPkId() != null) {
				tmp = this.getFriend(f.getPkId());
			}
			friends.set(i, tmp);
		}
		return ps;
	}
	
	public int getNoGroupFriendNum(LoginUser loginUser) throws Exception {
		if(loginUser == null){
			logger.debug("loginUser is null!");
			return 0;
		}
		
		return this.friendDAO.getNoGroupFriendNum(loginUser.getPkId());
	}

	public Friend getFriend(Long fid) throws Exception {
		Friend friend = this.friendDAO.getFriend(fid);
		if(friend != null)
			handleFriend(friend);
		return friend;
	}

	/**
	 * 处理好友
	 * 
	 * @param friend
	 * @throws Exception
	 */
	private void handleFriend(Friend friend) throws Exception{
		if(friend == null)
			return;
		
		if(friend.getFriendId() != null){
			friend.setFriendUser(this.userService.getUserById(friend.getFriendId()));
		}
		
		if(friend.getPkId() != null){
			friend.setGroups(this.getGroupByFriend(friend.getPkId()));
		}
	}

	public List<Long> listFriendIds(Long userId) throws Exception {
		PaginationSupport ps = this.listFriends(userId, null, -1, -1);
		List<Long> ids = new ArrayList<Long>();
		for(Object f : ps.getItems()){
			Friend friend = (Friend) f;
			ids.add(friend.getFriendId());
		}
		
		return ids;
	}

	public boolean saveApplyFriends(Long[] selectedUserIds, String applyMessage, boolean isEmailNotify, LoginUser loginUser, Long faid) 
			throws Exception {
		if(!Long.valueOf(-1).equals(faid)){
			//是再次申请
			FriendApply fa = this.getFriendApply(faid);
			fa.setApplyDate(new Date());
			fa.setIp(loginUser.getLoginIP());
			fa.setMessage(applyMessage);
			fa.setResult(ResourceType.AGREE_NOANSWER);
			fa.setRemark(StringUtils.EMPTY);
			
			this.friendDAO.updateEntity(fa);
			
			if(isEmailNotify){
				sendMail(fa.getInviteUserId(), fa, loginUser, applyMessage);
			}
			return true;
		}
		
		if(selectedUserIds == null || selectedUserIds.length <= 0){
			logger.debug("the selectedUserIds is null!");
			return false;
		}
		
		for(Long id : selectedUserIds){
			FriendApply fa = new FriendApply();
			fa.setApplyDate(new Date());
			fa.setApplyUserId(loginUser.getPkId());
			fa.setInviteUserId(id);
			fa.setIp(loginUser.getLoginIP());
			fa.setMessage(applyMessage);
			fa.setResult(ResourceType.AGREE_NOANSWER);		//默认是未回答的
			
			Long pkId = this.friendDAO.saveEntity(fa).getPkId();
			
			if(pkId == null)
				return false;
			
			if(isEmailNotify){
				sendMail(id, fa, loginUser, applyMessage);
			}
		}
		return true;
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param id					选中用户的ID
	 * @param fa					好友申请
	 * @param loginUser				当前登录者
	 * @param applyMessage			申请附言
	 * @throws Exception
	 */
	private void sendMail(Long id, FriendApply fa, LoginUser loginUser, String applyMessage) throws Exception{
		//发邮件通知
		User inviteUser = this.userService.getUserById(id);
		String email = inviteUser.getEmail();
		if(StringUtils.isNotEmpty(email) && ValidateUtils.isEmail(email)){
			String applyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fa.getApplyDate());
			String mailTemplate = MessageUtils.getProperties("mail.friend.template", 
					new Object[] {
						loginUser.getTruename(), 
						applyTime, 
						applyMessage, 
						MessageUtils.getProperties("messageboard.home", "http://sunhao.wiscom.com.cn:8089/message")
					});
			this.mailSend.sendMail("好友申请", mailTemplate, email);
		}
	}

	public List<Long> listApplyFriendIds(Long userId, Integer result) throws Exception {
		if(userId == null || Long.valueOf(-1).equals(userId)){
			logger.debug("userId is null!");
			return Collections.EMPTY_LIST;
		}
		if(!Integer.valueOf(0).equals(result) && !Integer.valueOf(2).equals(result)){
			//result既不为0也不为2,返回
			logger.debug("the result: '{}' is not what code want!", result);
			return Collections.EMPTY_LIST;
		}
		
		PaginationSupport ps = this.friendDAO.listApplyFriends(userId, result, ResourceType.RETURN_OTHER, -1, -1);
		List<Long> ids = new ArrayList<Long>();
		for(Object fa : ps.getItems()){
			FriendApply friendApply = (FriendApply) fa;
			ids.add(friendApply.getInviteUserId());
		}
		return ids;
	}

	public PaginationSupport listApplyFriends(Long userId, Integer result, int start, int num) throws Exception {
		if(userId == null || Long.valueOf(-1).equals(result)){
			logger.debug("userId is null!");
			return null;
		}
		PaginationSupport ps = this.friendDAO.listApplyFriends(userId, result, ResourceType.RETURN_PKID, start, num);
		List<FriendApply> applys = ps.getItems();
		for(int i = 0; i < applys.size(); i++){
			FriendApply fa = applys.get(i);
			FriendApply tmp = null;
			if(fa != null && fa.getPkId() != null) {
				tmp = this.getFriendApply(fa.getPkId());
			}
			applys.set(i, tmp);
		}
		
		return ps;
	}

	public FriendApply getFriendApply(Long faid) throws Exception {
		FriendApply fa = this.friendDAO.getFriendApply(faid);
		if(fa != null)
			handleFriendApply(fa);
		return fa;
	}
	
	/**
	 * 处理好友申请
	 * 
	 * @param friendApply
	 * @throws Exception
	 */
	private void handleFriendApply(FriendApply friendApply) throws Exception{
		if(friendApply == null)
			return;
		
		if(friendApply.getApplyUserId() != null){
			friendApply.setApplyUser(this.userService.getUserById(friendApply.getApplyUserId()));
		}
		
		if(friendApply.getInviteUserId() != null){
			friendApply.setInviteUser(this.userService.getUserById(friendApply.getInviteUserId()));
		}
	}
	
	public Friend getFriend(Long friendApplyId, Long userId) throws Exception {
		if(friendApplyId == null || userId == null){
			return null;
		}
		
		return this.friendDAO.getFriend(friendApplyId, userId);
	}

	public PaginationSupport getAllMyReceiveOrSend(LoginUser loginUser, String applyType, int start, int num) throws Exception {
		if(loginUser == null || !ObjectUtils.contain(new String[]{"receive", "send"}, applyType)){
			logger.debug("userId is null! or applyType is not macth!");
			return null;
		}
		
		PaginationSupport ps = this.friendDAO.getAllMyReceiveOrSend(loginUser, applyType, start, num);
		List<FriendApply> applys = ps.getItems();
		for(int i = 0; i < applys.size(); i++){
			FriendApply fa = applys.get(i);
			FriendApply tmp = null;
			if(fa != null && fa.getPkId() != null) {
				tmp = this.getFriendApply(fa.getPkId());
			}
			applys.set(i, tmp);
		}
		
		return ps;
	}

	public boolean cancelRequest(Long faid) throws Exception {
		if(faid == null || Long.valueOf(-1).equals(faid)){
			logger.debug("the friendApply id is null!");
			return false;
		}
		
		int result = this.friendDAO.deleteObject(faid, FriendApply.class);
		return result == 1;
	}

	public boolean ajaxHandleRequest(LoginUser loginUser, Long friendId, Integer agreeFlag, String disAgreeMessage) throws Exception {
		if(friendId == null || Long.valueOf(-1).equals(friendId) || agreeFlag == null || Integer.valueOf(-1).equals(agreeFlag)){
            logger.debug("this params maybe null");
            return false;
        }

        FriendApply friendApply = this.getFriendApply(friendId);
        if(!loginUser.getPkId().equals(friendApply.getInviteUserId())){
            logger.debug("current login user are not the invite user!");
            return false;
        }

        if(ResourceType.AGREE_YES.equals(agreeFlag)){
        	//同意
        	//1、先把正向的申请(别人向我申请的)置为同意
        	friendApply.setResult(ResourceType.AGREE_YES);

        	//2、创建一个好友(我同意了，他的好友列表中有我，但是我的好友列表还没有他，必须等他同意了我的申请)
        	Friend friend = new Friend();
        	friend.setApplyId(friendId);
        	friend.setUserId(friendApply.getApplyUserId());
        	friend.setFriendId(loginUser.getPkId());
        	friend.setBeFriendDate(new Date());
        	
        	Long fid = this.friendDAO.saveEntity(friend).getPkId();
        	this.friendDAO.updateEntity(friendApply);
        	
        	return fid != null;
        	
        } else if(ResourceType.AGREE_NO.equals(agreeFlag)){
        	//不同意
        	//1、直接把申请置为不同意
        	friendApply.setResult(ResourceType.AGREE_NO);
        	friendApply.setRemark(disAgreeMessage);
        	
        	this.friendDAO.updateEntity(friendApply);
        	return true;
        } else {
            logger.debug("this agree type '{}' is undefined in ResourceType.java", agreeFlag);
            return false;
        }
	}

	public boolean deleteFriend(LoginUser loginUser, Long friendId) throws Exception {
		if(loginUser == null || friendId == null || Long.valueOf(-1).equals(friendId)){
			logger.debug("this params maybe null!");
			return false;
		}
		
		return this.friendDAO.deleteFriend(loginUser.getPkId(), friendId);
	}

	public Long saveGroup(String groupName, LoginUser loginUser) throws Exception {
		if(StringUtils.isEmpty(groupName) || loginUser == null){
			logger.debug("empty group name and loginUser!");
			return null;
		}
		
		if("未分组".equals(groupName)){
			logger.debug("the group name can't be '{}'", groupName);
			return null;
		}
		
		FriendGroup group = new FriendGroup();
		group.setName(groupName);
		group.setOwer(loginUser.getPkId());
		group.setDeleteFlag(ResourceType.DELETE_NO_INTEGER);
		group.setCreateTime(new Date());
		
		this.friendDAO.saveEntity(group);
		
		return group.getPkId();
	}

	public PaginationSupport getFriendGroups(LoginUser loginUser, int start, int num) throws Exception {
		if(loginUser == null){
			return PaginationUtils.getNullPagination();
		}
		
		PaginationSupport ps = this.friendDAO.getFriendGroups(loginUser.getPkId(), start, num);
		List<FriendGroup> fgs = ps.getItems();
		for(int i = 0; i < fgs.size(); i++){
			FriendGroup fg = null;
			if(fgs.get(i).getPkId() != null){
				fg = this.getFriendGroup(fgs.get(i).getPkId());
			}
			fgs.set(i, fg);
		}
		
		return ps;
	}

	public FriendGroup getFriendGroup(Long fgid) throws Exception {
		if(fgid == null || Long.valueOf(-1).equals(fgid)){
			return null;
		}
		
		FriendGroup fg = this.friendDAO.getFriendGroup(fgid);
		if(fg != null)
			handleFriendGroup(fg);
		return fg;
	}
	
	private void handleFriendGroup(FriendGroup fg) throws Exception{
		if(fg.getOwer() != null){
			fg.setOwner(this.userService.getUserById(fg.getOwer()));
		}
		
		if(fg.getPkId() != null){
			fg.setUserNum(this.getGroupUserNum(fg.getPkId()));
		}
	}

	public boolean groupFunc(Long groupId, String groupName, String action) throws Exception {
		if(groupId == null || Long.valueOf(-1).equals(groupId) || !ObjectUtils.contain(new String[]{"edit", "delete"}, action)){
			logger.debug("groupId is null, or action is not in '[edit, delete]'");
			return false;
		}
		
		if("edit".equals(action)){
			//编辑
			FriendGroup fg = this.getFriendGroup(groupId);
			fg.setName(groupName);
			
			this.friendDAO.updateEntity(fg);
		} else if("delete".equals(action)){
			//删除
			this.friendDAO.deleteObject(groupId, FriendGroup.class);
		} else {
			return false;
		}
		
		return true;
	}

	public boolean saveFriendGroup(LoginUser loginUser, Long friendId, Long[] groups) throws Exception {
		if(loginUser == null || friendId == null){
			logger.debug("given wrong params!");
			return false;
		}
		
		//保存之前，先将原来的分组删除
		List<Long> guids = this.friendDAO.getGroupUserByFriendId(friendId);
		for(Long guid : guids){
			this.friendDAO.deleteObject(guid, FriendGroupUser.class);
		}
		
		//开始保存
		boolean result = groups.length == 0;
		for(Long groupId : groups){
			FriendGroupUser fgu = new FriendGroupUser();
			fgu.setFriendId(friendId);
			fgu.setGroupId(groupId);
			
			this.friendDAO.saveEntity(fgu);
			result = fgu.getPkId() != null;
			if(!result)
				break;
		}
		
		return result;
	}

	public int getGroupUserNum(Long groupId) throws Exception {
		if(groupId == null){
			logger.debug("groupId is null!");
			return 0;
		}
		
		return this.friendDAO.getGroupUserNum(groupId);
	}

	public List<FriendGroup> getGroupByFriend(Long friendId) throws Exception {
		if(friendId == null || Long.valueOf(-1).equals(friendId)){
			logger.debug("friend id is null!");
			return Collections.EMPTY_LIST;
		}
		List<Long> groupIds = this.friendDAO.getGroupByFriend(friendId);
		List<FriendGroup> groups = new ArrayList<FriendGroup>();
		for(Long groupId : groupIds){
			FriendGroup fg = this.getFriendGroup(groupId);
			if(fg != null)
				groups.add(fg);
		}
		
		return groups;
	}
}
