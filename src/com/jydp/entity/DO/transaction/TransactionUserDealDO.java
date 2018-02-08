package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 成交记录
 * @author fk
 *
 */
public class TransactionUserDealDO {

    private String orderNo;  //记录号,业务类型（2）+日期（6）+随机位（10）
    private String pendingOrderNo;  //挂单记录号
    private int userId;  //用户Id
    private int paymentType;  //收支类型,1：买入，2：卖出
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double currencyNumber;  //成交数量
    private double currencyTotalPrice;  //成交总价
    private String remark;  //备注
    private Timestamp addTime;  //添加时间

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPendingOrderNo() {
        return pendingOrderNo;
    }

    public void setPendingOrderNo(String pendingOrderNo) {
        this.pendingOrderNo = pendingOrderNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public double getCurrencyTotalPrice() {
        return currencyTotalPrice;
    }

    public void setCurrencyTotalPrice(double currencyTotalPrice) {
        this.currencyTotalPrice = currencyTotalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
