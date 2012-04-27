package com.message.main.upload.service.impl;

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
import com.message.base.utils.FileUtils;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;
import com.message.main.upload.dao.UploadDAO;
import com.message.main.upload.pojo.UploadFile;
import com.message.main.upload.service.GenericUploadService;
import com.message.resource.ResourceType;

/**
 * 上传文件的通用类的实现
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-18 上午11:39
 */
public class GenericUploadServiceImpl implements GenericUploadService {
    private static final Logger logger = LoggerFactory.getLogger(GenericUploadServiceImpl.class);

    private UploadDAO uploadDAO;

    public void setUploadDAO(UploadDAO uploadDAO) {
        this.uploadDAO = uploadDAO;
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

    public List listUploadFile(Long resourceId, Long uploadId, Integer resourceType) throws Exception {
        List<Long> pkIds = this.uploadDAO.listUploadFile(resourceId, uploadId, resourceType);

        if(pkIds == null || pkIds.size() < 0){
            logger.warn("can't get any files from database with params 'resourceId:{},uploadId:{},resourceType:{}'",
                    new Object[]{resourceId, uploadId, resourceType});

            return null;
        }

        List<UploadFile> files = new ArrayList<UploadFile>();

        for(Long pkId : pkIds){
            UploadFile file = this.uploadDAO.loadFile(pkId);

            logger.debug("the file is '{}'", file);

            files.add(file);
        }

        return files;
    }

    public boolean deleteFile(Long pkId) throws Exception {
        if(Long.valueOf(-1).equals(pkId)){
            logger.warn("the pkId is null!");
            return false;
        }
        UploadFile uploadFile = this.uploadDAO.loadFile(pkId);

        if(uploadFile == null){
            logger.warn("can not get any file by given pkId '{}'", pkId);
            return false;
        }

        //数据库中删除关系
        this.uploadDAO.deleteFile(uploadFile);

        //服务器硬盘上删除文件
        String filePath = uploadFile.getFilePath();
        boolean result = FileUtils.deleteFiles(filePath);

        return result;
        
    }

    public UploadFile loadFile(Long pkId) throws Exception {
        if(Long.valueOf(-1).equals(pkId)){
            logger.error("given pkId is null!");
            return null;
        }
        return this.uploadDAO.loadFile(pkId);
    }

    public void updateDownloadCount(Long pkId) throws Exception {
        UploadFile file = this.loadFile(pkId);
        if(file != null)
            file.setDownloadCount(file.getDownloadCount() + 1);

        this.uploadDAO.updateFile(file);
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

	public UploadFile genericUpload(MultipartFile file, Map params) throws Exception {
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

        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName(file.getOriginalFilename());
        uploadFile.setExtName(extName);
        uploadFile.setFilePath(fileRealPath);
        //文件大小的单位是字节
        uploadFile.setFileSize(FileUtils.getFileSize(fileRealPath) + "b");
        uploadFile.setResourceId((Long) params.get(ResourceType.MAP_KEY_RESOURCE_ID));
        uploadFile.setResourceType((Integer) params.get(ResourceType.MAP_KEY_RESOURCE_TYPE));
        uploadFile.setUploadId((Long) params.get(ResourceType.MAP_KEY_UPLOAD_ID));
        uploadFile.setUploadDate(new Date());
        uploadFile.setDownloadCount(Integer.valueOf(0));

        uploadFile = (UploadFile) this.uploadDAO.saveUpload(uploadFile);

        if(uploadFile.getPkId() == null){
            logger.error("save upload file failured!please check!");

            return null;
        }
        
        return uploadFile;
	}

	public List<UploadFile> genericUploads(MultipartRequest request, Map params) throws Exception {
		Iterator it = request.getFileNames();

		List<UploadFile> result = new ArrayList<UploadFile>();
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
