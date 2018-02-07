package com.jydp.entity.DO.system;

/**
 * 系统手机验证
 * @author whx
 *
 */
public class SystemValidatePhoneDO {

  private long id;  //记录Id
  private String phoneNo;  //手机号
  private String validateCode;  //验证码
  private String ipAddress;  //ip地址
  private java.sql.Timestamp addTime;  //添加时间
  private long validateStatus;  //验证状态
  private java.sql.Timestamp validateTime;  //验证时间


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }


  public String getValidateCode() {
    return validateCode;
  }

  public void setValidateCode(String validateCode) {
    this.validateCode = validateCode;
  }


  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }


  public java.sql.Timestamp getAddTime() {
    return addTime;
  }

  public void setAddTime(java.sql.Timestamp addTime) {
    this.addTime = addTime;
  }


  public long getValidateStatus() {
    return validateStatus;
  }

  public void setValidateStatus(long validateStatus) {
    this.validateStatus = validateStatus;
  }


  public java.sql.Timestamp getValidateTime() {
    return validateTime;
  }

  public void setValidateTime(java.sql.Timestamp validateTime) {
    this.validateTime = validateTime;
  }
}
