package com.lei.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/***
 * @author lei
 * @description class of transaction
 */
public class DefaultTransaction implements Transaction {

    //wrap connection as filed to achieve manually transaction
    protected Connection connection;

    public DefaultTransaction(Connection connection) {
        this.connection = connection;
    }



    /***
     * @author lei
     * @description commit method
     * @param
     * @return void
     */
    @Override
    public void commit() throws SQLException {



        if(connection != null && !connection.getAutoCommit()){

            connection.commit();

        }

    }


    /***
     * @author lei
     * @description rollback method
     * @param
     * @return void
     */
    @Override
    public void rollback() throws SQLException {


        if(connection != null && !connection.getAutoCommit()){

            connection.rollback();

        }


    }


    /***
     * @author lei
     * @description close method
     * @param
     * @return void
     */
    @Override
    public void close() throws SQLException {

        if(connection != null && !connection.getAutoCommit()){

            connection.close();

        }

    }
}
