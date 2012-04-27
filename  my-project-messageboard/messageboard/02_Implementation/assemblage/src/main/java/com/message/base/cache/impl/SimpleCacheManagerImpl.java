package com.message.base.cache.impl;

import com.message.base.cache.Cache;
import com.message.base.cache.CacheManager;
import com.message.base.utils.StringUtils;

import java.util.*;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-4-9 上午8:22
 */
public class SimpleCacheManagerImpl implements CacheManager {
	private Map cacheMap = Collections.synchronizedMap(new LinkedHashMap(200));

    public List getCacheNames() {
        List names = new ArrayList();
        Iterator it = this.cacheMap.keySet().iterator();
        while(it.hasNext()){
            names.add(it.next());
        }
        return names;
    }

    public Cache getCache(String cacheName) {
        if(StringUtils.isEmpty(cacheName))
            return null;

        Cache cache = (Cache) this.cacheMap.get(cacheName);
        if(cache == null){
            cache = new SimpleCacheImpl();

            this.cacheMap.put(cacheName, cache);
        }
        return cache;
    }

    public void removeCache(String cacheName) {
        this.cacheMap.remove(cacheName);
    }

    public void flush() {
        this.cacheMap.clear();
    }
}
