package com.jydp.entity.DTO;

/**
 * Description: 用户货币数量信息（后台）
 * Author: hht
 * Date: 2018-02-23 17:17
 */
public class BackerUserCurrencyNumDTO {
    private String currencyName;  //货币名称
    private double currencyNumber;  //货币数量
    private double currencyNumberLock;  //冻结数量

    /**
     * 货币名称
     *
     * @return the currency name
     */
    public String getCurrencyName() {
        return this.currencyName;
    }

    /**
     * 货币名称
     *
     * @param currencyName the currency name
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 货币数量
     *
     * @return the currency number
     */
    public double getCurrencyNumber() {
        return this.currencyNumber;
    }

    /**
     * 货币数量
     *
     * @param currencyNumber the currency number
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 冻结数量
     *
     * @return the currency number lock
     */
    public double getCurrencyNumberLock() {
        return this.currencyNumberLock;
    }

    /**
     * 冻结数量
     *
     * @param currencyNumberLock the currency number lock
     */
    public void setCurrencyNumberLock(double currencyNumberLock) {
        this.currencyNumberLock = currencyNumberLock;
    }
}
