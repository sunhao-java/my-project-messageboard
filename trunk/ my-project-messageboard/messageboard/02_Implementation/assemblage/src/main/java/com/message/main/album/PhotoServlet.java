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

import com.message.base.attachment.pojo.Attachment;
import com.message.base.attachment.service.AttachmentService;
import com.message.base.spring.ApplicationHelper;
import com.message.base.utils.FileUtils;
import com.message.base.utils.ImageUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;

/**
 * 处理图像显示的servlet.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-22 下午11:55:19
 */
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = -450919137470296605L;
	private static final Logger logger = LoggerFactory.getLogger(PhotoServlet.class);
	
	/**
	 * AttachmentService在spring中bean的id
	 */
	private static final String BEAN_NAME = "attachmentService";
	
	private static final int IMAGE_DIV_WIDTH = 720;
	private static final int IMAGE_DIV_HEIGHT = 540;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");

        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
		WebInput in = new WebInput(request);
		String filePath = in.getString("filePath", StringUtils.EMPTY);
		Long fileId = in.getLong("fileId", Long.valueOf(-1));
		boolean check = in.getBoolean("check", false);
		
		String path = this.getFilePath(filePath, fileId);
		
		byte[] imageChar = null;
		try {
			imageChar = FileUtils.getFileByte(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageChar));
		
		if(check){
			//查看详情的页面需要做此判断
			int height = image.getHeight();
			int width = image.getWidth();

			int[] size = null;
			if(height > IMAGE_DIV_HEIGHT || width > IMAGE_DIV_WIDTH){
				size = ImageUtils.getSizeByPercent(width, height, IMAGE_DIV_WIDTH, IMAGE_DIV_HEIGHT);
			} else {
				size = new int[]{width, height};
			}
			
			width = size[0];
			height = size[1];
			BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			image2.getGraphics().drawImage(image, 0, 0, width, height, null);
			image = image2;
		}
		
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	private String getFilePath(String filePath, Long fileId){
		String path = StringUtils.EMPTY;
		if(Integer.valueOf(-1).equals(fileId) && StringUtils.isEmpty(filePath)){
			logger.error("fileId or filePath, you must be given one at last!");
			return StringUtils.EMPTY;
		}
		
        if(StringUtils.isNotEmpty(filePath)){
        	path = filePath;
        } else if(fileId != null){
        	AttachmentService attachmentService = (AttachmentService) ApplicationHelper.getInstance().getBean(BEAN_NAME);
            
            if(attachmentService == null){
    			logger.error("can not find AttachmentService in spring context whit beanname '{}'!", BEAN_NAME);
    			return StringUtils.EMPTY;
    		}
            
            logger.info("AttachmentService: '{}' bean is get success!", attachmentService);
            
            Attachment attachment = null;
            try {
            	attachment = attachmentService.loadAttachment(fileId);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		if(attachment == null || StringUtils.isEmpty(attachment.getFilePath())){
    			logger.error("can't get Attachment with given fileId:'{}'", fileId);
    			return StringUtils.EMPTY;
    		}
    		
    		path = attachment.getFilePath();
        }
        
        return path;
	}
	
}
