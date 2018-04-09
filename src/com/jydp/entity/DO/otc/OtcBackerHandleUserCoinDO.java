package com.jydp.entity.DO.otc;

import java.sql.Timestamp;

/**
 * 后台管理员增减经销商用户可用币记录
 * @author yk
 */
public class OtcBackerHandleUserCoinDO {
    private long id; //记录Id
    private int userId; //用户Id
    private String userAccount; //用户账号
    private int typeHandle; //操作类型
    private int currencyId; //币种Id
    private double userBalance; //可用币
    private double discount; //折扣
    private String remark; //备注
    private String ipAddress; //操作时的ip地址
    private int backerId; //后台管理员Id
    private String backerAccount; //后台管理员帐号
    private Timestamp addTime; //记录时间

    /**
     * 获取 记录Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * 设置 记录Id
     * @param id 记录Id
     */
    public void setId(long id) {
        this.id = id;
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
     * 获取 用户账号
     * @return userAccount
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置 用户账号
     * @param userAccount 用户账号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 获取 操作类型
     * @return typeHandle
     */
    public int getTypeHandle() {
        return typeHandle;
    }

    /**
     * 设置 操作类型
     * @param typeHandle 操作类型
     */
    public void setTypeHandle(int typeHandle) {
        this.typeHandle = typeHandle;
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
     * 获取 可用币
     * @return userBalance
     */
    public double getUserBalance() {
        return userBalance;
    }

    /**
     * 设置 可用币
     * @param userBalance 可用币
     */
    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    /**
     * 获取 折扣
     * @return discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * 设置 折扣
     * @param discount 折扣
     */
    public void setDiscount(double discount) {
        this.discount = discount;
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
     * 获取 操作时的ip地址
     * @return ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置 操作时的ip地址
     * @param ipAddress 操作时的ip地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取 后台管理员Id
     * @return backerId
     */
    public int getBackerId() {
        return backerId;
    }

    /**
     * 设置 后台管理员Id
     * @param backerId 后台管理员Id
     */
    public void setBackerId(int backerId) {
        this.backerId = backerId;
    }

    /**
     * 获取 后台管理员帐号
     * @return backerAccount
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 设置 后台管理员帐号
     * @param backerAccount 后台管理员帐号
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    /**
     * 获取 记录时间
     * @return addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 设置 记录时间
     * @param addTime 记录时间
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
