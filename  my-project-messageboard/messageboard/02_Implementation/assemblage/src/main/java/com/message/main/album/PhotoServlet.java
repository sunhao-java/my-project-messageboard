package com.message.main.album;

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
import com.message.base.utils.FileUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.main.upload.pojo.UploadFile;
import com.message.main.upload.service.GenericUploadService;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-22 下午11:55:19
 */
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = -450919137470296605L;
	private static final Logger logger = LoggerFactory.getLogger(PhotoServlet.class);
	
	/**
	 * GenericUploadService在spring中bean的id
	 */
	private static final String BEAN_NAME = "genericUploadService";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebInput in = new WebInput(request);
		Long fileId = in.getLong("fileId", Long.valueOf(-1));
		
		if(Integer.valueOf(-1).equals(fileId)){
			logger.error("given fileId is null!");
			return;
		}
		
		response.setContentType("image/jpeg");

        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        GenericUploadService genericUploadService = (GenericUploadService) ApplicationHelper.getInstance().getBean(BEAN_NAME);
        
        if(genericUploadService == null){
			logger.error("can not find userService in spring context whit beanname '{}'!", BEAN_NAME);
			return;
		}
        
        logger.info("userService: '{}' bean is get success!", genericUploadService);
        
        UploadFile file = null;
        try {
			file = genericUploadService.loadFile(fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(file == null || StringUtils.isEmpty(file.getFilePath())){
			logger.error("can't get UploadFile with given fileId:'{}'", fileId);
			return;
		}
		
		byte[] imageChar = null;
		try {
			imageChar = FileUtils.getFileByte(file.getFilePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedImage image =ImageIO.read(new ByteArrayInputStream(imageChar));
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
}
