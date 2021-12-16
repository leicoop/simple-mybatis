/**
 * 
 */
package com.lei.mybatis.session.sqlsession;


import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.Configuration;


import java.util.List;



public interface SqlSession
{

    /**
     * 查询带条记录
     * 
     * @param statement
     * @param parameter
     * @return 
     * @see 
     */
    <T> T selectOne(MappedStatement  mappedStatement, Object parameter);

    /**
     * 查询多条记录
     * 
     * @param statement
     * @param parameter
     * @return 
     * @see 
     */
    <E> List<E> selectList(MappedStatement  mappedStatement, Object parameter);

    /**
     * update
     * 
     * @param statement
     * @param parameter 
     */
    void update(MappedStatement  mappedStatement, Object parameter);
    
    
    /**
     * insert
     * 
     * @param statementId
     * @param parameter 
     */
    void insert(MappedStatement  mappedStatement, Object parameter);
    
    /**
     * 获取mapper
     * 
     * @param paramClass
     * @return 
     * @see 
     */
    <T> T getMapper(Class<T> paramClass);

    /**
     * 获取配置类
     * 
     * @return 
     * @see 
     */
    Configuration getConfiguration();


    void commit() ;


    void rollback() ;

    void close() ;


    void delete(MappedStatement  mappedStatement, Object[] args);
}
