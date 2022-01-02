/**
 *
 */
package com.lei.mybatis.session;


import com.lei.mybatis.session.sqlsessionfactory.DefaultSqlSessionFactory;
import com.lei.mybatis.session.sqlsessionfactory.SqlSessionFactory;


import java.io.IOException;
import java.io.InputStream;


/***
 * @author lei
 * @description class of SqlSessionFactoryBuilder
 */
public class SqlSessionFactoryBuilder {


    /***
     * @author lei
     * @description read input stream of conf.properties file into application
     * @param
     * @param fileName
     * @return com.lei.mybatis.session.sqlsessionfactory.SqlSessionFactory
     */
    public SqlSessionFactory build(String fileName) {

        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);

        return build(inputStream);
    }

    /***
     * @author lei
     * @description override method which read elements of conf.properties into configuration and produce instance of sqlsessionfactory
     * @param
     * @param inputStream
     * @return com.lei.mybatis.session.sqlsessionfactory.SqlSessionFactory
     */
    public SqlSessionFactory build(InputStream inputStream) {
        try {


            Configuration.PROPS.load(inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(Configuration.getInstance());
    }
}
