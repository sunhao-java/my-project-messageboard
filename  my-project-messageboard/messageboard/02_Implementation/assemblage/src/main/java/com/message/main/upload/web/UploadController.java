package com.message.main.upload.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.Constants;
import com.message.base.attachment.pojo.Attachment;
import com.message.base.attachment.service.AttachmentService;
import com.message.base.exception.FileExistException;
import com.message.base.spring.SimpleController;
import com.message.base.utils.FileUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.home.web.GuestController;
import com.message.main.upload.service.UploadService;

/**
 * 文件上传的controller
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-15 下午11:10:35
 */
public class UploadController extends SimpleController {

	private static final Logger logger = LoggerFactory.getLogger(GuestController.class);
	
    /**
     * 上传头像的service
     */
	@Autowired
	private UploadService uploadService;
	@Autowired
    private AttachmentService attachmentService;

	private static WebOutput out = null;
	private static WebInput in = null;
	
	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
        Map<String, Object> params = new HashMap<String, Object>();
        boolean isHeadImage = in.getBoolean("headImage", Boolean.TRUE);
		Long userId = in.getLong("userId", Long.valueOf(-1));
        Long resourceId = in.getLong(Constants.MAP_KEY_RESOURCE_ID, Long.valueOf(-1));
        Integer resourceType = in.getInt(Constants.MAP_KEY_RESOURCE_TYPE, Integer.valueOf(-1));
        Long uploadId = in.getLong(Constants.MAP_KEY_UPLOAD_ID, Long.valueOf(-1));

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //是上传头像
        if(isHeadImage){
            Iterator iterator = multipartRequest.getFileNames();
            while(iterator.hasNext()){
                String fileName = (String) iterator.next();
                MultipartFile file = multipartRequest.getFile(fileName);
                this.uploadService.uploadHead(userId, file, in);
            }
        } else {
            Map uploadParams = new HashMap();
            uploadParams.put(Constants.MAP_KEY_RESOURCE_ID, resourceId);
            uploadParams.put(Constants.MAP_KEY_RESOURCE_TYPE, resourceType);
            uploadParams.put(Constants.MAP_KEY_UPLOAD_ID, uploadId);
            //上传文件
            List<String> results = this.attachmentService.uploads(multipartRequest, uploadParams);
            params.put("results", results);
        }


		params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
		return null;
	}

    /**
     * 展示上传的文件
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showUploads(HttpServletRequest request, HttpServletResponse response) throws Exception {
        out = new WebOutput(request, response);
		in = new WebInput(request);
        JSONObject params = new JSONObject();

        Long resourceId = in.getLong(Constants.MAP_KEY_RESOURCE_ID, Long.valueOf(-1));
        Integer resourceType = in.getInt(Constants.MAP_KEY_RESOURCE_TYPE, Integer.valueOf(-1));
        Long uploadId = in.getLong(Constants.MAP_KEY_UPLOAD_ID, Integer.valueOf(-1));

        List json = this.attachmentService.listAttachment(resourceId, uploadId, resourceType);

        if(json != null){
            logger.debug(json.toString());
            params.put("files", json);
        } else {
            logger.warn("can not find any files!");
        }

        params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
        return null;
    }

    /**
     * 删除文件
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        out = new WebOutput(request, response);
		in = new WebInput(request);
        JSONObject params = new JSONObject();

        Long pkId = in.getLong("fileId", Long.valueOf(-1));
        boolean result = this.attachmentService.deleteAttachment(pkId);

        params.put(ResourceType.AJAX_STATUS, result ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
        out.toJson(params);
        return null;
    }

    /**
     * 文件下载
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        in = new WebInput(request);
        out = new WebOutput(request, response);
        Long fileId = in.getLong("fileId", Long.valueOf(-1));

        //先从数据库中取出实体类
        Attachment attachment = this.attachmentService.loadAttachment(fileId);

        String path = attachment.getFilePath();

        File downFile = new File(path);

        if(!downFile.exists()){
            throw new FileExistException("文件不存在，读取文件失败！！");
        }

        this.attachmentService.updateDownloadCount(attachment.getPkId());

        Long fileSize = FileUtils.getFileSize(downFile);

        String fileName = attachment.getFileName();
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");

        out.getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        out.getResponse().setHeader("Content-Length", "" + fileSize);
        out.getResponse().setHeader("Content-Range", "bytes " + fileSize);

        FileInputStream fis = new FileInputStream(downFile);
        FileCopyUtils.copy(fis,  out.getResponse().getOutputStream());

        return null;
    }

}
