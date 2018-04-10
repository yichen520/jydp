package com.jydp.entity.DO.otc;

import java.sql.Timestamp;

/**
 * 场外交易挂单记录
 * @author yk
 */
public class OtcTransactionPendOrderDO {
    private String otcPendingOrderNo; //挂单记录号 业务类型（2）+日期（6）+随机位（10）
    private int userId; //用户Id
    private String userAccount; //用户帐号
    private int orderType; //挂单类型 1：出售，2：回购
    private int currencyId; //币种Id
    private String currencyName; //货币名称
    private double pendingRatio; //挂单比例
    private double minNumber; //最小金额
    private double maxNumber; //最大金额
    private double pendingNumber; //挂单数量
    private double dealNumber; //已成交数量
    private double buyFee; //买入手续费
    private double restBalanceLock; //剩余冻结金额
    private String area; //地区(默认CN)
    private int pendingStatus; //挂单状态(默认1) 1：挂单中，-1删除
    private Timestamp endTime; //完成时间
    private String remark; //备注
    private Timestamp updateTime; //修改时间
    private Timestamp addTime; //添加时间

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
     * 设置 用户帐号
     * @param userAccount 用户帐号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 获取 挂单类型
     * @return orderType
     */
    public int getOrderType() {
        return orderType;
    }

    /**
     * 设置 挂单类型
     * @param orderType 挂单类型
     */
    public void setOrderType(int orderType) {
        this.orderType = orderType;
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
     * 获取 最小金额
     * @return minNumber
     */
    public double getMinNumber() {
        return minNumber;
    }

    /**
     * 设置 最小金额
     * @param minNumber 最小金额
     */
    public void setMinNumber(double minNumber) {
        this.minNumber = minNumber;
    }

    /**
     * 获取 最大金额
     * @return maxNumber
     */
    public double getMaxNumber() {
        return maxNumber;
    }

    /**
     * 设置 最大金额
     * @param maxNumber 最大金额
     */
    public void setMaxNumber(double maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * 获取 挂单数量
     * @return pendingNumber
     */
    public double getPendingNumber() {
        return pendingNumber;
    }

    /**
     * 设置 挂单数量
     * @param pendingNumber 挂单数量
     */
    public void setPendingNumber(double pendingNumber) {
        this.pendingNumber = pendingNumber;
    }

    /**
     * 获取 已成交数量
     * @return dealNumber
     */
    public double getDealNumber() {
        return dealNumber;
    }

    /**
     * 设置 已成交数量
     * @param dealNumber 已成交数量
     */
    public void setDealNumber(double dealNumber) {
        this.dealNumber = dealNumber;
    }

    /**
     * 获取 买入手续费
     * @return buyFee
     */
    public double getBuyFee() {
        return buyFee;
    }

    /**
     * 设置 买入手续费
     * @param buyFee 买入手续费
     */
    public void setBuyFee(double buyFee) {
        this.buyFee = buyFee;
    }

    /**
     * 获取 剩余冻结金额
     * @return restBalanceLock
     */
    public double getRestBalanceLock() {
        return restBalanceLock;
    }

    /**
     * 设置 剩余冻结金额
     * @param restBalanceLock 剩余冻结金额
     */
    public void setRestBalanceLock(double restBalanceLock) {
        this.restBalanceLock = restBalanceLock;
    }

    /**
     * 获取 地区
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置 地区
     * @param area 地区
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取 挂单状态
     * @return pendingStatus
     */
    public int getPendingStatus() {
        return pendingStatus;
    }

    /**
     * 设置 挂单状态
     * @param pendingStatus 挂单状态
     */
    public void setPendingStatus(int pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    /**
     * 获取 完成时间
     * @return endTime
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * 设置 完成时间
     * @param endTime 完成时间
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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
