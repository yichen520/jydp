package com.jydp.entity.DO.back;

import java.sql.Timestamp;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 */
public class BackerHandleUserBalanceMoneyDO {
    private long id;  //记录号
    private int userId;  //用户Id
    private String userAccount;  //用户账号
    private int typeHandle;  //操作类型，1：增加，2：减少
    private int currencyId;  //币种id
    private double userBalance;  //可用币 单位（个）
    private String remarks;  //备注
    private String ipAddress;  //操作时的ip地址
    private int backerId;  //后台管理员Id
    private String backerAccount;  //后台管理员帐号
    private Timestamp addTime;  //记录时间

    /**
     * 记录号
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * 记录号
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
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
     * 用户账号
     *
     * @return user account
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
     * 操作类型，1：增加，2：减少
     *
     * @return type handle
     */
    public int getTypeHandle() {
        return typeHandle;
    }

    /**
     * 操作类型，1：增加，2：减少
     *
     * @param typeHandle the type handle
     */
    public void setTypeHandle(int typeHandle) {
        this.typeHandle = typeHandle;
    }

    /**
     * 币种id
     *
     * @return the currency id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种id
     *
     * @param currencyId the currency id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 可用币（个）
     *
     * @return user balance
     */
    public double getUserBalance() {
        return userBalance;
    }

    /**
     * 可用币（个）
     *
     * @param userBalance the user balance
     */
    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    /**
     * 备注
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     *
     * @param remarks the remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 操作时的ip地址
     *
     * @return ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 操作时的ip地址
     *
     * @param ipAddress the ip address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 后台管理员Id
     *
     * @return backer id
     */
    public int getBackerId() {
        return backerId;
    }

    /**
     * 后台管理员Id
     *
     * @param backerId the backer id
     */
    public void setBackerId(int backerId) {
        this.backerId = backerId;
    }

    /**
     * 后台管理员帐号
     *
     * @return backer account
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 后台管理员帐号
     *
     * @param backerAccount the backer account
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    /**
     * 操作时间
     *
     * @return add time
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 操作时间
     *
     * @param addTime the add time
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
