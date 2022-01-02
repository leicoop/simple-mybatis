package com.lei.mybatis.cache;


import com.lei.mybatis.cache.decorator.TransactionalCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 * @description core class of second level cache,when second level cache is on, transactionManager will take over cache management
 */
public class TransactionalCacheManager {

    /**
     * Collection of all TransactionCaches of current cachingExecutor
     */
    private final Map<Cache, TransactionalCache> transactionalCaches = new HashMap<>();

    public void clear(Cache cache) {
        getTransactionalCache(cache).clear();
    }


    public Object getObject(Cache cache, CacheKey key) {
        return getTransactionalCache(cache).getObject(key);
    }


    public void putObject(Cache cache, CacheKey key, Object value) {
        this.getTransactionalCache(cache).putObject(key, value);
    }


    public TransactionalCache getMappedCache(Cache cache) {
        return transactionalCaches.get(cache);
    }

    public void commit() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.commit();
        }
    }

    public void rollback() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.rollback();
        }
    }


    private TransactionalCache getTransactionalCache(Cache cache) {
        TransactionalCache transactionalCache = transactionalCaches.get(cache);
        if (transactionalCache == null) {

            transactionalCache = new TransactionalCache(cache);
            this.transactionalCaches.put(cache, transactionalCache);
        }

        return transactionalCache;


    }
}
