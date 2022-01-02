package com.lei.mybatis.transaction;

import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.session.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @author lei
 * @description class of transaction
 */
public class DefaultTransaction implements Transaction {

    //connection as filed to achieve manually transaction
    protected Connection connection;


    public DefaultTransaction(boolean enableTranscation) {
        initConnect();
        if (enableTranscation) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /***
     * @author lei
     * @description commit connection
     * @param
     * @return void
     */
    @Override
    public void commit() throws SQLException {


        if (connection != null && !connection.getAutoCommit()) {

            connection.commit();

        }

    }


    /***
     * @author lei
     * @description rollback connection
     * @param
     * @return void
     */
    @Override
    public void rollback() throws SQLException {


        if (connection != null && !connection.getAutoCommit()) {

            connection.rollback();

        }


    }


    /***
     * @author lei
     * @description close connection
     * @param
     * @return void
     */
    @Override
    public void close() throws SQLException {

        if (connection != null && !connection.getAutoCommit()) {

            connection.close();

        }

    }


    /***
     * @author lei
     * @description method to get instance of connection where singleton design pattern is applied
     * @param
     * @return java.sql.Connection
     */

    @Override
    public Connection getConnection() throws Exception {
        if (null != connection) {
            return connection;
        } else {
            throw new SQLException("fail to connect");
        }
    }


    /**
     * @param
     * @return void
     * @author lei
     * @description static method to build connection of database with params defined in config file
     */
    private void initConnect() {


        String driver = Configuration.getProperty(Constant.DB_DRIVER_CONF);
        String url = Configuration.getProperty(Constant.DB_URL_CONF);
        String username = Configuration.getProperty(Constant.DB_USERNAME_CONF);
        String password = Configuration.getProperty(Constant.db_PASSWORD);

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            connection = conn;

            System.out.println("======> connected database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
