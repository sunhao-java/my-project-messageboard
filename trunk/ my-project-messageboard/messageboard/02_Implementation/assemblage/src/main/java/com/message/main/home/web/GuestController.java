package com.message.main.home.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.properties.SystemConfig;
import com.message.base.spring.SimpleController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-5 下午8:06
 */
public class GuestController extends SimpleController {
    private WebInput in = null;
    private WebOutput out = null;

    private UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(GuestController.class);

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 进入用户登录页面
     *
     * @param request
     * @param response
     * @return
     */
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        in = new WebInput(request);
        Map<String, Object> params = new HashMap<String, Object>();
        String view = "";
        String goUrl = in.getString("goUrl", StringUtils.EMPTY);
        User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
        if (user != null) {
            view = "redirect:/home.do";
        } else {
        	params.put("goUrl", goUrl);
            view = "user.login";
        }
        params.put("guestAuth", SystemConfig.getBooleanProperty("system.auth.guest", Boolean.FALSE));
        return new ModelAndView(view, params);
    }

    /**
     * 判断注册的用户是否已存在
     *
     * @param request
     * @param response
     * @param user
     * @return
     * @throws Exception
     */
    public ModelAndView check(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        out = new WebOutput(request, response);
        JSONObject obj = new JSONObject();
        String status = "";
        try {
            status = this.userService.checkUser(user) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE;
            obj.put(ResourceType.AJAX_STATUS, status);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        out.toJson(obj);
        return null;
    }

    /**
     * 注册用户
     *
     * @param request
     * @param response
     * @param user
     * @return
     * @throws Exception
     */
    public ModelAndView register(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        out = new WebOutput(request, response);
        JSONObject params = new JSONObject();
        try {
            boolean status = this.userService.registerUser(user);
            if (status) {
                params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
            } else {
                params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        out.toJson(params);
        return null;
    }

    /**
     * 当注册人在验证激活用户邮件中点击链接时触发对用户进行激活
     *
     * @param request
     * @param response
     * @return
     */
    public ModelAndView emailConfirm(HttpServletRequest request, HttpServletResponse response) {
        out = new WebOutput(request, response);
        in = new WebInput(request);
        String view = "";
        try {
            boolean result = this.userService.emailConfirm(in);
            if (result) {
                view = "redirect:/guest.do";
            } else {
                view = "redirect:http://www.baidu.com";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView(view);
    }

    /**
     * 出现错误页面跳转
     *
     * @param request
     * @param response
     * @return
     */
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response){
        String exception = "您访问的链接可能被禁用，或者不存在！";
        return new ModelAndView("message.error.page", "exception", exception);
    }
}
