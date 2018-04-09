package com.jydp.entity.DO.otc;

import java.sql.Timestamp;

/**
 * 用户标识经销商
 * @author yk
 */
public class OtcDealerUserDO {
    private int dealerId; //标识表主键Id
    private int userId; //用户Id
    private String userAccount; //用户帐号
    private String dealerName; //经销商名称
    private String ipAddress; //操作时的ip地址
    private int backerId; //后台管理员Id
    private int userStatus; //经销商状态(默认值1)：1：启用，2：禁用，-1：删除
    private String remark; //备注
    private Timestamp updateTime;//修改时间
    private Timestamp addTime;  //添加时间

    /**
     * 获取 标识表主键Id
     * @return dealerId
     */
    public int getDealerId() {
        return dealerId;
    }

    /**
     * 设置 标识表主键Id
     * @param dealerId 标识表主键Id
     */
    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
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
     * 获取 经销商名称
     * @return dealerName
     */
    public String getDealerName() {
        return dealerName;
    }

    /**
     * 设置 经销商名称
     * @param dealerName 经销商名称
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
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
     * 获取 经销商状态
     * @return userStatus
     */
    public int getUserStatus() {
        return userStatus;
    }

    /**
     * 设置 经销商状态
     * @param userStatus 经销商状态
     */
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
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
