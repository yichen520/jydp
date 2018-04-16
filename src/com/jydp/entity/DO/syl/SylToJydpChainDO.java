package com.jydp.entity.DO.syl;


import java.sql.Timestamp;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
public class SylToJydpChainDO {
    private String orderNo;  //订单号
    private String walletOrderNo;  //钱包订单号
    private int userId;  //用户id
    private String userAccount;  //用户账号
    private String walletUserAccount;//钱包账号
    private double currencyNumber;  //币种数量
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private String coinType;  //币种类型
    private Timestamp orderTime;  //订单时间
    private Timestamp finishTime;  //完成时间
    private String remark;  //备注	varchar
    private Timestamp addTime;  //添加时间

    /**
     * 订单号
     * @return
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单号
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 钱包订单号
     * @return
     */
    public String getWalletOrderNo() {
        return walletOrderNo;
    }

    /**
     * 钱包订单号
     * @param walletOrderNo
     */
    public void setWalletOrderNo(String walletOrderNo) {
        this.walletOrderNo = walletOrderNo;
    }

    /**
     * 钱包账号
     * @return
     */
    public String getWalletUserAccount() {
        return walletUserAccount;
    }

    /**
     * 钱包账号
     * @param walletUserAccount
     */
    public void setWalletUserAccount(String walletUserAccount) {
        this.walletUserAccount = walletUserAccount;
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
     * 用户id
     *
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId the userId
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
     * 币种类型
     *
     * @return the coin type
     */
    public String getCoinType() {
        return coinType;
    }

    /**
     * 币种类型
     *
     * @param coinType the coin type
     */
    public void setCoinType(String coinType) {
        this.coinType = coinType;
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
     * 货币名称
     * @return
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     * @param currencyName
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 订单时间
     * @return
     */
    public Timestamp getOrderTime() {
        return orderTime;
    }

    /**
     * 订单时间
     * @param orderTime
     */
    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
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
