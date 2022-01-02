/**
 *
 */
package com.lei.mybatis.execution.statement;


import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.utils.CommonUtis;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * @author lei
 * @description statementhandler class
 */
public class SimpleStatementHandler implements StatementHandler {
    //defines regex pattern
    private static Pattern param_pattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");

    private MappedStatement mappedStatement;


    public SimpleStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }


    /***
     * @author lei
     * @description method to pre-process sql sentence
     * @param
     * @param connection
     * @return java.sql.PreparedStatement
     */
    @Override
    public PreparedStatement prepare(Connection connection)
            throws SQLException {
        String originalSql = mappedStatement.getSql();

        if (CommonUtis.isNotEmpty(originalSql)) {


            // replace #{} to ?
            return connection.prepareStatement(parseSymbol(originalSql));
        } else {
            throw new SQLException("original sql is null.");
        }
    }


    /***
     * @author lei
     * @description execute query
     * @param
     * @param preparedStatement
     * @return java.sql.ResultSet
     */
    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    /***
     * @author lei
     * @description execute update
     * @param
     * @param preparedStatement
     * @return java.sql.ResultSet
     */
    @Override
    public void update(PreparedStatement preparedStatement)
            throws SQLException {
        preparedStatement.executeUpdate();
    }


    /***
     * @author lei
     * @description execute insert
     * @param
     * @param preparedStatement
     * @return java.sql.ResultSet
     */
    @Override
    public void insert(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
    }


    /***
     * @author lei
     * @description execute delete
     * @param
     * @param preparedStatement
     * @return java.sql.ResultSet
     */
    @Override
    public void delete(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
    }

    /***
     * @author lei
     * @description process sql string with replacing all params to ?
     * @param
     * @param source
     * @return java.lang.String
     */
    private static String parseSymbol(String source) {
        source = source.trim();
        Matcher matcher = param_pattern.matcher(source);
        return matcher.replaceAll("?");
    }

}
