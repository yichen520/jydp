package com.jydp.entity.DO.transfer;

import java.sql.Timestamp;

/**
 * JYDP用户币种转出记录
 *
 * @Author: wqq
 */
public class JydpUserCoinOutRecordDO {

    private String coinRecordNo; //币种转出记录号 业务类型（2）+日期（6）+随机位（7）
    private String sylRecordNo; //电子钱包操作记录号
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
     *
     * @return coin record no
     */
    public String getCoinRecordNo() {
        return coinRecordNo;
    }

    /**
     * 币种转出记录号 业务类型（2）+日期（6）+随机位（7）
     *
     * @param coinRecordNo the coin record no
     */
    public void setCoinRecordNo(String coinRecordNo) {
        this.coinRecordNo = coinRecordNo;
    }

    /**
     * 电子钱包操作记录号
     *
     * @return the syl record no
     */
    public String getSylRecordNo() {
        return sylRecordNo;
    }

    /**
     * 电子钱包操作记录号
     *
     * @param sylRecordNo the syl record no
     */
    public void setSylRecordNo(String sylRecordNo) {
        this.sylRecordNo = sylRecordNo;
    }

    /**
     * 币种Id
     *
     * @return currency id
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
     * 用户Id
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 用户帐号
     *
     * @return user account
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户帐号
     *
     * @param userAccount the user account
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 电子钱包帐号
     *
     * @return wallet account
     */
    public String getWalletAccount() {
        return walletAccount;
    }

    /**
     * 电子钱包帐号
     *
     * @param walletAccount the wallet account
     */
    public void setWalletAccount(String walletAccount) {
        this.walletAccount = walletAccount;
    }

    /**
     * 币种名称
     *
     * @return currency name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 币种名称
     *
     * @param currencyName the currency name
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 币种数量
     *
     * @return currency number
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 币种数量
     *
     * @param currencyNumber the currency number
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 审核状态 1：待审核，2：审核通过，3：审核拒绝 4:已撤回
     *
     * @return handle status
     */
    public int getHandleStatus() {
        return handleStatus;
    }

    /**
     * 审核状态 1：待审核，2：审核通过，3：审核拒绝 4:已撤回
     *
     * @param handleStatus the handle status
     */
    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 审核时间
     *
     * @return handle time
     */
    public Timestamp getHandleTime() {
        return handleTime;
    }

    /**
     * 审核时间
     *
     * @param handleTime the handle time
     */
    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

    /**
     * 转出状态 1:待转出, 2:转出中, 3:转出成功, 4:转出失败
     *
     * @return out status
     */
    public int getOutStatus() {
        return outStatus;
    }

    /**
     * 转出状态 1:待转出, 2:转出中, 3:转出成功, 4:转出失败
     *
     * @param outStatus the out status
     */
    public void setOutStatus(int outStatus) {
        this.outStatus = outStatus;
    }

    /**
     * 完成时间
     *
     * @return finish time
     */
    public Timestamp getFinishTime() {
        return finishTime;
    }

    /**
     * 完成时间
     *
     * @param finishTime the finish time
     */
    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 备注
     *
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 添加时间
     *
     * @return add time
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
