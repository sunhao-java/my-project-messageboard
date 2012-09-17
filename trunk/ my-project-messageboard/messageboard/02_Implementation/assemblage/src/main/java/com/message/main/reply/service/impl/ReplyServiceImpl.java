package com.message.main.reply.service.impl;

import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.utils.DateUtils;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.login.pojo.LoginUser;
import com.message.main.reply.dao.ReplyDAO;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.main.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * reply模块service实现.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-8-31 下午9:44
 */
public class ReplyServiceImpl implements ReplyService {
    private static final Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);

    private ReplyDAO replyDAO;
    private UserService userService;

    public void setReplyDAO(ReplyDAO replyDAO) {
        this.replyDAO = replyDAO;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public PaginationSupport list(Long resourceId, Integer resourceType, int start, int num) throws Exception {
        if(resourceId == null || Long.valueOf(-1).equals(resourceId) || resourceType == null || Integer.valueOf(-1).equals(resourceType)){
            logger.debug("given paramters is not right!");
            return null;
        }

        PaginationSupport ps = this.replyDAO.list(resourceId, resourceType, start, num);
        List<Reply> replies = ps.getItems();
        for(int i = 0; i < replies.size(); i++){
            Reply reply = replies.get(i);
            if(reply.getPkId() != null) {
                reply = this.getReply(reply.getPkId());
                if(reply != null)
                    replies.set(i, reply);
            }
        }

        return ps;
    }

    public Long saveOrUpdateReply(Long resourceId, Integer resourceType, String title, String content,
                                     LoginUser loginUser, Long replyId) throws Exception {
        if(StringUtils.isEmpty(content) || resourceId == null || Long.valueOf(-1).equals(resourceId) || resourceType == null
                || Integer.valueOf(-1).equals(resourceType) || loginUser == null) {
            logger.debug("paramters may be null!");
            return Long.valueOf(-1);
        }

        if(replyId == null){
            //新建保存
            Reply reply = new Reply();
            reply.setTitle(title);
            reply.setContent(content);
            reply.setCreatorId(loginUser.getPkId());
            reply.setDeleteFlag(ResourceType.DELETE_NO);
            reply.setResourceId(resourceId);
            reply.setResourceType(resourceType);
            reply.setIp(loginUser.getLoginIP());
            reply.setReplyTime(new Date());

            return this.replyDAO.saveReply(reply);
        } else {
            //编辑
            Reply reply = this.getReply(replyId);
            reply.setTitle(title);
            reply.setContent(content);

            this.replyDAO.updateReply(reply);
            return reply.getPkId();
        }
    }

    public boolean delete(String replyIds) throws Exception {
        if(StringUtils.isEmpty(replyIds)){
            logger.debug("replyIds is null!");
            return false;
        }

        if(StringUtils.contains(replyIds, ",")) {
            String[] pkIds = replyIds.split(",");
            List<Long> ids = new ArrayList<Long>();
            for(String p : pkIds){
                ids.add(Long.valueOf(p));
            }

            return this.replyDAO.delete(ids);
        } else {
            return this.replyDAO.delete(Collections.singletonList(Long.valueOf(replyIds)));
        }
    }

    public Reply getReply(Long pkId) throws Exception {
        Reply reply = this.replyDAO.getReply(pkId);
        handleReply(reply);

        return reply;
    }

    public int getResourceReplyNum(Long resourceId, Integer resourceType) throws Exception {
        if(resourceId == null || Long.valueOf(-1).equals(resourceId) || resourceType == null || Integer.valueOf(-1).equals(resourceType)){
            logger.debug("resourceId or resourceId is null!");
            return 0;
        }

        return this.replyDAO.list(resourceId, resourceType, -1, -1).getItems().size();
    }

    private void handleReply(Reply reply) throws Exception {
        if(reply == null)
            return;

        if(reply.getCreatorId() != null)
            reply.setCreator(this.userService.getUserById(reply.getCreatorId()));

        if(reply.getReplyTime() != null)
            reply.setReplyDate(DateUtils.formatDate(reply.getReplyTime()));
    }
}
