package com.message.main.upload.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.message.base.utils.FileUtils;
import com.message.base.utils.ImageUtils;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.main.ResourceType;
import com.message.main.upload.service.UploadService;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * 上传头像的service实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-17 上午03:06:14
 */
public class UploadServiceImpl implements UploadService {
	private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
	
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void uploadHead(Long userId, MultipartFile file, WebInput in) throws Exception {
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if(StringUtils.isNotEmpty(fileName)){
			fileName = MD5Utils.MD5Encode(fileName) + suffix;
		}
		
		String path = ResourceType.USER_IMAGE_FOLDER_PATH;
		
		makeImageBySize(path, file, fileName);
		
		User user = this.userService.getUserById(userId);
		if(user == null){
			logger.error("can not get any user by userId '{}'", userId);
		}
		
		user.setHeadImage(fileName);
		this.userService.updateUser(user, in);
	}
	
	private void makeImageBySize(String savePath, MultipartFile file, String fileName) throws Exception{
		String filePath = StringUtils.EMPTY;
		File f = null;
		String fileRealPath = StringUtils.EMPTY;
		byte[] fileByte = file.getBytes();
		//临时路存图片放径，不删除
		String srcPath = savePath + "/srcImage/";
		
		if(!new File(srcPath).exists()){
			org.apache.commons.io.FileUtils.forceMkdir(new File(srcPath));
		}
		FileUtils.createFile(srcPath + fileName, fileByte);
		
		for(int i = 0; i < ResourceType.IMAGE_SIZE_LIST.length; i++){
			String[] size = ResourceType.IMAGE_SIZE_LIST[i].split("x");
			int width = Integer.valueOf(size[0]);
			int height = Integer.valueOf(size[1]);
			
			filePath = savePath + ResourceType.IMAGE_SIZE_LIST[i] + "/";
			
			f = new File(filePath);
			
			if(!f.exists()){
				org.apache.commons.io.FileUtils.forceMkdir(f);
			}
			//对图片大小进行切割
			File newFile = ImageUtils.getFixedIcon(srcPath + fileName, width, height, filePath + fileName);
			FileUtils.createFile(fileRealPath, FileUtils.getFileByte(newFile));
		}
	}
	
}
