package com.message.main.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.spring.ApplicationHelper;
import com.message.base.web.WebInput;
import com.message.main.user.service.UserService;

/**
 * 利用servlet解决头像显示的问题
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-17 上午02:33:22
 */
public class UserHeadServlet extends HttpServlet {
	private static final long serialVersionUID = -4366691937695711574L;
	private static final Logger logger = LoggerFactory.getLogger(UserHeadServlet.class);
	
	/**
	 * UserService在spring中bean的id
	 */
	private static final String BEAN_USERSERVICE = "userService";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebInput in = new WebInput(request);
		Long userId = in.getLong("userId", Long.valueOf(-1));
		Integer headType = in.getInt("headType", Integer.valueOf(2));
		
		if(Integer.valueOf(-1).equals(userId) || headType == null){
			logger.error("given userId or headType is null!");
			return;
		}
		
		response.setContentType("image/jpeg");

        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        UserService userService = (UserService) ApplicationHelper.getInstance().getBean(BEAN_USERSERVICE);
        
        if(userService == null){
			logger.error("can not find userService in spring context whit beanname '{}'!", BEAN_USERSERVICE);
			return;
		}
        
        logger.info("userService: '{}' bean is get success!", userService);
        
        byte[] imageChar = null;
		try {
			imageChar = userService.getHeadImage(userId, headType);
		} catch (Exception e) {
			logger.error("get head image byte[] is error!");
		}
		
		BufferedImage image =ImageIO.read(new ByteArrayInputStream(imageChar));
		
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	

}
