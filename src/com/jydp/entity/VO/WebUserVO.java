package com.jydp.entity.VO;

import java.sql.Timestamp;

/**
 * Created by xushilong3623 on 2018/4/19.
 */
public class WebUserVO {

    private Integer userId;  //用户Id
    private String userAccount;  //用户账号
    private String phoneAreaCode;  //手机号区号
    private String phoneNumber;  //绑定手机号
    private Double userBalance;  //可用资产单位(XT)
    private Double userBalanceLock;  //锁定资产单位(XT)
    private Double totalUserBalance; //用户总资产

    //用户id
    public Integer getUserId() {
        return userId;
    }
    //用户id
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    //用户账号
    public String getUserAccount() {
        return userAccount;
    }
    //用户账号
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    //手机号区号
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }
    //手机号区号
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }
    //绑定手机号
    public String getPhoneNumber() {
        return phoneNumber;
    }
    //绑定手机号
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //可用资产单位(XT)
    public Double getUserBalance() {
        return userBalance;
    }
    //可用资产单位(XT)
    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }
    //锁定资产单位(XT)
    public Double getUserBalanceLock() {
        return userBalanceLock;
    }
    //锁定资产单位(XT)
    public void setUserBalanceLock(Double userBalanceLock) {
        this.userBalanceLock = userBalanceLock;
    }
    //用户总资产
    public Double getTotalUserBalance() {
        return totalUserBalance;
    }
    //用户总资产
    public void setTotalUserBalance(Double totalUserBalance) {
        this.totalUserBalance = totalUserBalance;
    }
}
