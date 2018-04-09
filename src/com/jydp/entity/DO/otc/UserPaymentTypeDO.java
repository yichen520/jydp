package com.jydp.entity.DO.otc;

import java.sql.Timestamp;

/**
 * 用户收款方式
 * @Author yk
 */
public class UserPaymentTypeDO {
    private int typeId; //主键Id
    private int userId; //用户Id
    private int paymentType; //收款方式标识：1：银行卡，2：支付宝，3：微信
    private String paymentAccount; //收款账号
    private String bankName; //收款银行
    private String bankCode; //收款银行标识
    private String bankBranch; //收款支行
    private String paymentName; //收款人姓名
    private String paymentPhone; //收款人手机号
    private String paymentImage; //二维码地址
    private int typeStatus; //状态(默认1)：1：启用，2：禁用，-1：删除
    private String remark; //备注
    private Timestamp updateTime; //修改时间
    private Timestamp addTime; //添加时间

    /**
     * 获取 主键Id
     * @return typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * 设置 主键Id
     * @param typeId 主键Id
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取 用户Id
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置 用户Id
     * @param userId 用户Id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取 收款方式
     * @return paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 设置 收款方式
     * @param paymentType 收款方式
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 获取 收款账号
     * @return paymentAccount
     */
    public String getPaymentAccount() {
        return paymentAccount;
    }

    /**
     * 设置 收款账号
     * @param paymentAccount 收款账号
     */
    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    /**
     * 获取 收款银行
     * @return bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置 收款银行
     * @param bankName 收款银行
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取 收款银行标识
     * @return bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置 收款银行标识
     * @param bankCode 收款银行标识
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取 收款支行
     * @return bankBranch
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     * 设置 收款支行
     * @param bankBranch 收款支行
     */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    /**
     * 获取 收款人姓名
     * @return paymentName
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * 设置 收款人姓名
     * @param paymentName 收款人姓名
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * 获取 收款人手机号
     * @return paymentPhone
     */
    public String getPaymentPhone() {
        return paymentPhone;
    }

    /**
     * 设置 收款人手机号
     * @param paymentPhone 收款人手机号
     */
    public void setPaymentPhone(String paymentPhone) {
        this.paymentPhone = paymentPhone;
    }

    /**
     * 获取 二维码地址
     * @return paymentImage
     */
    public String getPaymentImage() {
        return paymentImage;
    }

    /**
     * 设置 二维码地址
     * @param paymentImage 二维码地址
     */
    public void setPaymentImage(String paymentImage) {
        this.paymentImage = paymentImage;
    }

    /**
     * 获取 状态
     * @return typeStatus
     */
    public int getTypeStatus() {
        return typeStatus;
    }

    /**
     * 设置 状态
     * @param typeStatus 状态
     */
    public void setTypeStatus(int typeStatus) {
        this.typeStatus = typeStatus;
    }

    /**
     * 获取 备注
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 修改时间
     * @return updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 添加时间
     * @return addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 设置 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
