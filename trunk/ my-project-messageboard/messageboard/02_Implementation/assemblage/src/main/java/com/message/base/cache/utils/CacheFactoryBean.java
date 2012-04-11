package com.message.base.cache.utils;

import com.message.base.cache.Cache;
import com.message.base.cache.CacheManager;
import org.springframework.beans.factory.FactoryBean;

/**
 * create cache object factory
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-4-9 上午9:11
 */
@SuppressWarnings("rawtypes")
public class CacheFactoryBean implements FactoryBean {
    private CacheManager cacheManager;
    private String cacheName;

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Object getObject() throws Exception {
        return this.cacheManager.getCache(cacheName);
    }

    public Class getObjectType() {
        return Cache.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
