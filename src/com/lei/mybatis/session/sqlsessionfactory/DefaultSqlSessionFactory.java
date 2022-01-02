
package com.lei.mybatis.session.sqlsessionfactory;


import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.session.Configuration;
import com.lei.mybatis.session.sqlsession.DefaultSqlSession;
import com.lei.mybatis.session.sqlsession.SqlSession;
import com.lei.mybatis.utils.CommonUtis;
import com.lei.mybatis.utils.XmlUtil;


import java.io.File;
import java.net.URL;
import java.sql.SQLException;


/***
 * @author lei
 * @description class of sessionfactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;


    public DefaultSqlSessionFactory(Configuration configuration) {

        this.configuration = configuration;

        //call loadMappersInfo method to load xxxmapper.xml file to configuration entity
        loadMappersInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.", "/"));

    }

    /***
     * @author lei
     * @description method to generate instance of session with transaction setting
     * @param
     * @param enableTransaction
     * @return com.lei.mybatis.session.sqlsession.SqlSession
     */
    @Override
    public SqlSession openSession(boolean enableTransaction) {


        SqlSession sqlSession = null;
        try {
            sqlSession = new DefaultSqlSession(this.configuration, enableTransaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return sqlSession;
    }


    /***
     * @author lei
     * @description method to generate session instance without transaction setting
     * @param
     * @return com.lei.mybatis.session.sqlsession.SqlSession
     */
    @Override
    public SqlSession openSession() {

        return openSession(false);
    }


    /***
     * @author lei
     * @description method to find all xxxmapper.xml files
     * @param
     * @param dirName
     * @return void
     */
    private void loadMappersInfo(String dirName) {

        URL resources = DefaultSqlSessionFactory.class.getClassLoader().getResource(dirName);

        File mappersDir = new File(resources.getFile());

        if (mappersDir.isDirectory()) {


            File[] mappers = mappersDir.listFiles();

            //iterate all mapper file
            if (CommonUtis.isNotEmpty(mappers)) {
                for (File file : mappers) {

                    // recursion
                    if (file.isDirectory()) {
                        loadMappersInfo(dirName + "/" + file.getName());

                    } else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)) {

                        // parse xml file
                        XmlUtil.readMapperXml(file, this.configuration);
                    }

                }

            }
        }

    }

}
