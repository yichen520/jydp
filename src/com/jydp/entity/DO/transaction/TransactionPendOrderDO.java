package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 挂单记录
 * @author fk
 *
 */
public class TransactionPendOrderDO {

    private String pendingOrderNo;  //记录号,业务类型（2）+日期（6）+随机位（10）
    private int userId;  //用户Id
    private int paymentType;  //收支类型,1：买入，2：卖出
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double pendingPrice;  //挂单单价
    private double pendingNumber;  //挂单数量
    private double dealNumber;  //成交数量
    private int pendingStatus;  //挂单状态
    private String remark;  //备注
    private Timestamp endTime;  //完成时间
    private Timestamp addTime;  //添加时间

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

    public double getPendingPrice() {
        return pendingPrice;
    }

    public void setPendingPrice(double pendingPrice) {
        this.pendingPrice = pendingPrice;
    }

    public double getPendingNumber() {
        return pendingNumber;
    }

    public void setPendingNumber(double pendingNumber) {
        this.pendingNumber = pendingNumber;
    }

    public double getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(double dealNumber) {
        this.dealNumber = dealNumber;
    }

    public int getPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(int pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
