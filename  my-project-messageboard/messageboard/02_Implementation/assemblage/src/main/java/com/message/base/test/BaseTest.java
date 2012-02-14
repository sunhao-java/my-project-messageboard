package com.message.base.test;

import com.message.base.spring.ApplicationHelper;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-2-14 下午8:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        //"classpath:jdbc.properties",
        "classpath:spring-init.xml",
        "classpath:spring.xml",
        "classpath:base/jobs.xml",
        "classpath:base/mail.xml",
        "classpath:base/spring-hibernate.xml",
        "classpath:database/db-oracle.xml",
        "classpath:ioc/controller.xml",
        "classpath:ioc/dao.xml",
        "classpath:ioc/service.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseTest {
    private static final String ROOTPATH = "F:\\workspace\\workspace\\messageboard\\messageboard\\02_Implementation\\assemblage\\src\\main\\webapp";

    static {
        ApplicationHelper.getInstance().setRootPath(ROOTPATH);
    }
}
