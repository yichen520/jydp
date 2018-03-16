package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * Description:(后台做单)交易统计记录表
 * Author: hht
 * Date: 2018-03-15 18:05
 */
public class TransactionStatisticsDO {
    private String orderNo;  //记录号，业务类型（2）+日期（6）+随机位（10）
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double transactionTotalNumber;  //成交总数量
    private double transactionTotalPrice;  //成交总价格
    private double currencyCoefficient;  //币种系数
    private Timestamp addTime;  //添加时间

    /**
     * 记录号，业务类型（2）+日期（6）+随机位（10）
     *
     * @return the order no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 记录号，业务类型（2）+日期（6）+随机位（10）
     *
     * @param orderNo the order no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 币种Id
     *
     * @return the currency id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     *
     * @param currencyId the currency id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

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

    /**
     * 成交总数量
     *
     * @return the transaction total number
     */
    public double getTransactionTotalNumber() {
        return transactionTotalNumber;
    }

    /**
     * 成交总数量
     *
     * @param transactionTotalNumber the transaction total number
     */
    public void setTransactionTotalNumber(double transactionTotalNumber) {
        this.transactionTotalNumber = transactionTotalNumber;
    }

    /**
     * 成交总价格
     *
     * @return the transaction total price
     */
    public double getTransactionTotalPrice() {
        return transactionTotalPrice;
    }

    /**
     * 成交总价格
     *
     * @param transactionTotalPrice the transaction total price
     */
    public void setTransactionTotalPrice(double transactionTotalPrice) {
        this.transactionTotalPrice = transactionTotalPrice;
    }

    /**
     * 币种系数
     *
     * @return the currency coefficient
     */
    public double getCurrencyCoefficient() {
        return currencyCoefficient;
    }

    /**
     * 币种系数
     *
     * @param currencyCoefficient the currency coefficient
     */
    public void setCurrencyCoefficient(double currencyCoefficient) {
        this.currencyCoefficient = currencyCoefficient;
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
