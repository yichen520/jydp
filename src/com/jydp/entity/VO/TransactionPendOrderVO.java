package com.jydp.entity.VO;

import com.jydp.entity.DO.transaction.TransactionPendOrderDO;

public class TransactionPendOrderVO extends TransactionPendOrderDO {

    private double countPrice; //挂单总价

    /**
     * 挂单总价
     * @return the countPrice
     */
    public double getCountPrice() {
        return countPrice;
    }

    /**
     * 挂单总价
     * @param countPrice the countPrice to set
     */
    public void setCountPrice(double countPrice) {
        this.countPrice = countPrice;
    }
}
