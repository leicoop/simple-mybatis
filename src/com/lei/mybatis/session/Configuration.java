/**
 * 
 */
package com.lei.mybatis.session;


import com.lei.mybatis.binding.MapperRegistry;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.sqlsession.SqlSession;


import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/***
 * @author lei
 * @description core configuration class which contains everything of this framework,and it is so so important
 */
public class Configuration
{

    //property instance which can read the config file
    public static Properties PROPS = new Properties();

    //mapperRegistry instance contains mapping info of mapper class and proxyfactory
    protected final MapperRegistry mapperRegistry = new MapperRegistry();
    
    //mappedStatements instance contains mapping info of mapper class and sql statement
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();


    protected  Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Configuration(){

    }

    private static Configuration configuration;


    public static Configuration getInstance(){
        if(configuration == null){
            configuration = new Configuration();
        }

        return configuration;
    }




    public <T> void addMapper(Class<T> type)
    {
      this.mapperRegistry.addMapper(type);
    }
    

    public <T> T getMapper(Class<T> type, SqlSession sqlSession)
    {
      return this.mapperRegistry.getMapper(type, sqlSession);
    }
    

    public void addMappedStatement(String key, MappedStatement mappedStatement)
    {
        this.mappedStatements.put(key, mappedStatement);
    }


    public MappedStatement getMappedStatement(String id)
    {
        return this.mappedStatements.get(id);
    }


    public static String getProperty(String key)
    {
        return getProperty(key, "");
    }


    public static String getProperty(String key, String defaultValue)
    {

        return PROPS.containsKey(key) ? PROPS.getProperty(key) : defaultValue;
    }

}
