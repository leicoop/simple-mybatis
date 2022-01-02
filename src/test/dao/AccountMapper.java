/**
 * 
 */
package test.dao;


import test.bean.Account;

import java.util.List;



public interface AccountMapper
{


    Account getAccount(int id);
    

    List<Account> getAll();
    

    void updateAccount(Account account);


    void insertAccount(Account account);


    void deleteAccount(int id);
}
