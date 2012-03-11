package com.message.main.home.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.i18n.SystemConfig;
import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.resource.ResourceType;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-5 下午8:06
 */
public class GuestController extends ExtMultiActionController {
    private WebInput in = null;
    private WebOutput out = null;

    private UserService userService;

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
        User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
        boolean guestAuth = SystemConfig.getBooleanProperty("system.auth.guest", false);
        if (user != null) {
            view = "redirect:/home/inMessageIndex.do";
        } else {
            view = "user.login";
        }
        params.put("guestAuth", guestAuth);
        return new ModelAndView(view, params);
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @param user
     * @return
     */
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, User user) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	
        in = new WebInput(request);
        logger.debug("username is " + user.getUsername());
        int status = 0;
        try {
            status = this.userService.userLogin(user, in);
            if (status == 0) {
                //跳转到另外一个controller
                User dbUser = this.userService.getUserByName(user.getUsername());
                dbUser.setLoginIP(in.getClientIP());
                /**
                 * 将登录用户放入session中
                 * 设置session的生命周期为1小时(即：用户登录后不做任何操作1小时后将被强制登出)
                 */
                HttpSession session = in.getSession();
                session.setAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION, dbUser);
                in.setMaxInactiveInterval(session, 1 * 60 * 60 * 1000);

                return new ModelAndView("redirect:/home/inMessageIndex.do");
            } else {
                if (status == 1) {
                	params.put("message", "用户名错误");
                } else if (status == 2) {
                    params.put("message", "密码错误");
                } else if (status == 3) {
                	params.put("message", "用户名密码必填");
                } else if (status == 4) {
                	params.put("message", "未进行邮箱验证，请验证！");
                } else if (status == 5) {
                	params.put("message", "用户已被停用");
                }
                params.put("status", status);
                return new ModelAndView("redirect:/guest/index.do", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = 3;        //网络有错误
            logger.error(e.getMessage(), e);
            request.setAttribute("status", status);
            return new ModelAndView("redirect:/guest/index.do");
        }
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
    public ModelAndView checkUser(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
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
                view = "redirect:/guest/index.do";
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
