package com.lei.mybatis.execution.executor.decorator;

import com.lei.mybatis.cache.Cache;
import com.lei.mybatis.cache.CacheKey;
import com.lei.mybatis.cache.TransactionalCacheManager;
import com.lei.mybatis.cache.decorator.TransactionalCache;
import com.lei.mybatis.execution.executor.Executor;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.transaction.Transaction;


import java.sql.SQLException;
import java.util.List;

/**
 * @author lei
 * @description decorator class of class SimpleExecutor which adds some transactional features
 *              if this class is instanced, second level cache is on
 */
public class CachingExecutor implements Executor {
    /**
     * Delegate will be SimpleExecutor
     */
    private final Executor delegate;

    /**
     * TransactionalCacheManager
     */
    private final TransactionalCacheManager tcm = new TransactionalCacheManager();


    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;

    }



    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        CacheKey key = createCacheKey(ms,parameter);
        return doQuery(ms,parameter,key);
    }

    /**
     * @author lei
     * @description query from second level cache first. If second cache result is null, then query from first level cache
     * @param
     * @param ms
     * @param parameter
     * @param key
     * @return java.util.List<E>
     */
    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, CacheKey key) {

        Cache cache = ms.getCache();

        if(null != cache ){

          // query from mapper cache
          List<E> list =  (List<E>)this.tcm.getObject(cache, key);
          if(list != null){
              System.out.println("======> query from second level cache ");
          }

          if(list == null){

              // query from sqlSession cache or db
              list = this.delegate.doQuery(ms,parameter,key);

              this.tcm.putObject(cache,key,list);
          }

          return list;

        }

        return delegate.doQuery(ms,parameter,key);
    }

    /**
     * @author lei
     * @description update method, once is called second level cache will be cleared.
     * @param
     * @param ms
     * @param parameter
     * @return void
     */
    @Override
    public void doUpdate(MappedStatement ms, Object parameter) {

          Cache cache = ms.getCache();
          if(null != cache){
              TransactionalCache tcmMappedCache =  this.tcm.getMappedCache(cache);
              tcmMappedCache.clear();
          }


        this.delegate.doUpdate(ms,parameter);
    }

    /**
     * @author lei
     * @description insert method, once is called second level cache will be cleared.
     * @param
     * @param
     * @param ms
     * @param parameter
     * @return void
     */
    @Override
    public void insert(MappedStatement ms, Object parameter) {
        Cache cache = ms.getCache();
        if(null != cache){
            TransactionalCache tcmMappedCache =  this.tcm.getMappedCache(cache);
            tcmMappedCache.clear();
        }
        this.delegate.insert(ms,parameter);
    }

    /**
     * @author lei
     * @description delete method, once is called second level cache will be cleared.
     * @param
     * @param
     * @param ms
     * @param args
     * @return void
     */
    @Override
    public void delete(MappedStatement ms, Object args) {
        Cache cache = ms.getCache();
        if(null != cache){
            TransactionalCache tcmMappedCache = this.tcm.getMappedCache(cache);
            tcmMappedCache.clear();
        }

        this.delegate.delete(ms,args);
    }

    @Override
    public Transaction getTransaction() {
        return delegate.getTransaction();
    }



    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameter) {
        return delegate.createCacheKey(ms,parameter);
    }

    @Override
    public void commit() throws SQLException {
    delegate.commit();
    tcm.commit();
    }

    @Override
    public void rollback() throws SQLException {
    delegate.rollback();
    tcm.rollback();
    }

    @Override
    public void close() {
        tcm.commit();
        delegate.close();
    }



}
