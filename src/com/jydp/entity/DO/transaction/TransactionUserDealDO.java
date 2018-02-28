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
    private String userAccount;  //用户账号
    private int paymentType;  //收支类型,1：买入，2：卖出，3：撤销
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double transactionPrice; //成交单价
    private double currencyNumber;  //成交数量
    private double currencyTotalPrice;  //成交总价
    private String remark;  //备注
    private Timestamp pendTime;  //挂单时间
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
     * 挂单记录号
     * @return the pendingOrderNo
     */
    public String getPendingOrderNo() {
        return pendingOrderNo;
    }

    /**
     * 挂单记录号
     * @param pendingOrderNo the pendingOrderNo to set
     */
    public void setPendingOrderNo(String pendingOrderNo) {
        this.pendingOrderNo = pendingOrderNo;
    }

    /**
     * 用户Id
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 用户账号
     *
     * @return the user account
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户账号
     *
     * @param userAccount the user account
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 收支类型,1：买入，2：卖出，3：撤销
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 收支类型,1：买入，2：卖出，3：撤销
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
     * 货币名称
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     * @param currencyName the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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
     * 备注
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 挂单时间
     * @return the pendTime
     */
    public Timestamp getPendTime() {
        return pendTime;
    }

    /**
     * 挂单时间
     * @param pendTime the pendTime to set
     */
    public void setPendTime(Timestamp pendTime) {
        this.pendTime = pendTime;
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
