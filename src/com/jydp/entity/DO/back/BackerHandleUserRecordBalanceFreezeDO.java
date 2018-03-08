package com.jydp.entity.DO.back;

import java.sql.Timestamp;

/**
 * 后台管理员增减用户冻结余额记录
 * @author sy
 */
public class BackerHandleUserRecordBalanceFreezeDO {
    private long id;  //记录号
    private int userId;  //用户Id
    private String userAccount;  //用户账号
    private int typeHandle;  //操作类型，1：增加，2：减少
    private double userBalance;  //冻结资产
    private String remarks;  //备注
    private String ipAddress;  //操作时的ip地址
    private int backerId;  //后台管理员Id
    private String backerAccount;  //后台管理员帐号
    private Timestamp addTime;  //记录时间

    /**
     * 记录号
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 记录号
     * @param id
     */
    public void setId(long id) {
        this.id = id;
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
     * 用户账号
     * @return
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户账号
     * @param userAccount
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 操作类型，1：增加，2：减少
     * @return
     */
    public int getTypeHandle() {
        return typeHandle;
    }

    /**
     * 操作类型，1：增加，2：减少
     * @param typeHandle
     */
    public void setTypeHandle(int typeHandle) {
        this.typeHandle = typeHandle;
    }

    /**
     * 冻结资产
     * @return
     */
    public double getUserBalance() {
        return userBalance;
    }

    /**
     * 冻结资产
     * @param userBalance
     */
    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    /**
     * 备注
     * @return
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 操作时的ip地址
     * @return
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 操作时的ip地址
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 后台管理员Id
     * @return
     */
    public int getBackerId() {
        return backerId;
    }

    /**
     * 后台管理员Id
     * @param backerId
     */
    public void setBackerId(int backerId) {
        this.backerId = backerId;
    }

    /**
     * 后台管理员帐号
     * @return
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 后台管理员帐号
     * @param backerAccount
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    /**
     * 操作时间
     * @return
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 操作时间
     * @param addTime
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
