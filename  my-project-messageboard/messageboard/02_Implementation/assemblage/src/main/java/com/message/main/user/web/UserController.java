package com.message.main.user.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.ModelAndMethod;
import com.message.base.spring.SimpleController;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.album.pojo.Album;
import com.message.main.album.service.AlbumService;
import com.message.main.friend.po.Friend;
import com.message.main.friend.service.FriendService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.message.service.MessageService;
import com.message.main.tweet.service.TweetService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserPrivacyService;
import com.message.main.user.service.UserService;

/**
 * 用户操作的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class UserController extends SimpleController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static WebInput in = null;
	private static WebOutput out = null;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserPrivacyService userPrivacyService;
	@Autowired
	private FriendService friendService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private TweetService tweetService;

	/**
	 * 进入用户信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		Long viewUserId = in.getLong("viewUserId", Long.valueOf(-1));
        User user = null;
        if(!Long.valueOf(-1).equals(viewUserId)){
            user = new User(viewUserId);
            params.put("customer", "true");
            params.put("viewwhoname", this.userService.getUserById(viewUserId).getTruename());
            params.put("privacy", this.userPrivacyService.getUserPrivacy(viewUserId));
            if(loginUser != null)
            	params.put("isFriend", this.friendService.isFriend(viewUserId, loginUser.getPkId()));

            user = this.userService.getUserById(user.getPkId());
            user = this.userService.addLoginInfo(user, viewUserId == null ? false : true);
            params.put("user", user);
        } else {
            params.put("user", AuthContextHelper.getAuthContext().getLoginUser());
        }
		return new ModelAndView("user.info", params);
	}
	
	/**
	 * 进入编辑用户信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView editUserInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("current", "base");
		return new ModelAndView("user.edit.info", params);
	}
	
	/**
	 * 保存修改后的用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONObject obj = new JSONObject();
        
        this.userService.saveEdit(user, in);
        obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 进入修改密码页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView changePsw(HttpServletRequest request, HttpServletResponse response){
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		return new ModelAndView("user.password.change", params);
	}
	
	/**
	 * 检查原密码是否正确
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkPsw(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		String oldPassword = in.getString("oldPassword", StringUtils.EMPTY);
		JSONObject obj = new JSONObject();
        String md5OldPsw = MD5Utils.MD5Encode(oldPassword);
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        if(md5OldPsw.equals(loginUser.getPassword())){
            obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
        } else {
            obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
        }
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 保存新密码
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ModelAndView savePassword(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject obj = new JSONObject();
		if(user != null)
            obj.put(ResourceType.AJAX_STATUS, this.userService.savePassword(user) ?
                    ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
        
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 列出所有的用户
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listAllUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
		in = new WebInput(request);
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		String permission = in.getString("permission", StringUtils.EMPTY);

		Map<String, Object> params = new HashMap<String, Object>();
        params.put("paginationSupport", this.userService.listAllUser(start, num, user));
		params.put("permission", permission);
		return new ModelAndView("user.list.alluser", params);
	}
	
	/**
	 * 删除用户(软删除)（可以删除一条也可批量删除）
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject obj = new JSONObject();
		String pkids = in.getString("pkIds", StringUtils.EMPTY);
		if(StringUtils.isNotEmpty(pkids)){
            obj.put(ResourceType.AJAX_STATUS, this.userService.deleteUser(pkids) ?
                        ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE );
		} else {
			obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
		}
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 管理员设置用户权限
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView managerPerm(HttpServletRequest request, HttpServletResponse response, User user) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject obj = new JSONObject();
		Long pkId = in.getLong("userPkId", 0L);
		boolean opertion = in.getBoolean("opertion", Boolean.FALSE);
        
        obj.put(ResourceType.AJAX_STATUS, this.userService.managerPerm(pkId, opertion) ?
                        ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(obj);
		return null;
	}
	
	/**
	 * 弹框进入新增用户界面
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public ModelAndView inAddUserJsp(HttpServletRequest request, HttpServletResponse response, User user){
		Map<String, Object> params = new HashMap<String, Object>();
		return new ModelAndView("user.add", params);
	}

    /**
     * 进入修改头像的页面
     * 
     * @param request
     * @param response
     * @param user
     * @return
     */
    public ModelAndView inEditHead(HttpServletRequest request, HttpServletResponse response, User user){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("current", "head");
        return new ModelAndView("user.editHead", params);
    }
    
    /**
     * 微博秀设置
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception 
     */
    public ModelAndView weibo(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("current", "weibo");
    	return new ModelAndView("weibo.setting", params);
    }
	
    /**
     * 保存微博秀设置
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception 
     */
    public ModelAndView saveWeibo(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("current", "weibo");
    	Integer weiboType = in.getInt("weiboType", Integer.valueOf(-1));
    	String uid = in.getString("uid", StringUtils.EMPTY);
    	String verifier = in.getString("verifier", StringUtils.EMPTY);
    	
    	boolean result = this.userService.saveWeibo(loginUser, weiboType, uid, verifier, in);
    	params.put(ResourceType.AJAX_STATUS, result ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	
    	out.toJson(params);
    	return null;
    }
    
    /**
     * 移除微博秀
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView removeWeibo(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	boolean result = this.userService.removeWeibo(loginUser, in);
    	
    	params.put(ResourceType.AJAX_STATUS, result ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
    	out.toJson(params);
    	return null;
    }
    
    /**
     * 进入用户主页都从此方法,如果是查看自己的主页,跳转到myProfile方法,查看别人的主页,跳转到userProfile方法
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     */
    public ModelAndMethod profile(WebInput in, WebOutput out, LoginUser loginUser){
    	//Map<String, Object> params = new HashMap<String, Object>();
    	Long uid = in.getLong("uid", Long.valueOf(-1));
    	if(!Long.valueOf(-1).equals(uid) && !loginUser.getPkId().equals(uid)){
    		//不是进入自己的主页
    		return new ModelAndMethod("user", "userProfile");
    	} else {
    		//进入自己的主页
    		return new ModelAndMethod("user", "myProfile");
    	}
    }
    
    /**
     * 进入用户的主页
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception 
     */
    public ModelAndView myProfile(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	params.put("loginUser", this.userService.getUserById(loginUser.getPkId()));
    	
    	//所有好友
    	List<Friend> friends = this.friendService.listFriends(loginUser.getPkId(), -1L, 0, 6, StringUtils.EMPTY).getItems();
    	params.put("friends", friends);
    	
    	//所有相册
    	List<Album> albums = this.albumService.getAlbumList(loginUser.getPkId(), 0, 6).getItems();
    	params.put("albums", albums);
    	
    	//博客文章数目
    	params.put("blogNum", this.messageService.getLoginUserMessageCount(loginUser.getPkId()));
    	
        return new ModelAndView("profile", params);
    }
    
    /**
     * 查看好友的主页
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView userProfile(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	Long uid = in.getLong("uid", Long.valueOf(-1));
    	User user = this.userService.getUserById(uid);
    	List<Long> fids = this.friendService.listFriendIds(uid);
    	int blogNum = this.messageService.getLoginUserMessageCount(uid);
    	List<Album> albums = this.albumService.getViewAlbums(loginUser, uid, 0, 5).getItems();
    	PaginationSupport messages = this.messageService.getMyMessages(0, 5, uid, null);
    	PaginationSupport tweets = this.tweetService.getTweetsByUId(uid, 0, 10);
    	
    	params.put("user", user);
    	params.put("friendIds", fids);
    	params.put("blogNum", blogNum);
    	params.put("albums", albums);
    	params.put("blogs", messages);
    	params.put("tweets", tweets);
    	return new ModelAndView("user.profile", params);
    }

    /**
     * 根据好友分组获取用户
     *
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView loadUserByGroup(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
        Map<String, Object> params = new HashMap<String, Object>();
        Long groupId = in.getLong("pkId");
        String keyword = in.getString("keyword");
        int num = 28;
        int start = SqlUtils.getStartNum(in, num);
        //所有好友
        PaginationSupport ps = this.friendService.listFriends(loginUser.getPkId(), groupId, start, num, keyword);
        List<Friend> friends = ps.getItems();
        List<User> users = new ArrayList<User>();
        for(Friend f : friends){
            if(f.getFriendId() != null)
                users.add(this.userService.getUserById(f.getFriendId()));
        }
        ps.setItems(users);
        params.put("paginationSupport", ps);
        out.toJson(params);
        return null;
    }
}

