package com.jydp.entity.DTO;

import java.sql.Timestamp;

/**
 * 挂单 表传输对象（含收款方式）
 *
 * @author lgx
 */
public class OtcTransactionPendOrderDTO {
    private String otcPendingOrderNo; //挂单记录号 业务类型（2）+日期（6）+随机位（10）
    private int userId; //用户Id
    private String userAccount; //用户帐号
    private int orderType; //挂单类型 1：出售，2：回购
    private int currencyId; //币种Id
    private String currencyName; //货币名称
    private double pendingRatio; //挂单比例
    private double minNumber; //最小金额
    private double maxNumber; //最大金额
    private String area; //地区(默认CN)

    private String bankAccount; //收款账号
    private String bankName; //收款银行
    private String bankCode; //收款银行标识
    private String bankBranch; //收款支行
    private String paymentName; //收款人姓名
    private String paymentPhone; //收款人手机号

    private String alipayAccount; //支付宝账号
    private String wechatAccount; //微信账号
    private String alipayImage; //支付宝二维码地址
    private String wechatImage; //微信二维码地址
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
     * 获取 用户帐号
     * @return userAccount
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置 用户帐号
     * @param userAccount 用户帐号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 获取 挂单记录号
     *
     * @return otcPendingOrderNo
     */
    public String getOtcPendingOrderNo() {
        return otcPendingOrderNo;
    }

    /**
     * 设置 挂单记录号
     *
     * @param otcPendingOrderNo 挂单记录号
     */
    public void setOtcPendingOrderNo(String otcPendingOrderNo) {
        this.otcPendingOrderNo = otcPendingOrderNo;
    }

    /**
     * 获取 币种Id
     *
     * @return currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 设置 币种Id
     *
     * @param currencyId 币种Id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 获取 挂单比例
     *
     * @return pendingRatio
     */
    public double getPendingRatio() {
        return pendingRatio;
    }

    /**
     * 设置 挂单比例
     *
     * @param pendingRatio 挂单比例
     */
    public void setPendingRatio(double pendingRatio) {
        this.pendingRatio = pendingRatio;
    }


    /**
     * 获取 地区
     *
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置 地区
     *
     * @param area 地区
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取 最小金额
     *
     * @return minNumber
     */
    public double getMinNumber() {
        return minNumber;
    }

    /**
     * 设置 最小金额
     *
     * @param minNumber 最小金额
     */
    public void setMinNumber(double minNumber) {
        this.minNumber = minNumber;
    }

    /**
     * 获取 最大金额
     *
     * @return maxNumber
     */
    public double getMaxNumber() {
        return maxNumber;
    }

    /**
     * 设置 最大金额
     *
     * @param maxNumber 最大金额
     */
    public void setMaxNumber(double maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * 获取 挂单类型
     *
     * @return orderType
     */
    public int getOrderType() {
        return orderType;
    }

    /**
     * 设置 挂单类型
     *
     * @param orderType 挂单类型
     */
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取 银行收款账号
     * @return bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置 银行收款账号
     * @param bankAccount 收款账号
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取 支付宝收款账号
     * @return alipayAccount
     */
    public String getAlipayAccount() {
        return alipayAccount;
    }

    /**
     * 设置 支付宝收款账号
     * @param alipayAccount 收款账号
     */
    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    /**
     * 获取 微信收款账号
     * @return wechatAccount
     */
    public String getWechatAccount() {
        return wechatAccount;
    }

    /**
     * 设置 微信收款账号
     * @param wechatAccount 收款账号
     */
    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
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
     * 获取 支付宝二维码地址
     * @return alipayImage
     */
    public String getAlipayImage() {
        return alipayImage;
    }

    /**
     * 设置 支付宝二维码地址
     * @param alipayImage
     */
    public void setAlipayImage(String alipayImage) {
        this.alipayImage = alipayImage;
    }

    /**
     * 获取 支付宝二维码地址
     * @return wechatImage
     */
    public String getWechatImage() {
        return wechatImage;
    }

    /**
     * 设置 支付宝二维码地址
     * @param wechatImage
     */
    public void setWechatImage(String wechatImage) {
        this.wechatImage = wechatImage;
    }


    /**
     * 获取 货币名称
     * @return currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 设置 货币名称
     * @param currencyName 货币名称
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

}
