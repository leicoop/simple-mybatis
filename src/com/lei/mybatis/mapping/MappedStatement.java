/**
 * 
 */
package com.lei.mybatis.mapping;

import com.lei.mybatis.constants.Constant;


/***
 * @author lei
 * @description class of mappedstatement
 */
public final class MappedStatement
{

    //namespace in xml
    private String namespace;

    //sqlId for statement
    private String sqlId;

    //sql sentence
    private String sql;


    //resultype
    private String resultType;

    //commandtype
    private Constant.SqlType sqlCommandType;

    //paramtype
    private String parameterType;

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * @return the namespace
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * @param namespace
     *            the namespace to set
     */
    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * @return the sqlId
     */
    public String getSqlId()
    {
        return sqlId;
    }

    /**
     * @param sqlId
     *            the sqlId to set
     */
    public void setSqlId(String sqlId)
    {
        this.sqlId = sqlId;
    }

    /**
     * @return the sql
     */
    public String getSql()
    {
        return sql;
    }

    /**
     * @param sql
     *            the sql to set
     */
    public void setSql(String sql)
    {
        this.sql = sql;
    }

    /**
     * @return the resultType
     */
    public String getResultType()
    {
        return resultType;
    }

    /**
     * @param resultType
     *            the resultType to set
     */
    public void setResultType(String resultType)
    {
        this.resultType = resultType;
    }

    
    /**
     * @return Returns the sqlCommandType.
     */
    public Constant.SqlType getSqlCommandType()
    {
        return sqlCommandType;
    }

    /**
     * @param sqlCommandType The sqlCommandType to set.
     */
    public void setSqlCommandType(Constant.SqlType sqlCommandType)
    {
        this.sqlCommandType = sqlCommandType;
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "MappedStatement{" +
                "namespace='" + namespace + '\'' +
                ", sqlId='" + sqlId + '\'' +
                ", sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                ", sqlCommandType=" + sqlCommandType +
                ", parameterType='" + parameterType + '\'' +
                '}';
    }
}
