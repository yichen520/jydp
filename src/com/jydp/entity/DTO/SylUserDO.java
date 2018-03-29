package com.jydp.entity.DTO;

import com.jydp.entity.DO.user.UserDO;

/**
 * 盛源链绑定接口参数
 */
public class SylUserDO extends UserDO {
    private String userCertNo;  //身份证号

    /**
     * 身份证号
     *
     * @return the user cert no
     */
    public String getUserCertNo() {
        return userCertNo;
    }

    /**
     * 身份证号
     *
     * @param userCertNo the user cert no
     */
    public void setUserCertNo(String userCertNo) {
        this.userCertNo = userCertNo;
    }
}
