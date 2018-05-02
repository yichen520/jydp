package com.jydp.entity.DTO;

/**
 * 查询参数
 * Created by xushilong3623 on 2018/4/18.
 */

public class QueryParamDTO {

    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 币种id
     */
    private String currencyId;
    /**
     * 处理类型
     */
    private String dealType;
    /**
     * 处理状态
     */
    private String dealStatus;
    /**
     * 开始添加时间
     */
    private String startAddTime;
    /**
     * 结束添加时间
     */
    private String endAddTime;
    /**
     * 支付类型
     */
    private String paymentType;
    /**
     * 页数
     */
    private String pageNumber;

    /**
     * 订单编号
     */
    private String otcOrderNo;

    /**
     * 经销商名称
     */
    private String dealerName;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getStartAddTime() {
        return startAddTime;
    }

    public void setStartAddTime(String startAddTime) {
        this.startAddTime = startAddTime;
    }

    public String getEndAddTime() {
        return endAddTime;
    }

    public void setEndAddTime(String endAddTime) {
        this.endAddTime = endAddTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getOtcOrderNo() {
        return otcOrderNo;
    }

    public void setOtcOrderNo(String otcOrderNo) {
        this.otcOrderNo = otcOrderNo;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
