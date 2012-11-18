package com.message;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.message.base.spring.ApplicationHelper;
import com.message.base.test.GenericTest;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-9-11 上午10:33:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-init.xml",
        "classpath:dataSource-test.xml",
        "classpath:spring/dao.xml",
        "classpath:spring/service.xml",
        "classpath:database/db-oracle.xml",
        "classpath:base/cache.xml",
        "classpath:base/mail.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BaseTest extends GenericTest {

	static {
		ApplicationHelper.getInstance().setRootPath("D:\\Wiscom_Sets\\workspace_oa\\messageboard\\02_Implementation\\assemblage\\src\\main\\webapp");
	}

}
