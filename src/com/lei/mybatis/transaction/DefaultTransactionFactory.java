package com.lei.mybatis.transaction;


/***
 * @author lei
 * @description class of transactionfactory
 */
public class DefaultTransactionFactory implements TransactionFactory {


    boolean enableTranscation;

    public DefaultTransactionFactory(boolean enableTranscation) {
        this.enableTranscation = enableTranscation;
    }

    /***
     * @author lei
     * @description method to produce instance of transaction
     * @param
     * @return com.lei.mybatis.transaction.Transaction
     */
    public Transaction newInstance() {
        return new DefaultTransaction(enableTranscation);
    }
}
