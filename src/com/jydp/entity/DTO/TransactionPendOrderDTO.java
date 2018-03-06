package com.jydp.entity.DTO;

import com.iqmkj.utils.BigDecimalUtil;

/**
 * 交易中心 挂单记录合并显示
 * @Author hz
 */
public class TransactionPendOrderDTO {
    private double pendingPrice;  //挂单单价
    private double pendingNumber;  //挂单数量
    private double dealNumber;  //成交数量
    private double restNumber; //剩余挂单数量
    private double sumPrice; //剩余挂单总价

    /**
     * 挂单单价
     * @return the pendingPrice
     */
    public double getPendingPrice() {
        return pendingPrice;
    }

    /**
     * 挂单单价
     * @param pendingPrice the pendingPrice to set
     */
    public void setPendingPrice(double pendingPrice) {
        this.pendingPrice = pendingPrice;
    }

    /**
     * 挂单数量
     * @return the pendingNumber
     */
    public double getPendingNumber() {
        return pendingNumber;
    }

    /**
     * 挂单数量
     * @param pendingNumber the pendingNumber to set
     */
    public void setPendingNumber(double pendingNumber) {
        this.pendingNumber = pendingNumber;
    }

    /**
     * 成交数量
     * @return the dealNumber
     */
    public double getDealNumber() {
        return dealNumber;
    }

    /**
     * 成交数量
     * @param dealNumber the dealNumber to set
     */
    public void setDealNumber(double dealNumber) {
        this.dealNumber = dealNumber;

        setRestNumber(BigDecimalUtil.sub(this.pendingNumber, this.dealNumber));
        setSumPrice(BigDecimalUtil.mul(BigDecimalUtil.sub(this.pendingNumber, this.dealNumber), this.pendingPrice));
    }

    /**
     * 剩余挂单数量
     * @return the restNumber
     */
    public double getRestNumber() {
        return restNumber;
    }

    /**
     * 剩余挂单数量
     * @param restNumber the restNumber to set
     */
    public void setRestNumber(double restNumber) {
        this.restNumber = restNumber;
    }

    /**
     * 剩余挂单总价
     * @return the sumPrice
     */
    public double getSumPrice() {
        return sumPrice;
    }

    /**
     * 剩余挂单总价
     * @param sumPrice the sumPrice to set
     */
    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }
}
