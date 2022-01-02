package com.lei.mybatis.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 * @description Default implemented class of interface Cache, achieving operating cache in memory
 */
public class DefaultCache implements Cache {

    /**
     * Id is the key to tell different cache instance in second level cache in a transactionalCacheManager
     */
    private final String id;

    /**
     * Actual data structure to store query results
     */
    private final Map<Object, Object> cache = new HashMap<>();

    public DefaultCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {

        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }


    /***
     * @author lei
     * @description override equals method by comparing two objects' name
     * @param
     * @param o
     * @return boolean
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Cache)) {
            return false;
        } else {
            Cache otherCache = (Cache) o;
            return this.getId().equals(otherCache.getId());
        }
    }

    /***
     * @author lei
     * @description override equals method: only when two caches have same name(String type, instead of address in memory), they are treated as same
     * @param
     * @return int
     */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}
