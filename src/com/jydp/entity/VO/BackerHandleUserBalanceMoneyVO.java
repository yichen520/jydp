package com.jydp.entity.VO;

import com.jydp.entity.DO.back.BackerHandleUserBalanceMoneyDO;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 */
public class BackerHandleUserBalanceMoneyVO extends BackerHandleUserBalanceMoneyDO {
    private String currencyName;  //货币名称

    /**
     * 货币名称
     *
     * @return the currency name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     *
     * @param currencyName the currency name
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
