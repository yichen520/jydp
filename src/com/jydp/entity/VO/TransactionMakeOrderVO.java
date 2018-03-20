package com.jydp.entity.VO;

import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;

/**
 * 后台做单
 * @author fk
 *
 */
public class TransactionMakeOrderVO extends TransactionMakeOrderDO{

    private double transactionPrice; //成交单价

    /**
     * 成交单价
     * @return the transactionPrice
     */
    public double getTransactionPrice() {
        return transactionPrice;
    }

    /**
     * 成交单价
     * @param transactionPrice the transactionPrice to set
     */
    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }
}
