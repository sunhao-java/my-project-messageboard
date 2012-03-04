package com.message.base.spring;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-4 下午8:29
 */
public class DefaultController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String uri = request.getServletPath();
        return new ModelAndView("redirect:" + uri);
    }
}
