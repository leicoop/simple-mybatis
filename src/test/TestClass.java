package test;

import com.lei.mybatis.session.SqlSessionFactoryBuilder;
import com.lei.mybatis.session.sqlsession.SqlSession;
import com.lei.mybatis.session.sqlsessionfactory.SqlSessionFactory;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import test.bean.Account;
import test.dao.AccountMapper;

import java.util.List;

/**
 * @author lei
 * @create 2021-12-14-4:55 PM
 */
public class TestClass {

    @Test
    public void testInsert() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession session = factory.openSession();
        AccountMapper mapper = session.getMapper(AccountMapper.class);

        mapper.insertAccount(new Account("mike", 200.00));
        session.close();

    }

    @Test
    public void testSelect() {

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession session = factory.openSession();
        AccountMapper mapper = session.getMapper(AccountMapper.class);
        //assume the id of new account is 1
        Account account = mapper.getAccount(1);


        session.close();
        System.out.println(account);


    }

    @Test
    public void testUpdate() {

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession session = factory.openSession();
        AccountMapper mapper = session.getMapper(AccountMapper.class);

        mapper.updateAccount(new Account(1, "mike", 300.00));
        session.close();


    }

    @Test
    public void testDelete() {

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession session = factory.openSession();
        AccountMapper mapper = session.getMapper(AccountMapper.class);

        mapper.deleteAccount(1);
        session.close();

    }

    @Test
    public void testTransaction() {

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession session = factory.openSession(true);
        AccountMapper mapper = session.getMapper(AccountMapper.class);


        try {
            mapper.updateAccount(new Account(1, "mike", 100.00));
            int i = 1 / 0;
            mapper.updateAccount(new Account(1, "mike", 99.99));
            session.commit();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }


    }


    @Test
    public void testLocalCache() {

        /**
         * This method tests the localCache integrated in every sqlSession, and it applies to all select operation in one session.
         * As long as practice same select operations in a same session, the result will be same.
         * LocalCache will expire when update, insert or delete operations have been conducted.
         * */


        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession session = factory.openSession();
        AccountMapper mapperA = session.getMapper(AccountMapper.class);
        AccountMapper mapperB = session.getMapper(AccountMapper.class);


        List<Account> firstQueryResult = mapperA.getAll();
        for (Account account : firstQueryResult) {
            System.out.println(account);
        }


        List<Account> secondQueryResult = mapperB.getAll();
        for (Account account : secondQueryResult) {
            System.out.println(account);
        }


        session.close();


    }


    @Test
    public void testSecondLevelCache() {
        /**
         * This method tests the second-level cache binding to namespace.
         * The cache is stored in each mappedStatement instance.
         * For each single mappedStatement in which sqlType is select, no matter which session conducted this statement, result will be same.
         * SecondLevel cache is designed to achieve transaction, which means only in following two cases, data will be stored into.
         *
         *
         * First case: when transaction is off, close session will flush data into cache.
         *
         * Second case:when transaction is on, commit session will flush data into cache.
         *
         *
         * */

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");

        SqlSession sessionA = factory.openSession();
        SqlSession sessionB = factory.openSession();


        AccountMapper mapperA = sessionA.getMapper(AccountMapper.class);
        AccountMapper mapperB = sessionB.getMapper(AccountMapper.class);


        List<Account> firstQueryResult = mapperA.getAll();
        for (Account account : firstQueryResult) {
            System.out.println(account);
        }

        sessionA.close();


        List<Account> secondQueryResult = mapperB.getAll();
        for (Account account : secondQueryResult) {
            System.out.println(account);
        }

        sessionB.close();
    }


}
