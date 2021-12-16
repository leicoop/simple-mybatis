package com.lei.mybatis.transaction;

import java.sql.Connection;

/***
 * @author lei
 * @description class of transactionfactory
 */
public class DefaultTransactionFactory implements TransactionFactory{


    private final Connection connection;

    public DefaultTransactionFactory(Connection connection){
        this.connection =connection;
    }

    /***
     * @author lei
     * @description method to produce instance of transaction
     * @param
     * @return com.lei.mybatis.transaction.Transaction
     */
    public Transaction newInstance() {
        return new DefaultTransaction(this.connection);
    }
}
