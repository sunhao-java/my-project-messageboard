package com.message.base.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-2-14 下午8:36
 */
public class ApplicationHelper {
    private static final Logger log = Logger.getLogger(ApplicationHelper.class);
    private static ApplicationHelper instance = new ApplicationHelper();
    private Set apps;
    private String rootPath;

    public static ApplicationHelper getInstance() {
        return instance;
    }

    private ApplicationHelper() {
        apps = new HashSet();
    }

    public void addApplicationContext(ApplicationContext ac) {
        log.debug((new StringBuilder("add ApplicationContext:")).append(ac).toString());
        apps.add(ac);
        ac.getParent();
        if (ac instanceof WebApplicationContext)
            setRootPath(((WebApplicationContext) ac).getServletContext().getRealPath("/"));
    }

    public void removeAll() {
        apps.clear();
        apps = null;
    }

    public Object getBean(String name) {
        Object obj = null;
        for (Iterator it = apps.iterator(); it.hasNext(); ) {
            ApplicationContext ac = (ApplicationContext) it.next();
            try {
                obj = ac.getBean(name);
                if (obj != null)
                    return obj;
            } catch (BeansException e) {
                log.debug(e.getMessage());
            }
        }

        if (obj == null)
            throw new NoSuchBeanDefinitionException((new StringBuilder("No bean named '")).append(name).append("' is defined").toString());
        else
            return obj;
    }

    public String getRootPath() {
        if (rootPath != null)
            return rootPath;
        else
            return "./web/";
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
