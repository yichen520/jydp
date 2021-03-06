package com.jydp.entity.DTO;


/**
 * 用户账户传输类 (用于数据记录更新)
 * @author yk
 */
public class UserDTO {

    private String userAccount;  //用户账号
    private String password;  //登录密码
    private String payPassword;  //支付密码
    private String phoneAreaCode;  //手机号区号
    private String phoneNumber;  //绑定手机号
    private double userBalance;  //可用资产单位(美刀$)
    private double userBalanceLock;  //锁定资产单位(美刀$)

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

}
