package com.jydp.entity.DTO;

/**
 * Description: 交易大盘保底价
 * Author: hht
 * Date: 2018-03-13 14:56
 */
public class TransactionBottomPriceDTO {
    private int currencyId;  //币种Id
    private double totalPrice;  //成交总价
    private double totalNumber;  //成交总数量

    /**
     * 币种Id
     *
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     *
     * @param currencyId the currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 成交总价
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * 成交总价
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 成交总数量
     *
     * @return the total number
     */
    public double getTotalNumber() {
        return totalNumber;
    }

    /**
     * 成交总数量
     *
     * @param totalNumber the total number
     */
    public void setTotalNumber(double totalNumber) {
        this.totalNumber = totalNumber;
    }
}
