package com.message.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.message.base.spring.ApplicationContextUtil;
import com.message.base.spring.AuthContext;
import com.message.base.spring.AuthContextHelper;
import com.message.main.menu.exception.NoPermException;
import com.message.main.menu.service.MenuService;
import com.message.main.user.pojo.User;
import com.message.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 检查是否登录的spring拦截器
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-18 下午06:14:15
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	/**
	 * 发起请求,进入拦截器链，运行所有拦截器的preHandle方法， 
	 * 1.当preHandle方法返回false时，从当前拦截器往回执行所有拦截器的afterCompletion方法，再退出拦截器链。
	 * 2.当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。
	 * 		然后进入拦截器链，运行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	 */

    /**
     * 在Controller方法前进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        String url = request.getServletPath();
		if(url.indexOf(".do") != -1){
			System.out.println("request process info:");
			System.out.println("begin-----------------");
			System.out.println("url=[" + url + "]");
			System.out.println("port=[" + request.getServerPort() + "]");
			System.out.println("client=[" + request.getRemoteAddr() + "]");
			System.out.println("method=[" + request.getMethod() + "]");
			System.out.println("end-------------------");
		}

        User loginUser = (User) session.getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);

        if(url.indexOf(".do") != -1){
			if(loginUser == null){
				response.sendRedirect(request.getContextPath() + "/guest/index.do");
			} else {
                AuthContext authContext = new AuthContext();
                authContext.setLoginUser(loginUser);
                AuthContextHelper.setAuthContext(authContext);

                String perm = loginUser.getIsAdmin().toString();
                MenuService menuService = (MenuService) ApplicationContextUtil.getContext().getBean("menuService");
                //是否有权限查看某个菜单
                boolean isPerm = menuService.checkPerm(perm, url);

                if(isPerm){
                    return true;
                } else {
                    throw new NoPermException("您无权访问\"" + url + "\"！");
                }
			}
		} else {
			return true;
		}
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

    /**
     * 在Controller方法后进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		AuthContextHelper.setAuthContext(null);
	}

}
