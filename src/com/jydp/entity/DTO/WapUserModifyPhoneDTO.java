package com.jydp.entity.DTO;

/**
 * wap端更新手机号码
 * Created by xushilong3623 on 2018/3/28.
 */
public class WapUserModifyPhoneDTO {

    /**
     * 区号
     */
    private String areaCode;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 原先手机的验证码
     */
    private String oldValidCode;

    /**
     * 新的手机的验证码
     */
    private String newValidCode;

    /**
     * 登陆密码
     */
    private String password;
    /**
     * 区号
     */
    public String getAreaCode() {
        return areaCode;
    }
    /**
     * 区号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    /**
     * 电话号码
     */
    public String getPhone() {
        return phone;
    }
    /**
     * 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * 原先手机的验证码
     */
    public String getOldValidCode() {
        return oldValidCode;
    }
    /**
     * 原先手机的验证码
     */
    public void setOldValidCode(String oldValidCode) {
        this.oldValidCode = oldValidCode;
    }
    /**
     * 新的手机的验证码
     */
    public String getNewValidCode() {
        return newValidCode;
    }
    /**
     * 新的手机的验证码
     */
    public void setNewValidCode(String newValidCode) {
        this.newValidCode = newValidCode;
    }
    /**
     * 登陆密码
     */
    public String getPassword() {
        return password;
    }
    /**
     * 登陆密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
