package com.jydp.entity.VO;

/**
 * 用户币种管理
 * @Author: wqq
 */
public class UserCoinConfigVO {
    private int userId;  //用户Id
    private int currencyId;  //币种Id
    private String currencyName; //币种名称
    private double currencyNumber;  //货币数量
    private double freeCurrencyNumber; //免审数量
    private double minCurrencyNumber; //最低数量

    /**
     * 用户Id
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 币种Id
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 币种名称
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 币种名称
     * @param currencyName the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 货币数量
     * @return the currencyNumber
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 货币数量
     * @param currencyNumber the currencyNumber to set
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     *  免审数量
     * @return the freeCurrencyNumber
     */
    public double getFreeCurrencyNumber() {
        return freeCurrencyNumber;
    }

    /**
     * 免审数量
     * @param freeCurrencyNumber the freeCurrencyNumber to set
     */
    public void setFreeCurrencyNumber(double freeCurrencyNumber) {
        this.freeCurrencyNumber = freeCurrencyNumber;
    }

    /**
     * 最低数量
     * @return the minCurrencyNumber
     */
    public double getMinCurrencyNumber() {
        return minCurrencyNumber;
    }

    /**
     * 最低数量
     * @param minCurrencyNumber the minCurrencyNumber to set
     */
    public void setMinCurrencyNumber(double minCurrencyNumber) {
        this.minCurrencyNumber = minCurrencyNumber;
    }
}
