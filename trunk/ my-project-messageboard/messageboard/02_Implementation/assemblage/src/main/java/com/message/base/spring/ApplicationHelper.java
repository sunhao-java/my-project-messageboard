package com.message.base.spring;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * applicationContext的辅助类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-8 下午09:56:58
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApplicationHelper {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);
	
	private static ApplicationHelper instance = new ApplicationHelper();
	
	private String rootPath;
	
	private Set apps = new HashSet();
	
	//构造器私有，不可在外部进行初始化实例
	private ApplicationHelper(){
		
	}
	
	public Object getBean(String name){
		Object result = null;
		Iterator it = apps.iterator();
		
		while(it.hasNext()){
			ApplicationContext app = (ApplicationContext) it.next();
			try {
				result = app.getBean(name);
				if(result != null)
					return result;
			} catch (BeansException e) {
				e.printStackTrace();
				logger.error("there is no bean named '{}'", name);
			}
		}
		
		if(result == null)
			throw new NoSuchBeanDefinitionException("there is no bean named '" + name + "'");
		
		return result;
	}
	
	public void removeAll(){
		apps.clear();
		apps = null;
	}
	
	public void addApplicationContext(ApplicationContext context){
		apps.add(context);
		
		if(context.getParent() != null){
			//递归，将context的所有上一级放入apps中
			this.addApplicationContext(context.getParent());
		}
		
		if(context instanceof WebApplicationContext){
			this.setRootPath(((WebApplicationContext)context).getServletContext().getRealPath("/"));
		}
	}

	public String getRootPath() {
		if(rootPath != null)
			return rootPath;
		return "./webapp/";
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public static ApplicationHelper getInstance() {
		return instance;
	}

}
