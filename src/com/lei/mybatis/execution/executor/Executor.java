/**
 * 
 */
package com.lei.mybatis.execution.executor;


import com.lei.mybatis.mapping.MappedStatement;

import java.util.List;


public interface Executor
{


    <E> List<E> doQuery(MappedStatement ms, Object parameter);
    void doUpdate(MappedStatement ms, Object parameter);

    void insert(MappedStatement mappedStatement, Object parameter);

    void delete(MappedStatement mappedStatement, Object args);
}
