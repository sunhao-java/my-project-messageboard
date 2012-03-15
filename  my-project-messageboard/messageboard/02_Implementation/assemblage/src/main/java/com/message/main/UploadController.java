package com.message.main;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;
import com.message.base.utils.SystemConfig;
import com.message.base.web.WebOutput;
import com.message.main.menu.exception.NoPermException;
import com.message.resource.ResourceType;

/**
 * 文件上传的controller
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-15 下午11:10:35
 */
public class UploadController extends ExtMultiActionController {
	private static WebOutput out = null;
	
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if(StringUtils.isNotEmpty(fileName)){
			fileName = MD5Utils.MD5Encode(fileName) + suffix;
		}
		
		String fileType = file.getContentType();
		if(!fileType.startsWith("image/")){
			throw new NoPermException("文件类型不正确!");
		}
		
		String path = SystemConfig.getProperty("message.upload.dir");
		Calendar calendar = Calendar.getInstance();
		String filePath = path + calendar.get(Calendar.YEAR) + "/" 
							+ (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/";
		File f = new File(filePath);
		
		if(!f.exists()){
			FileUtils.forceMkdir(f);
		}

		String fileRealPath = filePath + fileName;
		FileOutputStream fos = new FileOutputStream(fileRealPath);
		fos.write(file.getBytes());
		fos.flush();
		
		if(fos != null){
			fos.close();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		params.put("filePath", fileRealPath);
		out.toJson(params);
		return null;
	}
}
