package com.jydp.entity.DTO;

/**
 * Description: 保底价和当前价
 * Author: hht
 * Date: 2018-03-13 15:30
 */
public class TransactionBottomCurrentPriceDTO {
    private int currencyId;  //币种Id
    private double bottomPrice;  //保底价
    private double currentPrice;  //当前价

    /**
     * 币种Id
     *
     * @return the currency id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     *
     * @param currencyId the currency id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 保底价
     *
     * @return the bottom price
     */
    public double getBottomPrice() {
        return bottomPrice;
    }

    /**
     * 保底价
     *
     * @param bottomPrice the bottom price
     */
    public void setBottomPrice(double bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    /**
     * 当前价
     *
     * @return the current price
     */
    public double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * 当前价
     *
     * @param currentPrice the current price
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
