package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * redis成交记录
 * @author hz
 *
 */
public class TransactionDealRedisDO {

    private String orderNo;  //记录号,业务类型（2）+日期（6）+随机位（10）
    private int paymentType;  //收支类型,1：买入，2：卖出
    private int currencyId;  //币种Id
    private double transactionPrice; //成交单价
    private double currencyNumber;  //成交数量
    private double currencyTotalPrice;  //成交总价
    private Timestamp addTime;  //添加时间

    /**
     * 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 收支类型,1：买入，2：卖出
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 收支类型,1：买入，2：卖出
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 币种Id
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

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
     * 成交总价
     * @return the currencyTotalPrice
     */
    public double getCurrencyTotalPrice() {
        return currencyTotalPrice;
    }

    /**
     * 成交总价
     * @param currencyTotalPrice the currencyTotalPrice to set
     */
    public void setCurrencyTotalPrice(double currencyTotalPrice) {
        this.currencyTotalPrice = currencyTotalPrice;
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
