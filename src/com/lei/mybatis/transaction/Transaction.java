package com.lei.mybatis.transaction;

import java.sql.SQLException;

/**
 * @author lei
 * @create 2021-12-12-11:35 AM
 */
public interface Transaction {

    void commit() throws SQLException;

    /**
     * Rollback inner database connection.
     * @throws SQLException
     *           the SQL exception
     */
    void rollback() throws SQLException;

    /**
     * Close inner database connection.
     * @throws SQLException
     *           the SQL exception
     */
    void close() throws SQLException;
}
