package com.jydp.entity.DTO;

import java.sql.Timestamp;

/**
 * 查询基准货币信息
 * @Author: wqq
 */
public class TransactionCurrencyDealPriceDTO {

    private int currencyId; //币种id
    private double highestPrice; //最高价
    private double lowestPrice; //最低价
    private double turnover; //成交量

    private Timestamp addTime; //添加时间

    /**
     * 币种id
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种id
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 最高价
     * @return the highestPrice
     */
    public double getHighestPrice() {
        return highestPrice;
    }

    /**
     * 最高价
     * @param highestPrice the highestPrice to set
     */
    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    /**
     * 最低价
     * @return the lowestPrice
     */
    public double getLowestPrice() {
        return lowestPrice;
    }

    /**
     * 最低价
     * @param lowestPrice the lowestPrice to set
     */
    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * 成交量
     * @return the turnover
     */
    public double getTurnover() {
        return turnover;
    }

    /**
     * 成交量
     * @param turnover the turnover to set
     */
    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    /**
     * 添加时间
     * @return the addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime the addTime to set
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
