package com.message.main.reply.dao;

import com.message.base.pagination.PaginationSupport;
import com.message.main.reply.pojo.Reply;

import java.util.List;

/**
 * 回复DAO接口.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-8-31 下午9:39
 */
public interface ReplyDAO {

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
     * @param reply                 回复
     * @return
     * @throws Exception
     */
    Long saveReply(Reply reply) throws Exception;

    /**
     * 更新回复
     * 
     * @param reply                 回复
     * @throws Exception
     */
    void updateReply(Reply reply) throws Exception;

    /**
     * 批量删除回复
     *
     * @param replyIds              回复的ID以,隔开的字符串
     * @return
     * @throws Exception
     */
    boolean delete(List<Long> replyIds) throws Exception;

    /**
     * 根据主键获取回复对象
     *
     * @param pkId                  回复对象的ID
     * @return
     * @throws Exception
     */
    Reply getReply(Long pkId) throws Exception;
}
