package com.jydp.entity.DTO;

import java.sql.Timestamp;

/**
 * 查询基准货币价格信息
 *
 * @author sy
 */
public class TransactionDealPriceDTO {
    private int currencyId; //币种id
    private double transactionPrice; //基准价格 （当前交易价，总价，最高价，最低价，成交量，成交额）
    private Timestamp addTime; //添加时间

    /**
     * 币种id
     *
     * @return the currency id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种id
     *
     * @param currencyId the currency id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 基准价格 （当前交易价，总价，最高价，最低价，成交量，成交额）
     *
     * @return the transaction price
     */
    public double getTransactionPrice() {
        return transactionPrice;
    }

    /**
     * 基准价格 （当前交易价，总价，最高价，最低价，成交量，成交额）
     *
     * @param transactionPrice the transaction price
     */
    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    /**
     * 添加时间
     *
     * @return the add time
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     *
     * @param addTime the add time
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
