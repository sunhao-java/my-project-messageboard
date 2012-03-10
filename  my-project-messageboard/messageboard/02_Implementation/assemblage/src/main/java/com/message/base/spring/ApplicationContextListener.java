package com.message.base.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 系统初始化时运行此类，将上下文注入到ApplicationHelper中，
 * 运行时，可以从ApplicationHelper中获取bean
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-8 下午10:16:10
 */
@SuppressWarnings("rawtypes")
public class ApplicationContextListener implements ApplicationListener {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent){
			ContextRefreshedEvent refreshedEvent = (ContextRefreshedEvent) event;
			
			logger.info("the applicationContext is " + refreshedEvent.getApplicationContext());
			
			ApplicationHelper.getInstance().addApplicationContext(refreshedEvent.getApplicationContext());
		}
	}

}
