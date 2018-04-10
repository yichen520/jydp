package com.jydp.entity.DO.otc;

import java.sql.Timestamp;

/**
 * 场外交易成交记录
 * @author yk
 */
public class OtcTransactionUserDealDO {
    private String otcOrderNo; //记录号 业务类型（2）+日期（6）+随机位（10）
    private String otcPendingOrderNo; //挂单记录号
    private int userId; //用户Id
    private int typeId; //收款方式Id
    private String userAccount; //用户帐号
    private int dealType; //收支类型：1：买入，2：卖出
    private int currencyId; //币种Id
    private String currencyName; //货币名称
    private double transactionPrice; //成交单价
    private double pendingRatio; //挂单比例
    private double currencyNumber; //成交数量
    private double feeNumber; //成交费率
    private double currencyTotalPrice; //成交总价
    private Timestamp pendTime; //挂单时间
    private int dealStatus; //状态：1：待付款，2：已付款（待确认），3：已完成，4：用户取消，5：商家取消
    private String remark; //备注
    private Timestamp updateTime; //修改时间
    private Timestamp addTime; //添加时间

    /**
     * 获取 记录号
     * @return otcOrderNo
     */
    public String getOtcOrderNo() {
        return otcOrderNo;
    }

    /**
     * 设置 记录号
     * @param otcOrderNo 记录号
     */
    public void setOtcOrderNo(String otcOrderNo) {
        this.otcOrderNo = otcOrderNo;
    }

    /**
     * 获取 挂单记录号
     * @return otcPendingOrderNo
     */
    public String getOtcPendingOrderNo() {
        return otcPendingOrderNo;
    }

    /**
     * 设置 挂单记录号
     * @param otcPendingOrderNo 挂单记录号
     */
    public void setOtcPendingOrderNo(String otcPendingOrderNo) {
        this.otcPendingOrderNo = otcPendingOrderNo;
    }

    /**
     * 获取 用户Id
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置 用户Id
     * @param userId 用户Id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取 用户帐号
     * @return userAccount
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 获取 收款方式Id
     * @return userAccount
     */
    public int getTypeId() {
        return typeId;
    }


    /**
     * 设置 收款方式Id
     * @param typeId 用户Id
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * 设置 用户帐号
     * @param userAccount 用户帐号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 获取 收支类型1：买入，2：卖出，3：撤销
     * @return paymentType
     */
    public int getDealType() {
        return dealType;
    }

    /**
     * 设置 收支类型1：买入，2：卖出，3：撤销
     * @param dealType 收支类型
     */
    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    /**
     * 获取 币种Id
     * @return currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 设置 币种Id
     * @param currencyId 币种Id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 获取 货币名称
     * @return currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 设置 货币名称
     * @param currencyName 货币名称
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 获取 成交单价
     * @return transactionPrice
     */
    public double getTransactionPrice() {
        return transactionPrice;
    }

    /**
     * 设置 成交单价
     * @param transactionPrice 成交单价
     */
    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    /**
     * 获取 挂单比例
     * @return pendingRatio
     */
    public double getPendingRatio() {
        return pendingRatio;
    }

    /**
     * 设置 挂单比例
     * @param pendingRatio 挂单比例
     */
    public void setPendingRatio(double pendingRatio) {
        this.pendingRatio = pendingRatio;
    }

    /**
     * 获取 成交数量
     * @return currencyNumber
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 设置 成交数量
     * @param currencyNumber 成交数量
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 获取 成交费率
     * @return feeNumber
     */
    public double getFeeNumber() {
        return feeNumber;
    }

    /**
     * 设置 成交费率
     * @param feeNumber 成交费率
     */
    public void setFeeNumber(double feeNumber) {
        this.feeNumber = feeNumber;
    }

    /**
     * 获取 成交总价
     * @return currencyTotalPrice
     */
    public double getCurrencyTotalPrice() {
        return currencyTotalPrice;
    }

    /**
     * 设置 成交总价
     * @param currencyTotalPrice 成交总价
     */
    public void setCurrencyTotalPrice(double currencyTotalPrice) {
        this.currencyTotalPrice = currencyTotalPrice;
    }

    /**
     * 获取 挂单时间
     * @return pendTime
     */
    public Timestamp getPendTime() {
        return pendTime;
    }

    /**
     * 设置 挂单时间
     * @param pendTime 挂单时间
     */
    public void setPendTime(Timestamp pendTime) {
        this.pendTime = pendTime;
    }

    /**
     * 获取 状态
     * @return dealStatus
     */
    public int getDealStatus() {
        return dealStatus;
    }

    /**
     * 设置 状态
     * @param dealStatus 状态
     */
    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    /**
     * 获取 备注
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 修改时间
     * @return updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 添加时间
     * @return addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 设置 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
