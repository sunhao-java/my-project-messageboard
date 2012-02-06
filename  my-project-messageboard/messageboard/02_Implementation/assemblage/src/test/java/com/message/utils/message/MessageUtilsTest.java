package com.message.utils.message;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.message.base.i18n.MessageUtils;

import junit.framework.TestCase;
@SuppressWarnings("unused")
public class MessageUtilsTest extends TestCase {
	
	@Before
	public void before(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void testGetMessage(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
		
		System.out.println(MessageUtils.getMessage("user.confirm", "1111"));
	}
}
