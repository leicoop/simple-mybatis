/**
 *
 */
package com.lei.mybatis.execution.statement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface StatementHandler {


    PreparedStatement prepare(Connection paramConnection) throws SQLException;

    ResultSet query(PreparedStatement preparedStatement) throws SQLException;

    void update(PreparedStatement preparedStatement) throws SQLException;

    void insert(PreparedStatement preparedStatement) throws SQLException;

    void delete(PreparedStatement preparedStatement) throws SQLException;
}
