package com.message.main.reply.pojo;

import com.message.main.user.pojo.User;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复实体.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-8-31 下午9:42
 */
public class Reply implements Serializable {
    private Long pkId;                          //主键
    private String title;                       //回复主题
    private String content;                     //回复内容
    private String ip;                          //回复地IP
    private Date replyTime;                     //回复时间
    private Long creatorId;                     //回复人(创建者)
    private Long resourceId;                    //资源ID
    private Integer resourceType;               //资源类型
    private Long deleteFlag;                    //删除标识位(0,1)

    //VO
    private User creator;                       //回复人(创建者)
    private String replyDate;                    //回复时间(字符串型)

    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Long getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Long deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }
}
