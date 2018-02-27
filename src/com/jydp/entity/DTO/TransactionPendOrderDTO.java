package com.jydp.entity.DTO;

/**
 * 交易中心 挂单记录合并显示
 * @Author hz
 */
public class TransactionPendOrderDTO {
    private double pendingPrice;  //挂单单价
    private double pendingNumber;  //挂单数量
    private double dealNumber;  //成交数量

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
    }
}
