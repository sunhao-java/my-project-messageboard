package com.message.base.attachment.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.message.base.Constants;
import com.message.base.attachment.dao.AttachmentDAO;
import com.message.base.attachment.pojo.Attachment;
import com.message.base.attachment.service.AttachmentService;
import com.message.base.utils.FileUtils;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;

/**
 * 上传附件通用类的实现
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-18 上午11:39
 */
public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private AttachmentDAO attachmentDAO;

	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}

	public List<String> uploads(MultipartRequest request, Map params) throws Exception {
        Iterator it = request.getFileNames();

        List<String> result = new ArrayList<String>();
        while(it.hasNext()){
            String key = (String) it.next();
            if(logger.isDebugEnabled()){
                logger.debug("the filaName key is '{}'!", key);
            }
            MultipartFile file = request.getFile(key);
            String fileName = this.upload(file, params);
            result.add(fileName);
        }

        return result;
    }

    public String upload(MultipartFile file, Map params) throws Exception {
    	this.genericUpload(file, params);
    	return file.getOriginalFilename();
    }

    public List listAttachment(Long resourceId, Long uploadId, Integer resourceType) throws Exception {
        List<Long> pkIds = this.attachmentDAO.listAttachment(resourceId, uploadId, resourceType);

        if(pkIds == null || pkIds.size() < 0){
            logger.warn("can't get any files from database with params 'resourceId:{},uploadId:{},resourceType:{}'",
                    new Object[]{resourceId, uploadId, resourceType});

            return null;
        }

        List<Attachment> attachments = new ArrayList<Attachment>();

        for(Long pkId : pkIds){
            Attachment attachment = this.attachmentDAO.loadAttachment(pkId);

            logger.debug("the file is '{}'", attachment);

            attachments.add(attachment);
        }

        return attachments;
    }

    public boolean deleteAttachment(Long pkId) throws Exception {
        if(Long.valueOf(-1).equals(pkId)){
            logger.warn("the pkId is null!");
            return false;
        }
        Attachment attachment = this.attachmentDAO.loadAttachment(pkId);

        if(attachment == null){
            logger.warn("can not get any file by given pkId '{}'", pkId);
            return false;
        }

        //数据库中删除关系
        this.attachmentDAO.deleteAttachment(attachment);

        //服务器硬盘上删除文件
        String path = attachment.getFilePath();
        boolean result = FileUtils.deleteFiles(path);

        return result;
        
    }

    public Attachment loadAttachment(Long pkId) throws Exception {
        if(Long.valueOf(-1).equals(pkId)){
            logger.error("given pkId is null!");
            return null;
        }
        return this.attachmentDAO.loadAttachment(pkId);
    }

    public void updateDownloadCount(Long pkId) throws Exception {
    	Attachment attachment = this.loadAttachment(pkId);
        if(attachment != null)
        	attachment.setDownloadCount(attachment.getDownloadCount() + 1);

        this.attachmentDAO.updateAttachment(attachment);
    }

    /**
     * 保存字节码文件到硬盘
     *
     * @param savePath      保存的路径
     * @param file          文件
     * @param fileName      文件名（算好的，MD5加密的文件名）
     * @return 文件存储路径
     * @throws Exception
     */
    private String makeImageBySize(String savePath, MultipartFile file, String fileName) throws Exception{
        String fileRealPath = StringUtils.EMPTY;
        byte[] fileByte = file.getBytes();

        Date now = new Date();
        String datePath = new SimpleDateFormat("/yyyy/MM/dd/").format(now);

        File uploadFile = new File(savePath + datePath);
        
        if(!uploadFile.exists()){
            org.apache.commons.io.FileUtils.forceMkdir(uploadFile);
        }

        fileRealPath = savePath + "/" + datePath + "/" + fileName;

        FileUtils.createFile(fileRealPath, fileByte);

        return fileRealPath;
    }

	public Attachment genericUpload(MultipartFile file, Map params) throws Exception {
		String fileName = file.getOriginalFilename();
        if(logger.isDebugEnabled()){
            logger.debug("the fileName is '{}'", fileName);
        }
        String extName = FileUtils.getExt(fileName);
        if(StringUtils.isEmpty(extName)){
            logger.error("can not find suffix from given fileName '{}'!", fileName);
        }

        fileName = MD5Utils.MD5Encode(fileName) + extName;

        String path = Constants.DEFAULT_UPLOAD_PAYH;

        String fileRealPath = makeImageBySize(path, file, fileName);

        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setExtName(extName);
        attachment.setFilePath(fileRealPath);
        //文件大小的单位是字节
        attachment.setFileSize(FileUtils.getFileSize(fileRealPath) + "b");
        attachment.setResourceId((Long) params.get(Constants.MAP_KEY_RESOURCE_ID));
        attachment.setResourceType((Integer) params.get(Constants.MAP_KEY_RESOURCE_TYPE));
        attachment.setUploadId((Long) params.get(Constants.MAP_KEY_UPLOAD_ID));
        attachment.setUploadDate(new Date());
        attachment.setDownloadCount(Integer.valueOf(0));

        attachment = (Attachment) this.attachmentDAO.saveAttachment(attachment);

        if(attachment.getPkId() == null){
            logger.error("save upload file failured!please check!");

            return null;
        }
        
        return attachment;
	}

	public List<Attachment> genericUploads(MultipartRequest request, Map params) throws Exception {
		Iterator it = request.getFileNames();

		List<Attachment> result = new ArrayList<Attachment>();
        while(it.hasNext()){
            String key = (String) it.next();
            if(logger.isDebugEnabled()){
                logger.debug("the filaName key is '{}'!", key);
            }
            MultipartFile file = request.getFile(key);
            result.add(this.genericUpload(file, params));
        }

        return result;
	}
}
