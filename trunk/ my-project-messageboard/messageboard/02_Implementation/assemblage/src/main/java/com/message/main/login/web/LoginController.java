package com.message.main.login.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.login.service.LoginService;
import com.message.resource.ResourceType;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-22 下午2:08
 */
public class LoginController extends ExtMultiActionController {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private WebInput in;
    private WebOutput out;

    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 登录的方法
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        in = new WebInput(request);
        out = new WebOutput(request, response);
        Map<String, Object> params = new HashMap<String, Object>();
        String loginName = in.getString("username", StringUtils.EMPTY);
        String password = in.getString("password", StringUtils.EMPTY);

        Integer result = this.loginService.login(in, out, loginName, password);

        String view = StringUtils.EMPTY;

        if(Integer.valueOf(0).equals(result)){
            view = "redirect:/home/inMessageIndex.do";
        } else {
            if (Integer.valueOf(1).equals(result)) {
                params.put("message", "用户名错误！");
            } else if (Integer.valueOf(2).equals(result)) {
                params.put("message", "密码错误！");
            } else if (Integer.valueOf(3).equals(result)) {
                params.put("message", "用户名密码必填！");
            } else if (Integer.valueOf(4).equals(result)) {
                params.put("message", "未进行邮箱验证，请验证！");
            } else if (Integer.valueOf(5).equals(result)) {
                params.put("message", "用户已被停用！");
            } else if (Integer.valueOf(6).equals(result)) {
                params.put("message", "验证码错误！");
            }
            params.put("status", result);
            view = "redirect:/guest/index.do";
        }

        return new ModelAndView(view, params);
    }

    /**
     * 登出系统
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        out = new WebOutput(request, response);
		in = new WebInput(request);
		JSONObject obj = new JSONObject();
        
		Integer result = this.loginService.logout(in);

        if(Integer.valueOf(1).equals(result)){
            obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
        } else {
            obj.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
        }

		out.toJson(obj);
		return null;
    }
    
}
