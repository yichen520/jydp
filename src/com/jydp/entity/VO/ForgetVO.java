package com.jydp.entity.VO;

import java.io.Serializable;

/**
 * 忘记密码数据体
 * @author yqz
 *
 */
public class ForgetVO implements Serializable {

    private String userAccount; //账号
    private String password;  //密码
    private String validateCode;  //验证码
    private String phoneNumber;  //手机号
    private String phoneAreaCode;  //区域号

    /**
     * 获取账号
     * @return
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置账号
     * @param userAccount
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取验证码
     * @return
     */
    public String getValidateCode() {
        return validateCode;
    }

    /**
     * 设置验证码
     * @param validateCode
     */
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    /**
     * 获取手机号
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置手机号
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取区域号
     * @return
     */
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    /**
     * 设置区域号
     * @param phoneAreaCode
     */
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }
}
