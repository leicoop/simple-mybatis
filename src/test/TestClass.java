package test;

import com.lei.mybatis.session.SqlSessionFactoryBuilder;
import com.lei.mybatis.session.sqlsession.SqlSession;
import com.lei.mybatis.session.sqlsessionfactory.SqlSessionFactory;
import org.junit.Test;
import test.bean.Account;
import test.dao.AccountMapper;

/**
 * @author lei
 * @create 2021-12-14-4:55 PM
 */
public class TestClass {

    @Test
    public void testInsert(){
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");

        SqlSession session = factory.openSession();

        AccountMapper mapper = session.getMapper(AccountMapper.class);

        mapper.insertAccount(new Account("mike",200.00));



    }

    @Test
    public void testSelect(){

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");

        SqlSession session = factory.openSession();

        AccountMapper mapper = session.getMapper(AccountMapper.class);

        //assume the id of new account is 1
        Account account = mapper.getAccount(1);

        System.out.println(account);
    }


    @Test
    public void testUpdate(){

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");

        SqlSession session = factory.openSession();

        AccountMapper mapper = session.getMapper(AccountMapper.class);

        mapper.updateAccount(new Account(1,"mike",300.00));


    }
    @Test
    public void testDelete(){

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");

        SqlSession session = factory.openSession();

        AccountMapper mapper = session.getMapper(AccountMapper.class);

        mapper.deleteAccount(1);


    }
    @Test
    public void testTransaction(){

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");

        SqlSession session = factory.openSession(true);

        AccountMapper mapper = session.getMapper(AccountMapper.class);

        try{
            mapper.updateAccount(new Account(1,"mike",100.00));
            int i = 1 /0;
            mapper.updateAccount(new Account(1,"mike",99.99));
            session.commit();
        }
        catch (Exception e){
            session.rollback();
        }finally {
            session.close();
        }


    }



}
