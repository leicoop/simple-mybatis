/**
 * 
 */
package com.lei.mybatis.execution.executor;


import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.execution.parameter.DefaultParameterHandler;
import com.lei.mybatis.execution.parameter.ParameterHandler;
import com.lei.mybatis.execution.resultset.DefaultResultSetHandler;
import com.lei.mybatis.execution.resultset.ResultSetHandler;
import com.lei.mybatis.execution.statement.SimpleStatementHandler;
import com.lei.mybatis.execution.statement.StatementHandler;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.Configuration;


import java.sql.*;
import java.util.List;



/***
 * @author lei
 * @description class of executor
 */
public class SimpleExecutor implements Executor
{
    private static Connection connection;

    private Configuration conf;

    //static block to create connection once upon the load of this class
    static
    {
        initConnect();
    }





    public SimpleExecutor(Configuration configuration)
    {
        this.conf = configuration;
        this.conf.setConnection(connection);
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
    public <E> List<E> doQuery(MappedStatement ms, Object parameter)
    {

        try
        {
           //get connection
            Connection connection = getConnect();

            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(ms);
            
            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);
            
            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter,ms);
            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);
            
            //execute query and return result Table
            ResultSet resultSet = statementHandler.query(preparedStatement);

            
            //get instance  of resulthandler
            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(ms);

            //wrap info into entity
            return resultSetHandler.handleResultSets(resultSet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * @author lei
     * @description method to execute update
     * @param
     * @param ms
     * @param parameter
     * @return java.util.List<E>
     */
    @Override
    public void doUpdate(MappedStatement ms, Object parameter)
    {
        try
        {
            //get connection
            Connection connection = getConnect();



            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(ms);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter,ms);

            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);
            
            //execute update
            statementHandler.update(preparedStatement);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /***
     * @author lei
     * @description method to execute insert
     * @param
     * @param mappedStatement
     * @param parameter
     * @return java.util.List<E>
     */
    @Override
    public void insert(MappedStatement mappedStatement, Object parameter) {

        try
        {
            //get connection
            Connection connection = getConnect();


            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter,mappedStatement);

            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);

            //execute insert
            statementHandler.insert(preparedStatement);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





    }



    /***
     * @author lei
     * @description method to execute delete
     * @param
     * @param mappedStatement
     * @param args
     * @return void
     */
    @Override
    public void delete(MappedStatement mappedStatement, Object args) {





        try
        {
            //get connection
            Connection connection = getConnect();



            //get instance of statementHandler
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            //get preparedstatement from connection
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            //get instance of parameterHandler
            ParameterHandler parameterHandler = new DefaultParameterHandler(args,mappedStatement);

            //set params into preparedstatement
            parameterHandler.setParameters(preparedStatement);

            //execute insert
            statementHandler.delete(preparedStatement);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }









    }

   /***
    * @author lei
    * @description method to get instance of connection where singleton design pattern is applied
    * @param
    * @return java.sql.Connection
    */
    public Connection getConnect() throws SQLException 
    {
        if (null != connection)
        {
            return connection;
        }
        else 
        {
            throw new SQLException("failed to connect database");
        }
    }
    

    private static void initConnect()
    {

        String driver = Configuration.getProperty(Constant.DB_DRIVER_CONF);
        String url = Configuration.getProperty(Constant.DB_URL_CONF);
        String username = Configuration.getProperty(Constant.DB_USERNAME_CONF);
        String password = Configuration.getProperty(Constant.db_PASSWORD);

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

            System.out.println("connected successfully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
