package com.message.base.cache;

import java.util.List;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-4-9 上午6:47
 */
public interface CacheManager {

    /**
     * get all cache names
     * 
     * @return
     */
    List getCacheNames();

    /**
     * get cache with cache name
     * 
     * @param cacheName
     * @return
     */
    Cache getCache(String cacheName);

    /**
     * remove cache with given cache name
     * 
     * @param cacheName
     */
    void removeCache(String cacheName);

    /**
     * flush the cache
     */
    void flush();

}
