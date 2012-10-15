package com.message.main.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.email.MailSend;
import com.message.base.pagination.PaginationSupport;
import com.message.base.properties.MessageUtils;
import com.message.base.spring.ApplicationHelper;
import com.message.base.utils.FileUtils;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.main.ResourceType;
import com.message.main.event.pojo.BaseEvent;
import com.message.main.event.service.EventService;
import com.message.main.history.service.HistoryService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.message.service.MessageService;
import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.LoginUserHelper;

/**
 * 用户操作的service 实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	/**
	 * 用户未设置头像时显示的默认头像
	 */
	private static final String HEAD_IMAGE_BLANK = "/blank.jpg";
    /**
     * 用户未设置头像时显示的默认头像所在文件夹
     */
    private static final String HEAD_IMAGE_FLODER = "/image/head/";
	
	private UserDAO userDAO;
	private HistoryService historyService;
	private MessageService messageService;
	private EventService eventService;
	private MailSend mailSend;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public void setMailSend(MailSend mailSend) {
		this.mailSend = mailSend;
	}

	public boolean registerUser(User user) throws Exception {
		if(user != null){
			Long pkId = null;
			try {
				if(StringUtils.isNotEmpty(user.getPassword())){
					user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
				} else {
					user.setPassword(MD5Utils.MD5Encode(ResourceType.DEFAULT_PASSWORD));
				}
				user.setCreateDate(new Date());
				user.setDeleteFlag(ResourceType.DELETE_NO);
				user.setIsAdmin(ResourceType.IS_ADMIN_NO);
				user.setIsMailCheck(ResourceType.MAIL_CHECK_NO);
				pkId = this.userDAO.registerUser(user);
				if(pkId != null){
					if(user.getEmail() != null){
						String mailTitle = MessageUtils.getProperties("mail.confirm.title", "邮件验证");
						this.mailSend.sendMail(mailTitle, makeMailContent(user), user.getEmail());
					}
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 组装要发送邮件的内容
	 * @param pkId
	 * @param username
	 * @return
	 */
	private String makeMailContent(User user){
		String urlConfirm = MessageUtils.getProperties("user.confirm", "http://sunhao.wiscom.com.cn:8089/message/user/emailConfirm.do?");
		String userNameCode = MessageUtils.getProperties("mail.confirm.key", "usernameCode");
		String confirmUserId = MessageUtils.getProperties("mail.confirm.userid", "userid");	
		StringBuffer sb = new StringBuffer();
		String username_md5 = MD5Utils.MD5Encode(user.getUsername());
		sb.append(urlConfirm).append(userNameCode).append("=").append(username_md5).append("&").append(confirmUserId)
							.append("=").append(user.getPkId());
		
		Date now = new Date();
		String nowStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now);
		
		String content = MessageUtils.getProperties("mailTemplate", new Object[]{nowStr, user.getUsername(), sb.toString()});
		
		return content;
	}

	public int userLogin(User user, WebInput in) throws Exception {
		if(StringUtils.isEmpty(user.getUsername())){
			return 3;		//用户名为空
		}
		User dbUser = this.userDAO.getUserByName(user.getUsername());
		String loginPsw = MD5Utils.MD5Encode(user.getPassword());
		if(dbUser == null){
			return 1;		//用户名错误
		} else {
			if(dbUser.getDeleteFlag() != ResourceType.DELETE_YES){
				if(loginPsw.equals(dbUser.getPassword())){
					if(dbUser.getIsMailCheck() != ResourceType.MAIL_CHECK_YES){
						return 4;	//未进行邮箱验证
					} else {
						this.historyService.saveLoginHistory(in, dbUser);
						return 0;	//正确
					}
				} else {
					return 2;	//密码错误
				}
			} else {
				return 5;		//用户被删除
			}
		}
	}

	public User getUserById(Long userId) throws Exception {
		return this.userDAO.getUserById(userId);
	}

	public boolean checkUser(User user) throws Exception {
		String name = user.getUsername();
		User dbUser = null;
		if(StringUtils.isNotEmpty(name)){
			dbUser = this.userDAO.getUserByName(name);
		}
		return dbUser == null ? true : false;
	}

	public User getUserByName(String username) throws Exception {
		User dbUser = null;
		if(StringUtils.isNotEmpty(username)){
			dbUser = this.userDAO.getUserByName(username);
		}
		return dbUser;
	}

	public User addLoginInfo(User user, boolean view) throws Exception {
		if(user != null){
			user.setLastLoginTime(this.historyService.getLastLoginTime(user.getPkId(), view));
			user.setLoginCount(this.historyService.getLoginCount(user.getPkId()));
			user.setMessageCount(this.messageService.getLoginUserMessageCount(user.getPkId()));
		}
		return user;
	}

	public void saveEdit(User user, WebInput in) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		User dbUser = null;
		if(user != null){
			if(user.getPkId() != null){
				dbUser = this.userDAO.getUserById(user.getPkId());
				if(dbUser != null){
					dbUser.setAddress(user.getAddress());
					dbUser.setPhoneNum(user.getPhoneNum());
					dbUser.setEmail(user.getEmail());
					dbUser.setQq(user.getQq());
					dbUser.setHomePage(user.getHomePage());
					
					this.userDAO.updateUser(dbUser);
					
					LoginUserHelper.syncLoginUser(dbUser, in);
					
					String eventMsg = MessageUtils.getProperties("event.message.user.edit", new Object[]{dbUser.getTruename(), dbUser.getPkId()});
					this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_EDIT, user.getPkId(),
							ResourceType.USER_TYPE, loginUser.getPkId(), loginUser.getLoginIP(), eventMsg));
				}
			}
		}
	}

	public boolean savePassword(User user) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		if(StringUtils.isNotEmpty(user.getPassword()) && user.getPkId() != null){
			User dbUser = this.userDAO.getUserById(user.getPkId());
			if(dbUser != null){
				dbUser.setPassword(MD5Utils.MD5Encode(user.getPassword()));
				this.userDAO.updateUser(dbUser);
				
				String eventMsg = MessageUtils.getProperties("event.message.psw.edit", new Object[]{dbUser.getTruename(), dbUser.getPkId()});
				this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_EDIT, user.getPkId(),
						ResourceType.USER_TYPE, loginUser.getPkId(), loginUser.getLoginIP(), eventMsg));
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public PaginationSupport listAllUser(int start, int num, User user)
			throws Exception {
		return listAllUser(start, num, user, false);
	}
	
	public PaginationSupport listAllUser(int start, int num, User user, boolean containSelf) throws Exception {
		List<Long> notContain = new ArrayList<Long>();
		if(!containSelf){
			//不包含本身
			LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
			notContain.add(loginUser.getPkId());
		}
		return this.userDAO.listAllUser(start, num, user, notContain);
	}

	public boolean deleteUser(String pkids) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		String[] pkIds = pkids.split(",");
		if(pkIds != null && pkIds.length > 0){
			for(String pkId : pkIds){
				User dbUser = this.userDAO.getUserById(Long.valueOf(pkId));
				
				if(dbUser != null){
					dbUser.setDeleteFlag(ResourceType.DELETE_YES);
					String eventMsg = MessageUtils.getProperties("event.message.user.delete", new Object[]{dbUser.getTruename(), dbUser.getPkId()});
					this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_DELETE, dbUser.getPkId(),
							ResourceType.USER_TYPE, loginUser.getPkId(), loginUser.getLoginIP(), eventMsg));
					this.userDAO.updateUser(dbUser);
				}
			}
			return true;
		} 
		
		return false;
	}

	public boolean managerPerm(long pkId, boolean opertion) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		User dbUser = this.userDAO.getUserById(pkId);
		if(dbUser != null){
			dbUser.setIsAdmin(opertion ? ResourceType.IS_ADMIN_YES : ResourceType.IS_ADMIN_NO);
			this.userDAO.updateUser(dbUser);
			String eventMsg = MessageUtils.getProperties("event.message.userPerm", new Object[]{dbUser.getTruename(), dbUser.getPkId()});
			this.eventService.publishEvent(new BaseEvent(loginUser.getPkId(), ResourceType.EVENT_EDIT, dbUser.getPkId(),
					ResourceType.USER_TYPE, loginUser.getPkId(), loginUser.getLoginIP(), eventMsg));
			return true;
		}
		return false;
	}

	public boolean emailConfirm(WebInput in) throws Exception {
		final String USERNAME_CODE = MessageUtils.getProperties("mail.confirm.key", "usernameCode");
		final String CONFIRM_USER_ID = MessageUtils.getProperties("mail.confirm.userid", "userid");
		
		String usernameCode = in.getString(USERNAME_CODE, StringUtils.EMPTY);
		Long userid = in.getLong(CONFIRM_USER_ID, 0L);
		
		User dbUser = this.userDAO.getUserById(userid);
		if(dbUser == null){
			return false;
		} else {
			String dbUsername = dbUser.getUsername();
			String dbusernameMD5 = MD5Utils.MD5Encode(dbUsername);
			if(dbusernameMD5.equals(usernameCode)) {
				dbUser.setIsMailCheck(ResourceType.MAIL_CHECK_YES);
				
				this.userDAO.updateUser(dbUser);
				return true;
			} else {
				return false;
			}
		}
	}

	public byte[] getHeadImage(Long userId, Integer headType) throws Exception {
		if(Long.valueOf(-1).equals(userId) || userId == null || headType == null){
			logger.error("given userId or headType is null!");
			return null;
		}
		User user = this.getUserById(userId);
		
		if(user == null){
			logger.error("no user found!");
		}
		
		StringBuffer imagePath = new StringBuffer();
		//当可以取得头像的时候用
		String imageDir = ResourceType.USER_IMAGE_FOLDER_PATH;
		//当用户未设置头像的时候使用
		String rootPath = ApplicationHelper.getInstance().getRootPath();
		//头像大小
		String imageSize = StringUtils.EMPTY;
		
		if(Integer.valueOf(0).equals(headType)){
			imageSize = ResourceType.IMAGE_SIZE_NORMAL;
		} else if(Integer.valueOf(1).equals(headType)){
			imageSize = ResourceType.IMAGE_SIZE_BIG;
		} else {
			imageSize = ResourceType.IMAGE_SIZE_SMALL;
		}
		
		if(StringUtils.isEmpty(user.getHeadImage())){
            //默认头像
			imagePath.append(rootPath).append(HEAD_IMAGE_FLODER)
                    .append(imageSize).append("/")
                    .append(HEAD_IMAGE_BLANK);
		} else {
			imagePath.append(imageDir)								//系统设置上传路径
						.append("/").append(imageSize).append("/")	//头像大小
						.append(user.getHeadImage());				//头像实际所在路径
		}
		
		return FileUtils.getFileByte(imagePath.toString());
	}

    public void updateUser(User user, WebInput in) throws Exception {
		this.userDAO.updateUser(user);
		LoginUserHelper.syncLoginUser(user, in);
	}

	public boolean saveWeibo(LoginUser loginUser, Integer weiboType, String uid, String verifier, WebInput in) throws Exception {
		if(loginUser == null || Integer.valueOf(-1).equals(weiboType) || StringUtils.isEmpty(uid)){
			logger.debug("loginUser, weiboType, uid or verifier is null!");
			return false;
		}
		
		Long pkId = loginUser.getPkId();
		User user = this.getUserById(pkId);
		
		if(user == null){
			logger.debug("user is null with pkId: '{}'!", pkId);
			return false;
		}
		
		String weiboUrl = "";
		if(Integer.valueOf(1).equals(weiboType)){
			//新浪微博
			weiboUrl = "http://widget.weibo.com/weiboshow/index.php?language=&width=220&height=400&fansRow=2" +
					"&ptype=1&speed=0&skin=1&isTitle=0&noborder=0&isWeibo=1&isFans=0&dpc=1&uid=" + uid + "&verifier=" + verifier;
		} else if(Integer.valueOf(2).equals(weiboType)){
			//腾讯微博
			weiboUrl = "http://show.v.t.qq.com/index.php?c=show&a=index&n=" + uid + "&w=220&h=400&fl=2&l=8&o=17&co=4&" +
					"cs=D2D2D2_B5B5B5_595959_354E71";
		} else {
			return false;
		}
		
		user.setWeiboType(weiboType);
		user.setWeiboUrl(weiboUrl);
		
		this.updateUser(user, in);
		
		return true;
	}

	public boolean removeWeibo(LoginUser loginUser, WebInput in) throws Exception {
		if(loginUser == null){
			logger.debug("loginUser is null!");
			return false;
		}
		
		User user = this.getUserById(loginUser.getPkId());
		
		user.setWeiboType(0);
		user.setWeiboUrl(StringUtils.EMPTY);
		this.updateUser(user, in);
		return true;
	}

	
	
}
