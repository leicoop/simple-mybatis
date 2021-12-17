# Simply Mybatis

**Introduction**<br>
This is a mini-version of Mybatis which brings most core features of a real Mybatis .
It is a study demo, but it also imported design patterns and concepts shown in the original Mybatis.

**Background**<br>
As a developer who wants to gain insight into design patterns and OOP rules, I decided to implement a Mybatis framework widely used in J2EE applications. The basic structure and workflow come from Mybatis3 and I also draw lessons from an open-source project in which only partial functions are achieved. To make the project functional and meet the minimum requirements of CRUD, some codes were added and modifications were conducted.

**key features**

- implement the binding between the interface and the XML file
- implement the non-primitive type parameter processing
- implement the wrapper of the original transaction
- implement the processing of resultset
- implement the basic CRUD operations
* * *
**Project Structure（From my understanding）**

[![TFStkF.png](https://s4.ax1x.com/2021/12/17/TFStkF.png)](https://imgtu.com/i/TFStkF)

**Note:**<br>
The red texts illustrate the method invoked by the user<br>
The blue Classes are core elements in this framework

* * *

**Usage**

- [ ] To achieve CRUD demonstration, you can follow below steps
    
 
1. download the code
    
2.  create a table named account in a database named test in MySql
    

Note:
Three columns must be defined if you just want to take advantage of this demo without any code work
[![TidgT1.png](https://s4.ax1x.com/2021/12/17/TidgT1.png)](https://imgtu.com/i/TidgT1)

 open project in your device and step into TestClass where unit test methods have been created
  [![TidcwR.png](https://s4.ax1x.com/2021/12/17/TidcwR.png)](https://imgtu.com/i/TidcwR)
    
3.  invoke test methods in order
    
* * *
- [ ] To use this framework in java applications, you want to do the following four steps
    

1.  get the sqlsessionfactory instance<br>
    
    **SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");**<br>
    **Ps**:conf.properties is a property file containing database connection info and your mapper.xml location<br>
    
2.  get the session instance<br>
    **SqlSession session = factory.openSession();**<br>
    
3.  get the proxy instance of your interface<br>
    **AccountMapper mapper = session.getMapper(AccountMapper.class);**<br>
    
4.  invoke methods defined in the interface<br>
    **mapper.updateAccount(new Account(1,"mike",300.00));**<br>


* * *

- [ ] If you want to implement a transaction, you may need to practice some changes based on the previous 4-steps

1.  get the sqlsessionfactory instance(same as previous)<br>
    
2.  get the session instance with setting transaction on<br>
    **SqlSession session = factory.openSession(true);**<br>
    
3.  get the proxy instance of your interface(same as previous)<br>
    
4.  wrap codes with try-catch-finnally and pratice manual commit,rollback and close<br>
    **try{<br>
    mapper.updateAccount(new Account(1,"mike",100.00));<br>
    mapper.updateAccount(new Account(1,"mike",99.99));<br>
    session.commit();<br>
    }<br>
    catch (Exception e){<br>
    session.rollback();<br>
    }finally {<br>
    session.close();<br>
    }**<br>
    

* * *



**License**
MIT © Lei
