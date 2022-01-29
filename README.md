# Simply Mybatis

**Introduction**<br>
This is a mini-version of Mybatis which brings most core features of a real Mybatis .
It is a study demo, but it also imported design patterns and concepts shown in the original Mybatis.

**Background**<br>
As a developer who wants to gain insight into design patterns and OOP rules, I decided to learn in practice by implementing a simple version of Mybatis which is widely used in J2EE applications. The basic structure and workflow come from Mybatis3 and I also draw lessons from open-source projects. 

**Key features**

- Implemented the binding between the interface and the XML file
- Implemented the non-primitive type parameter processing
- Implemented the transaction 
- Implemented the processing of resultset
- Implemented the basic CRUD operations
- Implemented the two-level cache: localchche and transactionalcache
* * *
**Project Structure**

````java
|-- src
    |-- config.properties 
    |-- com
    |   |-- lei
    |       |-- mybatis
    |           |-- binding 
    |           |   |-- MapperProxy.java
    |           |   |-- MapperProxyFactory.java
    |           |   |-- MapperRegistry.java 
    |           |-- cache
    |           |   |-- Cache.java
    |           |   |-- CacheKey.java
    |           |   |-- DefaultCache.java
    |           |   |-- TransactionalCacheManager.java
    |           |   |-- decorator
    |           |       |-- TransactionalCache.java
    |           |-- constants
    |           |   |-- Constant.java
    |           |-- execution
    |           |   |-- executor
    |           |   |   |-- Executor.java
    |           |   |   |-- SimpleExecutor.java
    |           |   |   |-- decorator
    |           |   |       |-- CachingExecutor.java
    |           |   |-- parameter
    |           |   |   |-- DefaultParameterHandler.java
    |           |   |   |-- ParameterHandler.java
    |           |   |-- resultset
    |           |   |   |-- DefaultResultSetHandler.java
    |           |   |   |-- ResultSetHandler.java
    |           |   |-- statement
    |           |       |-- SimpleStatementHandler.java
    |           |       |-- StatementHandler.java
    |           |-- mapping
    |           |   |-- MappedStatement.java
    |           |-- session
    |           |   |-- Configuration.java
    |           |   |-- SqlSessionFactoryBuilder.java
    |           |   |-- sqlsession
    |           |   |   |-- DefaultSqlSession.java
    |           |   |   |-- SqlSession.java
    |           |   |-- sqlsessionfactory
    |           |       |-- DefaultSqlSessionFactory.java
    |           |       |-- SqlSessionFactory.java
    |           |-- transaction
    |           |   |-- DefaultTransaction.java
    |           |   |-- DefaultTransactionFactory.java
    |           |   |-- Transaction.java
    |           |   |-- TransactionFactory.java
    |           |-- utils
    |               |-- CommonUtis.java
    |               |-- XmlUtil.java
    |-- test
        |-- TestClass.java 
        |-- bean
        |   |-- Account.java
        |-- dao
            |-- AccountMapper.java
            |-- UserMapper.xml
````

***



**Workflow（with first and second level cache）**

[![T76GFK.png](https://s4.ax1x.com/2022/01/03/T76GFK.png)](https://imgtu.com/i/T76GFK)

**Explaination:**<br>
The red lines illustrate the workflow of a query operation and numbers assigned showing sequence<br>
The classes highlighted as blue are core elements in this framework

* * *

**Demo:**<br>
For demonstration, you can follow below steps
    
1. clone the code
    
2.  create a table named account in a database named test in MySql databse
    

**Note**:
Three columns must be defined if you only want to implement this demo without any coding
[![TidgT1.png](https://s4.ax1x.com/2021/12/17/TidgT1.png)](https://imgtu.com/i/TidgT1)

3.  open project in your device and step into testclass where unit tests have been claimed
    
4.  run test methods in order<br>
 [![T766fS.png](https://s4.ax1x.com/2022/01/03/T766fS.png)](https://imgtu.com/i/T766fS)<br>
  
  
  
    
* * *

**Quick Start**

To achieve basic CRUD by this framework in java applications, you want to do the following five steps(take update as a sample)
    	
	
 ````java
         /**
         * Get the sqlsessionfactory instance
         */
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");

        /**
         * Get the session instance
         */

        SqlSession session = factory.openSession();

        /**
         * Get the proxy instance of interface
         */

        AccountMapper mapper = session.getMapper(AccountMapper.class);

        /**
         *  Invoke methods defined in the interface
         */

        mapper.updateAccount(new Account(1, "mike", 300.00));

        /**
         * Close connection
         */

        session.close();

````
	
* * *

**Use Transaction**

 If you want to add transaction, you may need to practice some changes based on the previous 5-steps(also take update as a sample)

    
````java
        /**
         * Get the sqlsessionfactory instance
         */
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");

        /**
         * Get the session instance and set transaction on
         */
        SqlSession session = factory.openSession(true);

        /**
         * Get the proxy instance of interface
         */
        AccountMapper mapper = session.getMapper(AccountMapper.class);

        /**
         * Wrap with try-catch-finally block
         */
        try {
            mapper.updateAccount(new Account(1, "mike", 100.00));
            /**
             * Imitate an exception 
             */
            int i = 1 / 0;
            mapper.updateAccount(new Account(1, "mike", 99.99));

            /**
             * Commit transaction
             */
            session.commit();
        } catch (Exception e) {
            /**
             * RollBack upon exception 
             */
            session.rollback();
        } finally {
            /**
             * close connection anyway
             */
            session.close();
        }
````
	
	
* * *
**Local Cache(First Level Cache)**

LocalCache is always on, like Mybatis3, no settings could be done to active or disable it. 
As long as practice same select operations in the same session, the result will be same.

```java
        /**
         * Get the sqlsessionfactory instance
         */
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");

        /**
         * Get the session instance
         */
        SqlSession session = factory.openSession();

        /**
         * Get the proxy instance A of interface
         */
        AccountMapper mapperA = session.getMapper(AccountMapper.class);

        /**
         * Get the proxy instance B of interface
         */
        AccountMapper mapperB = session.getMapper(AccountMapper.class);

        /**
         *  Query all account(s) in the first time
         */
        List<Account> firstQueryResult = mapperA.getAll();
        for (Account account : firstQueryResult) {
            System.out.println(account);
        }

        /**
         * Query all account(s) in the second time
         */
        List<Account> secondQueryResult = mapperB.getAll();
        for (Account account : secondQueryResult) {
            System.out.println(account);
        }

        /**
         * close session
         */
        session.close();
````




* * *

**Transactional Cache(Second Level Cache)**

To avtive this level cache, two steps should be done firstly.

Step 1:
In config.properties file, add key-value property: ''cache=true''.
````java
cache=true
````

Step 2:

In mapper.xml file, add property in the mapper lable:"cache = true"

````java
<mapper namespace="test.dao.AccountMapper" cache = "true">

````

````java
        /**
         * Get the sqlsessionfactory instance
         */
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("config.properties");

        /**
         * Get sessionA
         */
        SqlSession sessionA = factory.openSession();

        /**
         * Get sessionB
         */
        SqlSession sessionB = factory.openSession();

        /**
         * Get mapperA from sessionA
         */
        AccountMapper mapperA = sessionA.getMapper(AccountMapper.class);

        /**
         * Get mapperB from sessionB
         */
        AccountMapper mapperB = sessionB.getMapper(AccountMapper.class);

        /**
         * Query all account(s) in the first time 
         */
        List<Account> firstQueryResult = mapperA.getAll();
        for (Account account : firstQueryResult) {
            System.out.println(account);
        }

        /**
         * Close sessionA to make cache available
         */
        sessionA.close();

        /**
         * Query all account(s) in the second time
         */
        List<Account> secondQueryResult = mapperB.getAll();
        for (Account account : secondQueryResult) {
            System.out.println(account);
        }

        /**
         * Close sessionB
         */
        sessionB.close();
````

* * *

**Reference**<br>
This project is inspired by a project posted by @SeasonPanPan in Github, and the project gave me an insight and understanding of how a persistance layer framework worked.
As an entry-level SDE, some work has been done and some new features are added into this project with referring to the design of MyBatis3. 

Refered project link:
Mybatis3: https://github.com/mybatis/mybatis-3<br>
Refered project :https://github.com/SeasonPanPan/minimybatis<br>
* * *

**License**
MIT © Lei
