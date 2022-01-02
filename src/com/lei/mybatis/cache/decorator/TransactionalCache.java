package com.lei.mybatis.cache.decorator;

import com.lei.mybatis.cache.Cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lei
 * @description decorator class of defaultCache which adds some transactional features
 */
public class TransactionalCache implements Cache {


    private final Cache delegate;

    /**
     * A hashMap holding temporally cached data, data will be flushed into delegate upon commit or close
     */
    private final Map<Object, Object> entriesToAddOnCommit;


    public TransactionalCache(Cache delegate) {
        this.delegate = delegate;

        this.entriesToAddOnCommit = new HashMap<>();

    }

    @Override
    public String getId() {
        return delegate.getId();
    }


    /**
     * @param
     * @param key
     * @param value
     * @return void
     * @author lei
     * @description put query result into entriesToAddOnCommit instead of delegate cache
     */
    @Override
    public void putObject(Object key, Object value) {
        entriesToAddOnCommit.put(key, value);
    }

    @Override
    public Object getObject(Object key) {

        return delegate.getObject(key);

    }

    @Override
    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    /**
     * @param
     * @return void
     * @author lei
     * @description not only clear delegate cache but also entriesToAddOnCommit
     */
    @Override
    public void clear() {

        entriesToAddOnCommit.clear();
        delegate.clear();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    public void commit() {

        flushPendingEntries();
        entriesToAddOnCommit.clear();
    }

    public void rollback() {

        entriesToAddOnCommit.clear();
    }


    /**
     * @param
     * @return void
     * @author lei
     * @description Flush all items in entriesToAddOnCommit to delegate cache
     */
    private void flushPendingEntries() {
        for (Map.Entry<Object, Object> entry : entriesToAddOnCommit.entrySet()) {
            delegate.putObject(entry.getKey(), entry.getValue());
        }

    }


}
