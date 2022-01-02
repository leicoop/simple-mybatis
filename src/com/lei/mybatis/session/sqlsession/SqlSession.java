/**
 *
 */
package com.lei.mybatis.session.sqlsession;


import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.Configuration;


import java.util.List;


public interface SqlSession {


    <T> T selectOne(MappedStatement mappedStatement, Object parameter);


    <E> List<E> selectList(MappedStatement mappedStatement, Object parameter);

    void update(MappedStatement mappedStatement, Object parameter);


    void insert(MappedStatement mappedStatement, Object parameter);


    <T> T getMapper(Class<T> paramClass);


    Configuration getConfiguration();


    void commit();


    void rollback();

    void close();


    void delete(MappedStatement mappedStatement, Object[] args);
}
