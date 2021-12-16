**Simply Mybatis**

**Introduction**
This is a mini-version of Mybatis which brings most core features of a real Mybatis .
It is a study demo, but it also imported design patterns and concepts shown in the original Mybatis.

**Background**
As a developer who wants to gain insight into design patterns and OOP rules, I decided to implement a Mybatis framework widely used in J2EE applications. The basic structure and workflow come from Mybatis3 and I also draw lessons from an open-source project in which only partial functions are achieved. To make the project functional and meet the minimum requirements of CRUD, some codes were added and modifications were conducted.

**key features**

- implement the binding between the interface and the XML file
- implement the non-primitive type parameter processing
- implement the wrapper of the original transaction
- implement the processing of resultset
- implement the basic CRUD operations
* * *
**Project Structure**

![p1.drawio.png](../_resources/p1.drawio.png)

**Note:**
The ==red== texts illustrate the method called by the user to use this framework
The ==blue== Classes are core elements in this framework

* * *

**Usage**

- [ ] To achieve CRUD demonstration, you can follow below steps
    
 
1. download the code
    
2.  create a table named account in a database named test in MySql
    

Note:
Three columns must be defined if you just want to take advantage of this demo without any code work
![34ea6c30f368c2c4cadb5244905cfef0.png](../_resources/34ea6c30f368c2c4cadb5244905cfef0.png)

 open project in your device and step into TestClass where unit test methods have been created
    ![e4284747052b2d3fd5e570eb816b89ba.png](../_resources/e4284747052b2d3fd5e570eb816b89ba.png)
    
3.  invoke test methods in order
    
* * *
- [ ] To use this framework in java applications, you want to do the following four steps
    

1.  get the sqlsessionfactory instance
    
    **SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");**
    **Ps**:conf.properties is a property file containing database connection info and your mapper.xml location
    
2.  get the session instance
    **SqlSession session = factory.openSession();**
    
3.  get the proxy instance of your interface
    **AccountMapper mapper = session.getMapper(AccountMapper.class);**
    
4.  invoke methods defined in the interface
    **mapper.updateAccount(new Account(1,"mike",300.00));**


* * *

- [ ] If you want to implement a transaction, you may need to practice some changes based on the previous 4-steps

1.  get the sqlsessionfactory instance(same as previous)
    
2.  get the session instance with setting transaction on
    **SqlSession session = factory.openSession(true);**
    
3.  get the proxy instance of your interface(same as previous)
    
4.  wrap codes with try-catch-finnally and pratice manual commit,rollback and close
    **try{
    mapper.updateAccount(new Account(1,"mike",100.00));
    mapper.updateAccount(new Account(1,"mike",99.99));
    session.commit();
    }
    catch (Exception e){
    session.rollback();
    }finally {
    session.close();
    }**
    

* * *



**License**
MIT © Lei