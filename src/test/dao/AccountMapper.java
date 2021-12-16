/**
 * 
 */
package test.dao;


import test.bean.Account;

import java.util.List;


/**
 * UserMapper.java
 * 
 * @author PLF
 * @date 2019年3月6日
 */
public interface AccountMapper
{

    /**
     * 获取单个user
     * 
     * @param id
     * @return 
     * @see 
     */
    Account getAccount(int id);
    
    /**
     * 获取所有用户
     * 
     * @return 
     * @see
     */
    List<Account> getAll();
    

    void updateAccount(Account account);


    void insertAccount(Account account);


    void deleteAccount(int id);
}
