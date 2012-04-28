package com.message.base.attachment.pojo;

import java.util.Date;

/**
 * 通用附件类
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-20 下午9:45
 */
public class Attachment {
    private Long pkId;                                  //主键
    private String fileName;                            //文件名
    private String extName;                             //文件扩展名
    private String filePath;                            //文件存储路径
    private Integer resourceType;                       //资源类型，定义在constant.java中
    private Long resourceId;                            //资源ID，关联的是哪个资源
    private String fileSize;                            //文件大小
    private Long uploadId;                              //上传者ID
    private Date uploadDate;                            //上传时间
    private Integer downloadCount;                      //下载次数
    
    public Attachment() {
		super();
	}

	public Attachment(Long pkId, String fileName, String extName,
			String filePath, Integer resourceType, Long resourceId,
			String fileSize, Long uploadId, Date uploadDate,
			Integer downloadCount) {
		super();
		this.pkId = pkId;
		this.fileName = fileName;
		this.extName = extName;
		this.filePath = filePath;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.fileSize = fileSize;
		this.uploadId = uploadId;
		this.uploadDate = uploadDate;
		this.downloadCount = downloadCount;
	}

	public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}
