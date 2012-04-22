package com.message.base.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.AbstractUrlMethodNameResolver;

/**
 * spring url mappings, url mapping class method name.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-22 下午03:49:49
 */
@SuppressWarnings("unchecked")
public class ExtPropertiesMethodNameResolver extends AbstractUrlMethodNameResolver {
	private static final Logger logger = LoggerFactory.getLogger(ExtPropertiesMethodNameResolver.class);

	private Properties mappings;
	private PathMatcher pathMatcher = new AntPathMatcher();
	private Object controller;
	private Class clazz;
	private List controllerName;


	/**
	 * Set explicit URL to method name mappings through a Properties object.
	 * @param mappings Properties with URL as key and method name as value
	 */
	public void setMappings(Properties mappings) {
		this.mappings = mappings;
	}

	/**
	 * Set the PathMatcher implementation to use for matching URL paths
	 * against registered URL patterns. Default is AntPathMatcher.
	 * @see org.springframework.util.AntPathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}

	public void afterPropertiesSet() {
		if (this.mappings == null || this.mappings.isEmpty()) {
			throw new IllegalArgumentException("'mappings' property is required");
		}
	}

	/**
	 * set the controller Class
	 * @param clazz		controller class
	 */
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	/**
	 * get controller object
	 * @return		controller object
	 */
	public Object getController() {
		return controller;
	}
	
	/**
	 * set controller name, it will be used at url mappings
	 * .e.g. /album/index.do --> index,this 'album' is here controllerName
	 * @return
	 */
	public List getControllerName() {
		return controllerName;
	}

	/**
	 * set controller name
	 * @param controllerName
	 */
	public void setControllerName(List controllerName) {
		this.controllerName = controllerName;
	}

	/**
	 * set controller object
	 * @param controller
	 */
	public void setController(Object controller) {
		this.controller = controller;
		this.setClazz(controller.getClass());
		
		if(this.controllerName == null){
			this.makeControllerName(controller);
		}
		
		Method[] methods = clazz.getMethods();
		Properties prop = new Properties();
		for(Method m : methods){
			Class returnType = m.getReturnType();
			if(ModelAndView.class.equals(returnType)){
				for(Object name : this.getControllerName()) {
					prop.put("/" + name.toString() + "/" + m.getName() + ".do", m.getName());
				}
			}
		}
		if(this.mappings == null)
			this.mappings = prop;
		else
			this.mappings.putAll(prop);
	}
	
	private void makeControllerName(Object controller){
		//controller name
		String className = controller.getClass().getSimpleName();
		logger.debug("the controller:'{}' class name is '{}'", controller, className);
		
		StringBuffer sb = new StringBuffer();
		if(className != null && className.length() > 0){
			for(int i = 0; i < className.length(); i++){
				String tmp = className.substring(i, i + 1);
				if(tmp.equals(tmp.toUpperCase())){
					//此字符是大写的
					sb.append("_").append(tmp);
				} else {
					sb.append(tmp);
				}
			}
			if(sb.toString().startsWith("_"))
				sb = new StringBuffer(sb.substring(1));
		}
		logger.debug("get controller name is '{}'", sb.toString());
		String tmp[] = sb.toString().split("_");
		List names = new ArrayList();
		
		for(int i = 0; i < tmp.length - 1; i++){
			names.add(tmp[i].toLowerCase());
			if(i != 0){
				String strTmp = "";
				for(int j = 0; j < i + 1; j++){
					if(j == 0)
						strTmp += tmp[j].toLowerCase();
					else
						strTmp += tmp[j];
				}
				names.add(strTmp);
			}
		}
		
		this.setControllerName(names);
	}
	
	protected String getHandlerMethodNameForUrlPath(String urlPath) {
		String methodName = this.mappings.getProperty(urlPath);
		if (methodName != null) {
			return methodName;
		}
		Enumeration propNames = this.mappings.propertyNames();
		while (propNames.hasMoreElements()) {
			String registeredPath = (String) propNames.nextElement();
			if (this.pathMatcher.match(registeredPath, urlPath)) {
				return (String) this.mappings.get(registeredPath);
			}
		}
		return null;
	}

}
