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
The red texts illustrate the methods invoked by users<br>
The Classes marked as blue are core elements in this framework

* * *

**Usage**

- [ ] To achieve CRUD demonstration, you can follow below steps
    
 
1. download the code
    
2.  create a table named account in a database named test in MySql
    

Note:
Three columns must be defined if you just want to take advantage of this demo without any code work
[![TidgT1.png](https://s4.ax1x.com/2021/12/17/TidgT1.png)](https://imgtu.com/i/TidgT1)

3.  open project in your device and step into TestClass where unit test methods have been created
  [![TidcwR.png](https://s4.ax1x.com/2021/12/17/TidcwR.png)](https://imgtu.com/i/TidcwR)
    
4.  invoke test methods in order<br>
  [![TFCNtS.png](https://s4.ax1x.com/2021/12/17/TFCNtS.png)](https://imgtu.com/i/TFCNtS)<br>
  
  
  
    
* * *
- [ ] To use this framework in java applications, you want to do the following four steps(take update as a sample)
    

1.  get the sqlsessionfactory instance<br>
    
    **SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");**<br>
    **Ps**:conf.properties is a property file containing database connection info and your mapper.xml location<br>
    
2.  get the session instance<br>
    **SqlSession session = factory.openSession();**<br>
    
3.  get the proxy instance of your interface<br>
    **AccountMapper mapper = session.getMapper(AccountMapper.class);**<br>
    
4.  invoke methods defined in the interface<br>
    **mapper.updateAccount(new Account(1,"mike",300.00));**<br>
    
    
[![TFCIn1.png](https://s4.ax1x.com/2021/12/17/TFCIn1.png)](https://imgtu.com/i/TFCIn1)

* * *

- [ ] If you want to implement a transaction, you may need to practice some changes based on the previous 4-steps(take updates as a sample)

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
    
[![TFCo0x.png](https://s4.ax1x.com/2021/12/17/TFCo0x.png)](https://imgtu.com/i/TFCo0x)
* * *

**Reference && Acknowledgement**<br>
This project is based on an existing project demo submitted by @SeasonPanPan, and this demo gave me an insight and understanding of how a framework worked.<br>
As an entry-level SDE, only limited work has been done with referring to the design of MyBatis3 and demo as I mentioned. <br>
However, the knowledge and experience I gained are intangible.<br>

Refered project link:https://github.com/SeasonPanPan/minimybatis<br>
* * *

**License**
MIT © Lei
