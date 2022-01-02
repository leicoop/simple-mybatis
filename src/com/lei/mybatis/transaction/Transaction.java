package com.lei.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lei
 * @create 2021-12-12-11:35 AM
 */
public interface Transaction {

    void commit() throws SQLException;

    void rollback() throws SQLException;


    void close() throws SQLException;


    Connection getConnection() throws Exception;


}
