package com.jydp.entity.DO.transaction;

/**
 * @author cyfIverson
 * @description
 * @create 2018-04-01-18:23
 */

public class WapTransactionUserDealDO extends TransactionPendOrderDO {

    private String totalPrice; //总价

    /**
     * 委托记录总价
     * @return the totalPrice
     */
    public String getTotalPrice() {
        return totalPrice;
    }

    /**
     * 委托记录总价
     * @param totalPrice the pendingOrderNo to set
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
