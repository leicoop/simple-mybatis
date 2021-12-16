/**
 * 
 */
package com.lei.mybatis.session.sqlsessionfactory;

import com.lei.mybatis.session.sqlsession.SqlSession;


public interface SqlSessionFactory
{


    SqlSession openSession(boolean enableTransaction);
    SqlSession openSession();

}
