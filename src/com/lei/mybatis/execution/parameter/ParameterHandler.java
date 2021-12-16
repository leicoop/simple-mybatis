/**
 * 
 */
package com.lei.mybatis.execution.parameter;


import java.sql.PreparedStatement;



public interface ParameterHandler
{


    void setParameters(PreparedStatement paramPreparedStatement);
}
