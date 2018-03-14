package com.jydp.entity.DTO;

/**
 * Description: 保底价和当前价
 * Author: hht
 * Date: 2018-03-13 15:30
 */
public class TransactionBottomCurrentPriceDTO {
    private String currencyShortName;  //货币简称
    private double keepPrice;  //保底价
    private double currentPrice;  //当前价

    /**
     * 货币简称
     *
     * @return the currency short name
     */
    public String getCurrencyShortName() {
        return currencyShortName;
    }

    /**
     * 货币简称
     *
     * @param currencyShortName the currency short name
     */
    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }

    /**
     * 保底价
     *
     * @return the keep price
     */
    public double getKeepPrice() {
        return keepPrice;
    }

    /**
     * 保底价
     *
     * @param keepPrice the keep price
     */
    public void setKeepPrice(double keepPrice) {
        this.keepPrice = keepPrice;
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
