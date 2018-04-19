package com.jydp.entity.DO.user;

/**
 *
 * 注册信息账号
 * @author cyfIverson
 * @create 2018-04-19
 */
public class UserRegisterDO extends UserDO{

    private String validateCode; //手机验证码

    /**
     * 手机验证码
     * @return the validateCode
     */
    public String getValidateCode() {
        return validateCode;
    }

    /**
     * 手机验证码
     * @param validateCode the register validateCode
     */
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
