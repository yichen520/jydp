package com.jydp.entity.VO;

import com.jydp.entity.DO.user.UserCurrencyNumDO;

/**
 * 用户币数量
 *
 * @author sy
 */
public class UserCurrencyNumVO extends UserCurrencyNumDO {
    private double currencyNumberSum;  //用户币总金额

    /**
     * 用户币总金额
     *
     * @return the currency number sum
     */
    public double getCurrencyNumberSum() {
        return currencyNumberSum;
    }

    /**
     * 用户币总金额
     *
     * @param currencyNumberSum the currency number sum
     */
    public void setCurrencyNumberSum(double currencyNumberSum) {
        this.currencyNumberSum = currencyNumberSum;
    }


}
