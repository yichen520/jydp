package com.jydp.entity.DTO;

/**
 * wap端用户修改支付密码
 * Created by xushilong3623 on 2018/3/27.
 */
public class WapUserModifyPayPasswordDTO {

    /**
     * 原始密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 重复密码
     */
    private String confirmPassword;

    /**
     * 验证码
     */
    private String validCode;

    /**
     * 原始密码
     */
    public String getOldPassword() {
        return oldPassword;
    }
    /**
     * 原始密码
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    /**
     * 新密码
     */
    public String getNewPassword() {
        return newPassword;
    }
    /**
     * 新密码
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    /**
     * 重复密码
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }
    /**
     * 重复密码
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    /**
     * 验证码
     */
    public String getValidCode() {
        return validCode;
    }
    /**
     * 验证码
     */
    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

}
