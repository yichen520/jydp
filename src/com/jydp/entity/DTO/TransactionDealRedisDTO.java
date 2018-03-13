package com.jydp.entity.DTO;

import java.sql.Timestamp;

/**
 * redis成交记录（用于k线图数据拿取）
 * @Author hz
 */
public class TransactionDealRedisDTO {
    private double transactionPrice; //成交单价
    private double currencyNumber;  //成交数量
    private Timestamp addTime;  //添加时间

    /**
     * 成交数量
     * @return the currencyNumber
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 成交数量
     * @param currencyNumber the currencyNumber to set
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
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

    /**
     * 获取成交单价
     * @return transactionPrice 成交单价
     */
    public double getTransactionPrice() {
        return transactionPrice;
    }

    /**
     * 设置成交单价
     * @param transactionPrice 成交单价
     */
    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }
}
