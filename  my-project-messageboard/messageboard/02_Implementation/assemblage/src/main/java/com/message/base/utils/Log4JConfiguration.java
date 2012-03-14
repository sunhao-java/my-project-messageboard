package com.message.base.utils;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * log4j配置文件的载入
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-14 下午11:27:16
 */
public class Log4JConfiguration implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(Log4JConfiguration.class);
	
	private Resource location;

	public Resource getLocation() {
		return location;
	}

	public void setLocation(Resource location) {
		this.location = location;
	}

	public void afterPropertiesSet() throws Exception {
		if(this.getLocation() == null){
			logger.error("given location is not found!please check!");
			return;
		}
		String filePath = null;
		
		try {
			filePath = this.getLocation().getFile().getAbsolutePath();
		} catch (Exception e) {
			logger.error("the config file is not exist!");
		}
		
		if(filePath != null){
			logger.info("the log4j config file path is '{}'", filePath);
			PropertyConfigurator.configure(filePath);
		} else {
			logger.error("the config file is not exist!");
		}
	}

}
