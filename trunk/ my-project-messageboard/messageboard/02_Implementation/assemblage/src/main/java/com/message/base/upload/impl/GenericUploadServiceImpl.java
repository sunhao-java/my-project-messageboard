package com.message.base.upload.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.message.base.Constants;
import com.message.base.upload.GenericUploadService;
import com.message.base.utils.FileUtils;
import com.message.base.utils.MD5Utils;
import com.message.base.utils.StringUtils;

/**
 * 上传文件的通用类的实现
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-18 上午11:39
 */
public class GenericUploadServiceImpl implements GenericUploadService {
    private static final Logger logger = LoggerFactory.getLogger(GenericUploadServiceImpl.class);

    public List<String> uploads(MultipartRequest request) throws Exception {
        Iterator it = request.getFileNames();

        List<String> result = new ArrayList<String>();
        while(it.hasNext()){
            String key = (String) it.next();
            if(logger.isDebugEnabled()){
                logger.debug("the filaName key is '{}'!", key);
            }
            MultipartFile file = request.getFile(key);
            String fileName = this.upload(file);
            result.add(fileName);
        }

        return result;
    }

    public String upload(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        if(logger.isDebugEnabled()){
            logger.debug("the fileName is '{}'", fileName);
        }
        String suffix = fileName.substring(fileName.indexOf("."));
        if(StringUtils.isEmpty(suffix)){
            logger.error("can not find suffix from given fileName '{}'!", fileName);
        }

        fileName = MD5Utils.MD5Encode(fileName) + suffix;

        String path = Constants.DEFAULT_UPLOAD_PAYH;

        makeImageBySize(path, file, fileName);

        return fileName;
    }

    /**
     * 保存字节码文件到硬盘
     *
     * @param savePath      保存的路径
     * @param file          文件
     * @param fileName      文件名（算好的，MD5加密的文件名）
     * @throws Exception
     */
    private void makeImageBySize(String savePath, MultipartFile file, String fileName) throws Exception{
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
    }
}
