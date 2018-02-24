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
    private int pendingStatus;  //挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
    private String remark;  //备注
    private Timestamp endTime;  //完成时间
    private Timestamp addTime;  //添加时间

    private String userAccount;  //用户账号

    /**
     * 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return the pendingOrderNo
     */
    public String getPendingOrderNo() {
        return pendingOrderNo;
    }

    /**
     * 记录号,业务类型（2）+日期（6）+随机位（10）
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
     * 挂单单价
     * @return the pendingPrice
     */
    public double getPendingPrice() {
        return pendingPrice;
    }

    /**
     * 挂单单价
     * @param pendingPrice the pendingPrice to set
     */
    public void setPendingPrice(double pendingPrice) {
        this.pendingPrice = pendingPrice;
    }

    /**
     * 挂单数量
     * @return the pendingNumber
     */
    public double getPendingNumber() {
        return pendingNumber;
    }

    /**
     * 挂单数量
     * @param pendingNumber the pendingNumber to set
     */
    public void setPendingNumber(double pendingNumber) {
        this.pendingNumber = pendingNumber;
    }

    /**
     * 成交数量
     * @return the dealNumber
     */
    public double getDealNumber() {
        return dealNumber;
    }

    /**
     * 成交数量
     * @param dealNumber the dealNumber to set
     */
    public void setDealNumber(double dealNumber) {
        this.dealNumber = dealNumber;
    }

    /**
     * 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @return the pendingStatus
     */
    public int getPendingStatus() {
        return pendingStatus;
    }

    /**
     * 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @param pendingStatus the pendingStatus to set
     */
    public void setPendingStatus(int pendingStatus) {
        this.pendingStatus = pendingStatus;
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
     * 完成时间
     * @return the endTime
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * 完成时间
     * @param endTime the endTime to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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

}
