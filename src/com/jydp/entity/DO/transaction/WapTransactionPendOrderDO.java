package com.jydp.entity.DO.transaction;

/**
 * wap委托记录
 * @author cyfIverson
 * @create 2018-04-01
 */
public class WapTransactionPendOrderDO extends TransactionPendOrderDO {

    private double totalPrice; //总价

    /**
     * 委托记录总价
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * 委托记录总价
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
