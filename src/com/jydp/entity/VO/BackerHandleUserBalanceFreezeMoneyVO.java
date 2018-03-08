package com.jydp.entity.VO;

import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;

/**
 * 后台管理员增减用户冻结币记录
 * @author sy
 */
public class BackerHandleUserBalanceFreezeMoneyVO extends BackerHandleUserBalanceFreezeMoneyDO{
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
