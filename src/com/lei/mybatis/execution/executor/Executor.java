/**
 * 
 */
package com.lei.mybatis.execution.executor;


import com.lei.mybatis.cache.CacheKey;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;


public interface Executor
{



    <E> List<E> doQuery(MappedStatement ms, Object parameter);

    <E> List<E> doQuery(MappedStatement ms, Object parameter,CacheKey key);


    void doUpdate(MappedStatement ms, Object parameter);

    void insert(MappedStatement mappedStatement, Object parameter);

    void delete(MappedStatement mappedStatement, Object args);

    Transaction  getTransaction();

     CacheKey createCacheKey(MappedStatement ms, Object parameterObject);

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close();


}
