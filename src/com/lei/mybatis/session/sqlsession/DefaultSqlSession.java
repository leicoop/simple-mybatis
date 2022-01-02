/**
 *
 */
package com.lei.mybatis.session.sqlsession;


import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.execution.executor.Executor;
import com.lei.mybatis.execution.executor.SimpleExecutor;
import com.lei.mybatis.execution.executor.decorator.CachingExecutor;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.Configuration;
import com.lei.mybatis.transaction.DefaultTransactionFactory;
import com.lei.mybatis.transaction.Transaction;
import com.lei.mybatis.utils.CommonUtis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/***
 * @author lei
 * @description class of DefaultSqlSession which is the core class of whole framework
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private Executor executor;

    private boolean enableTranscation;


    public boolean isEnableTranscation() {
        return enableTranscation;
    }

    /***
     * @author lei
     * @description default constructor, transaction will be automatically closed
     * @param
     * @param configuration
     * @return
     */
    public DefaultSqlSession(Configuration configuration) throws SQLException {

        this(configuration, false);

    }


    /***
     * @author lei
     * @description default constructor, and transaction can be enabled or disabled manually
     * @param
     * @param configuration
     * @param enableTranscation
     * @return
     */
    public DefaultSqlSession(Configuration configuration, boolean enableTranscation) throws SQLException {

        this.configuration = configuration;
        this.enableTranscation = enableTranscation;

        Executor delegate = new SimpleExecutor(configuration, this.enableTranscation);
        String isCached = Configuration.getProperty(Constant.CACHE);
        if ("true".equals(isCached)) {
            this.executor = new CachingExecutor(delegate);
        } else {
            this.executor = delegate;
        }


    }


    /***
     * @author lei
     * @description query method
     * @param
     * @param mappedStatement
     * @param parameter
     * @return T
     */
    @Override
    public <T> T selectOne(MappedStatement mappedStatement, Object parameter) {
        List<T> results = this.selectList(mappedStatement, parameter);

        return CommonUtis.isNotEmpty(results) ? results.get(0) : null;
    }


    /***
     * @author lei
     * @description query method
     * @param
     * @param mappedStatement
     * @param parameter
     * @return java.util.List<E>
     */
    @Override
    public <E> List<E> selectList(MappedStatement mappedStatement, Object parameter) {


        return this.executor.doQuery(mappedStatement, parameter);
    }


    /***
     * @author lei
     * @description update method
     * @param
     * @param mappedStatement
     * @param parameter
     * @return void
     */
    @Override
    public void update(MappedStatement mappedStatement, Object parameter) {

        this.executor.doUpdate(mappedStatement, parameter);
    }


    /***
     * @author lei
     * @description insert method
     * @param
     * @param mappedStatement
     * @param parameter
     * @return void
     */
    @Override
    public void insert(MappedStatement mappedStatement, Object parameter) {

        this.executor.insert(mappedStatement, parameter);


    }


    /***
     * @author lei
     * @description delete method
     * @param
     * @param mappedStatement
     * @param args
     * @return void
     */
    @Override
    public void delete(MappedStatement mappedStatement, Object[] args) {

        this.executor.delete(mappedStatement, args);
    }


    /***
     * @author lei
     * @description method to get instance of target class T
     * @param
     * @param
     * @param type
     * @return void
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.<T>getMapper(type, this);
    }


    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }


    /***
     * @author lei
     * @description method to commit operation manually
     * @param
     * @return void
     */
    @Override
    public void commit() {
        try {
            executor.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /***
     * @author lei
     * @description method to rollback manually
     * @param
     * @return void
     */
    @Override
    public void rollback() {
        try {
            executor.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /***
     * @author lei
     * @description method to return connection to pool manually
     * @param
     * @return void
     */
    @Override
    public void close() {

        executor.close();
    }


}
