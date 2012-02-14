package com.message;

import com.message.base.test.BaseTest;
import com.message.main.user.pojo.User;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-2-14 下午8:49
 */
public class VoteDAOTest extends BaseTest {
    @Autowired
    private VoteDAO voteDAO;

    @Test
    public void testGetAttendVote() throws Exception {
        List<Vote> votes = this.voteDAO.listMyAnswerVoteId(new User(41L));
        for(Vote v : votes){
            System.out.println(v.getQuestion());
        }
    }
}
