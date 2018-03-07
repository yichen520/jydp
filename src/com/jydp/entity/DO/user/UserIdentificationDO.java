package com.jydp.entity.DO.user;


import java.sql.Timestamp;

/**
 * 用户认证
 *
 * @author hht
 */
public class UserIdentificationDO {

    private long id;  //记录Id
    private int userId;  //用户Id
    private String userAccount;  //用户账号
    private String userName;  //用户姓名
    private String phoneAreaCode;  //手机号区号
    private String userPhone;  //手机号
    private int userCertType;  //证件类型，1:身份证，2：护照
    private String userCertNo;  //证件号码
    private int identificationStatus;  //实名认证状态，1：待审核，2：审核通过，3：审核拒绝
    private String remark;  //备注
    private Timestamp identiTime;  //审核时间
    private Timestamp addTime;  //提交时间

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
     * 证件类型，1:身份证，2：护照
     *
     * @return the user cert type
     */
    public int getUserCertType() {
        return userCertType;
    }

    /**
     * 证件类型，1:身份证，2：护照
     *
     * @param userCertType the user cert type
     */
    public void setUserCertType(int userCertType) {
        this.userCertType = userCertType;
    }

    /**
     * 记录Id
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * 记录Id
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }


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
     * 用户姓名
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户姓名
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * 手机号
     *
     * @return the user phone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 手机号
     *
     * @param userPhone the user phone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    /**
     * 证件号码
     *
     * @return the user cert no
     */
    public String getUserCertNo() {
        return userCertNo;
    }

    /**
     * 证件号码
     *
     * @param userCertNo the user cert no
     */
    public void setUserCertNo(String userCertNo) {
        this.userCertNo = userCertNo;
    }


    /**
     * 实名认证状态，1：待审核，2：审核通过，3：审核拒绝
     *
     * @return the identification status
     */
    public int getIdentificationStatus() {
        return identificationStatus;
    }

    /**
     * 实名认证状态，1：待审核，2：审核通过，3：审核拒绝
     *
     * @param identificationStatus the identification status
     */
    public void setIdentificationStatus(int identificationStatus) {
        this.identificationStatus = identificationStatus;
    }


    /**
     * 备注
     *
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


    /**
     * 审核时间
     *
     * @return the identi time
     */
    public java.sql.Timestamp getIdentiTime() {
        return identiTime;
    }

    /**
     * 审核时间
     *
     * @param identiTime the identi time
     */
    public void setIdentiTime(java.sql.Timestamp identiTime) {
        this.identiTime = identiTime;
    }


    /**
     * 提交时间
     *
     * @return the add time
     */
    public java.sql.Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 提交时间
     *
     * @param addTime the add time
     */
    public void setAddTime(java.sql.Timestamp addTime) {
        this.addTime = addTime;
    }

}
