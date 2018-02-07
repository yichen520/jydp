package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 交易币种
 * @author fk
 *
 */
public class TransactionCurrencyDO {
    private int currencyId;  //币种Id
    private String currencyShortName;  //货币简称
    private String currencyName;  //货币名称
    private Timestamp addTime;  //添加时间

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyShortName() {
        return currencyShortName;
    }

    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
