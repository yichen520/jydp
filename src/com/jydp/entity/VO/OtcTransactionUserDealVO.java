package com.jydp.entity.VO;

import com.iqmkj.utils.StringUtil;
import config.FileUrlConfig;

import java.sql.Timestamp;

/**
 * 场外交易用户成交记录展示
 * @author sy
 */
public class OtcTransactionUserDealVO {
    private String otcOrderNo; //记录号 业务类型（2）+日期（6）+随机位（10）
    private double currencyNumber; //成交数量
    private double currencyTotalPrice; //成交总价
    private int typeId; //收款方式Id
    private String currencyName; //货币名称
    private String dealerName;  //经销商名称
    private int paymentType; //收款方式标识：1：银行卡，2：支付宝，3：微信
    private String paymentAccount; //收款账号
    private String bankName; //收款银行
    private String bankCode; //收款银行标识
    private String bankBranch; //收款支行
    private String paymentName; //收款人姓名
    private String paymentPhone; //收款人手机号
    private String paymentImage; //二维码地址（绝对路径）
    private String imageUrl;  //二维码地址
    private int dealType; //收支类型：1：买入，2：卖出，3：撤销
    private int dealStatus; //状态：1：待付款，2：已付款（待确认），3：已完成，4：用户取消，5：商家取消
    private String remark; //备注
    private Timestamp updateTime; //修改时间
    private Timestamp addTime; //添加时间

    /**
     * 记录号 业务类型（2）+日期（6）+随机位（10）
     * @return
     */
    public String getOtcOrderNo() {
        return otcOrderNo;
    }

    /**
     * 记录号 业务类型（2）+日期（6）+随机位（10）
     * @param otcOrderNo
     */
    public void setOtcOrderNo(String otcOrderNo) {
        this.otcOrderNo = otcOrderNo;
    }

    /**
     * 成交数量
     * @return
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 成交数量
     * @param currencyNumber
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 收款方式Id
     * @return
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * 收款方式Id
     * @param typeId
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * 成交总价
     * @return
     */
    public double getCurrencyTotalPrice() {
        return currencyTotalPrice;
    }

    /**
     * 成交总价
     * @param currencyTotalPrice
     */
    public void setCurrencyTotalPrice(double currencyTotalPrice) {
        this.currencyTotalPrice = currencyTotalPrice;
    }

    /**
     * 货币名称
     * @return
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     * @param currencyName
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 经销商名称
     * @return
     */
    public String getDealerName() {
        return dealerName;
    }

    /**
     * 经销商名称
     * @param dealerName
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    /**
     * 收款方式标识：1：银行卡，2：支付宝，3：微信
     * @return
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 收款方式标识：1：银行卡，2：支付宝，3：微信
     * @param paymentType
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 收款账号
     * @return
     */
    public String getPaymentAccount() {
        return paymentAccount;
    }

    /**
     * 收款账号
     * @param paymentAccount
     */
    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    /**
     * 收款银行
     * @return
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 收款银行
     * @param bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 收款银行标识
     * @return
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 收款银行标识
     * @param bankCode
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 收款支行
     * @return
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     * 收款支行
     * @param bankBranch
     */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    /**
     * 收款人姓名
     * @return
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * 收款人姓名
     * @param paymentName
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * 收款人手机号
     * @return
     */
    public String getPaymentPhone() {
        return paymentPhone;
    }

    /**
     * 收款人手机号
     * @param paymentPhone
     */
    public void setPaymentPhone(String paymentPhone) {
        this.paymentPhone = paymentPhone;
    }

    /**
     * 二维码地址
     * @return
     */
    public String getPaymentImage() {
        return paymentImage;
    }

    /**
     * 二维码地址
     * @param paymentImage
     */
    public void setPaymentImage(String paymentImage) {
        this.paymentImage = paymentImage;
    }

    /**
     * 二维码地址（绝对路径）
     *
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 二维码地址（绝对路径）
     *
     * @param imageUrl the image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        if (StringUtil.isNotNull(imageUrl)) {
            setPaymentImage(FileUrlConfig.file_visit_url + imageUrl);
        }
    }

    /**
     * 收支类型：1：买入，2：卖出，3：撤销
     * @return
     */
    public int getDealType() {
        return dealType;
    }

    /**
     * 收支类型：1：买入，2：卖出，3：撤销
     * @param dealType
     */
    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    /**
     * 状态：1：待付款，2：已付款（待确认），3：已完成，4：用户取消，5：商家取消
     * @return
     */
    public int getDealStatus() {
        return dealStatus;
    }

    /**
     * 状态：1：待付款，2：已付款（待确认），3：已完成，4：用户取消，5：商家取消
     * @param dealStatus
     */
    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    /**
     * 备注
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 修改时间
     * @return
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 添加时间
     * @return
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }


}
