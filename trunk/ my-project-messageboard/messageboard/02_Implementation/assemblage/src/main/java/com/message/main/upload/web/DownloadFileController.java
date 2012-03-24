package com.message.main.upload.web;

import com.message.base.utils.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载的controller
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-25 上午6:43
 */
public class DownloadFileController extends ParameterizableViewController {
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Long fileId = Long.valueOf(-1);

        String url = this.getViewName();
        String path = request.getRequestURI();

        path = StringUtils.isEmpty(path) ? StringUtils.EMPTY : path;
        String[] pathInfo = path.split("/");
        if(pathInfo != null && pathInfo.length > 0){
            fileId = Long.valueOf(pathInfo[pathInfo.length - 1]);
        }

        return new ModelAndView(url + "?fileId=" + fileId);
    }
    
}
