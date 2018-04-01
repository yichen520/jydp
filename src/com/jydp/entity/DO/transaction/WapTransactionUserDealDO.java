package com.jydp.entity.DO.transaction;

/**
 * wap委托记录实体
 * @author cyfIverson
 * @create 2018-04-01
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
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
