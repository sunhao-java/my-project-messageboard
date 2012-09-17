package com.message.main.reply.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.login.pojo.LoginUser;
import com.message.main.reply.pojo.Reply;

/**
 * reply模块service接口.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-8-31 下午9:43
 */
public interface ReplyService {

    /**
     * 根据资源ID和资源类型获取回复
     * 
     * @param resourceId            资源ID
     * @param resourceType          资源类型
     * @param start
     * @param num
     * @return
     * @throws Exception
     */
    PaginationSupport list(Long resourceId, Integer resourceType, int start, int num) throws Exception;

    /**
     * 保存新回复或者更新已有的回复
     * 
     * @param resourceId            资源ID
     * @param resourceType          资源类型
     * @param title                 回复标题
     * @param content               回复内容
     * @param loginUser             当前登录者
     * @param replyId               回复对象ID,新建保存:null,编辑保存:Long
     * @return
     * @throws Exception
     */
    Long saveOrUpdateReply(Long resourceId, Integer resourceType, String title, String content,
                              LoginUser loginUser, Long replyId) throws Exception;

    /**
     * 批量删除回复
     * 
     * @param replyIds              回复的ID以,隔开的字符串
     * @return
     * @throws Exception
     */
    boolean delete(String replyIds) throws Exception;

    /**
     * 根据主键获取回复对象
     *
     * @param pkId                  回复对象的ID
     * @return
     * @throws Exception
     */
    Reply getReply(Long pkId) throws Exception;

    /**
     * 根据资源ID和资源类型获取指定对象的回复条数
     *
     * @param resourceId            资源ID
     * @param resourceType          资源类型
     * @return
     * @throws Exception
     */
    int getResourceReplyNum(Long resourceId, Integer resourceType) throws Exception;
}