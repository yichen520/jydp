package com.jydp.entity.DO.transfer;

import java.sql.Timestamp;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
public class JydpUserCoinOutRecordDO {

    private String coinRecordNo; //币种转出记录号 业务类型（2）+日期（6）+随机位（7）
    private int currencyId; //币种Id
    private int userId; //用户Id
    private String userAccount; //用户帐号
    private String walletAccount; //电子钱包帐号
    private String currencyName; //币种名称
    private double currencyNumber; //币种数量
    private int handleStatus; //审核状态 1：待审核，2：审核通过，3：审核拒绝 4:已撤回
    private Timestamp handleTime; //审核时间
    private int outStatus; //转出状态 1:待转出, 2:转出中, 3:转出成功, 4:转出失败
    private Timestamp finishTime; //完成时间
    private String remark; //备注
    private Timestamp addTime; //添加时间

    /**
     * 币种转出记录号 业务类型（2）+日期（6）+随机位（7）
     * @return
     */
    public String getCoinRecordNo() {
        return coinRecordNo;
    }

    /**
     * 币种转出记录号 业务类型（2）+日期（6）+随机位（7）
     * @param coinRecordNo
     */
    public void setCoinRecordNo(String coinRecordNo) {
        this.coinRecordNo = coinRecordNo;
    }

    /**
     * 币种Id
     * @return
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     * @param currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 用户Id
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 用户帐号
     * @return
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户帐号
     * @param userAccount
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 电子钱包帐号
     * @return
     */
    public String getWalletAccount() {
        return walletAccount;
    }

    /**
     * 电子钱包帐号
     * @param walletAccount
     */
    public void setWalletAccount(String walletAccount) {
        this.walletAccount = walletAccount;
    }

    /**
     * 币种名称
     * @return
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 币种名称
     * @param currencyName
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 币种数量
     * @return
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 币种数量
     * @param currencyNumber
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 审核状态 1：待审核，2：审核通过，3：审核拒绝 4:已撤回
     * @return
     */
    public int getHandleStatus() {
        return handleStatus;
    }

    /**
     * 审核状态 1：待审核，2：审核通过，3：审核拒绝 4:已撤回
     * @param handleStatus
     */
    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 审核时间
     * @return
     */
    public Timestamp getHandleTime() {
        return handleTime;
    }

    /**
     * 审核时间
     * @param handleTime
     */
    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

    /**
     * 转出状态 1:待转出, 2:转出中, 3:转出成功, 4:转出失败
     * @return
     */
    public int getOutStatus() {
        return outStatus;
    }

    /**
     * 转出状态 1:待转出, 2:转出中, 3:转出成功, 4:转出失败
     * @param outStatus
     */
    public void setOutStatus(int outStatus) {
        this.outStatus = outStatus;
    }

    /**
     * 完成时间
     * @return
     */
    public Timestamp getFinishTime() {
        return finishTime;
    }

    /**
     * 完成时间
     * @param finishTime
     */
    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 备注
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 添加时间
     * @return
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
