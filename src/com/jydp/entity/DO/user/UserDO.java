package com.jydp.entity.DO.user;

import java.sql.Timestamp;

/**
 * 用户帐号
 *
 * @author hht
 */
public class UserDO {

    private int userId;  //用户Id
    private String userAccount;  //用户账号
    private String password;  //登录密码
    private String payPassword;  //支付密码
    private String phoneAreaCode;  //手机号区号
    private String phoneNumber;  //绑定手机号
    private double userBalance;  //可用资产单位(美刀$)
    private double userBalanceLock;  //锁定资产单位(美刀$)
    private int accountStatus;  //账号状态：1：启用，2：禁用，-1：删除
    private Timestamp addTime;  //注册时间
    private int authenticationStatus;//用户实名认证状态：1：待审核，2：审核通过，3：审核拒绝， 4：未提交

    /**
     * 用户Id
     *
     * @return the user id
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
     * 登录密码
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 登录密码
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 支付密码
     *
     * @return the pay password
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 支付密码
     *
     * @param payPassword the pay password
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }


    /**
     * 手机号区号
     *
     * @return the phone area code
     */
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    /**
     * 手机号区号
     *
     * @param phoneAreaCode the phone area code
     */
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }


    /**
     * 绑定手机号
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 绑定手机号
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * 可用资产单位(美刀$)
     *
     * @return the user balance
     */
    public double getUserBalance() {
        return userBalance;
    }

    /**
     * 可用资产单位(美刀$)
     *
     * @param userBalance the user balance
     */
    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }


    /**
     * 锁定资产单位(美刀$)
     *
     * @return the user balance lock
     */
    public double getUserBalanceLock() {
        return userBalanceLock;
    }

    /**
     * 锁定资产单位(美刀$)
     *
     * @param userBalanceLock the user balance lock
     */
    public void setUserBalanceLock(double userBalanceLock) {
        this.userBalanceLock = userBalanceLock;
    }


    /**
     * 账号状态：1：启用，2：禁用，-1：删除
     *
     * @return the account status
     */
    public int getAccountStatus() {
        return accountStatus;
    }

    /**
     * 账号状态：1：启用，2：禁用，-1：删除
     *
     * @param accountStatus the account status
     */
    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }


    /**
     * 注册时间
     *
     * @return the add time
     */
    public java.sql.Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 注册时间
     *
     * @param addTime the add time
     */
    public void setAddTime(java.sql.Timestamp addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取实名认证状态：1：待审核，2：审核通过，3：审核拒绝， 4：未提交
     * @return authenticationStatus 实名认证状态
     */
    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    /**
     * 设置实名认证状态：1：待审核，2：审核通过，3：审核拒绝， 4：未提交
     * @param authenticationStatus 实名认证状态
     */
    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
