/**
 *
 */
package com.lei.mybatis.execution.executor;


import com.lei.mybatis.cache.CacheKey;
import com.lei.mybatis.cache.DefaultCache;
import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.execution.parameter.DefaultParameterHandler;
import com.lei.mybatis.execution.parameter.ParameterHandler;
import com.lei.mybatis.execution.resultset.DefaultResultSetHandler;
import com.lei.mybatis.execution.resultset.ResultSetHandler;
import com.lei.mybatis.execution.statement.SimpleStatementHandler;
import com.lei.mybatis.execution.statement.StatementHandler;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.Configuration;
import com.lei.mybatis.transaction.DefaultTransactionFactory;
import com.lei.mybatis.transaction.Transaction;


import java.sql.*;
import java.util.List;


/***
 * @author lei
 * @description class of executor
 */
public class SimpleExecutor implements Executor {

    protected Configuration conf;

    protected Transaction transaction;

    protected DefaultCache localCache;


    public SimpleExecutor(Configuration configuration, boolean enableTranscation) {

        this.transaction = new DefaultTransactionFactory(enableTranscation).newInstance();

        this.conf = configuration;

        this.localCache = new DefaultCache("1st level cache");
    }


    /***
     * @author lei
     * @description method to execute query
     * @param
     * @param ms
     * @param parameter
     * @return java.util.List<E>
     */
    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {

        CacheKey key = createCacheKey(ms, parameter);

        return doQuery(ms, parameter, key);

    }


    /**
     * @param
     * @param ms
     * @param parameter
     * @param key
     * @return java.util.List<E>
     * @author lei
     * @description query method: query from localCache firstly, and if no cache returned then query from database
     */
    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, CacheKey key) {


        List<E> cachedList = (List<E>) localCache.getObject(key);

        if (cachedList != null) {

            System.out.println("======> query from local cache");

            return cachedList;

        }

        cachedList = queryFromDatabase(ms, parameter);

        localCache.putObject(key, cachedList);

        return cachedList;


    }


    public <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter) {

        try {
            //get connection
            Connection connection = getTransaction().getConnection();

            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(ms);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter, ms);
            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);

            //execute query and return result Table
            ResultSet resultSet = statementHandler.query(preparedStatement);


            //get instance  of resulthandler
            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(ms);

            //wrap info into entity
            return resultSetHandler.handleResultSets(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }


    /***
     * @author lei
     * @description method to execute update, once called, localcache is cleared
     * @param
     * @param ms
     * @param parameter
     * @return java.util.List<E>
     */
    @Override
    public void doUpdate(MappedStatement ms, Object parameter) {
        try {
            //get connection
            Connection connection = getTransaction().getConnection();


            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(ms);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter, ms);

            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);

            //execute update
            statementHandler.update(preparedStatement);


            localCache.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * @author lei
     * @description method to execute insert,once called, localcache is cleared
     * @param
     * @param mappedStatement
     * @param parameter
     * @return java.util.List<E>
     */
    @Override
    public void insert(MappedStatement mappedStatement, Object parameter) {

        try {
            //get connection
            Connection connection = getTransaction().getConnection();


            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter, mappedStatement);

            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);

            //execute insert
            statementHandler.insert(preparedStatement);

            localCache.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /***
     * @author lei
     * @description method to execute delete, once called, localcache is cleared
     * @param
     * @param mappedStatement
     * @param args
     * @return void
     */
    @Override
    public void delete(MappedStatement mappedStatement, Object args) {


        try {
            //get connection
            Connection connection = getTransaction().getConnection();


            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(args, mappedStatement);

            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);

            //execute insert
            statementHandler.delete(preparedStatement);

            localCache.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Transaction getTransaction() {
        return this.transaction;
    }

    @Override
    public void commit() throws SQLException {

        localCache.clear();
        transaction.commit();


    }

    @Override
    public void rollback() throws SQLException {
        localCache.clear();
        transaction.rollback();
    }


    /**
     * @param
     * @param ms
     * @param parameterObject
     * @return com.lei.mybatis.cache.CacheKey
     * @author lei
     * @description create cacheKey by inputting two parameters:  MappedStatement and parameterObject; to make sure generated key is unique
     */
    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject) {

        CacheKey cacheKey = new CacheKey();
        cacheKey.update(ms.getSqlId());
        cacheKey.update(parameterObject);

        return cacheKey;


    }


    @Override
    public void close() {
        try {
            transaction.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            transaction = null;
            localCache = null;
        }
    }


}
