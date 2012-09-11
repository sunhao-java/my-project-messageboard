package com.message.resource;

import org.junit.Test;

import com.message.BaseTest;
import com.message.base.properties.MessageUtils;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-9-11 上午10:31:36
 */
public class ResourceTest extends BaseTest {
	
	@Test
	public void testGetResource(){
		String mailTemplate = MessageUtils.getProperties("mailTemplate");
		System.out.println(mailTemplate);
	}
}
