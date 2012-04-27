package com.message.main.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserPrivacyService;
import com.message.main.user.service.UserService;
import com.message.resource.ResourceType;

/**
 * 用户操作的controller
 * @author sunhao(sunhao.java@gmail.com)
 */
public class UserController extends ExtMultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static WebInput in = null;
	private static WebOutput out = null;
	
	private UserService userService;
	private UserPrivacyService userPrivacyService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserPrivacyService(UserPrivacyService userPrivacyService) {
		this.userPrivacyService = userPrivacyService;
	}
	
	/**
	 * 进入用户信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		Long viewUserId = in.getLong("viewUserId", Long.valueOf(-1));
        User user = null;
        if(!Long.valueOf(-1).equals(viewUserId)){
            user = new User(viewUserId);
            params.put("customer", "true");
            params.put("viewwhoname", this.userService.getUserById(viewUserId).getTruename());
            params.put("privacy", this.userPrivacyService.getUserPrivacy(viewUserId));

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
        
        this.userService.saveEdit(user);
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
	
}

