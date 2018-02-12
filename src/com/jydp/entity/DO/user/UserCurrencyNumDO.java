package com.jydp.entity.DO.user;


import java.sql.Timestamp;

/**
 * 用户币数量
 *
 * @author hht
 */
public class UserCurrencyNumDO {

    private int userId;  //用户Id
    private int currencyId;  //币种Id
    private double currencyNumber;  //货币数量
    private double currencyNumberLock;  //冻结数量
    private Timestamp addTime;  //添加时间

    /**
     * 用户Id
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

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
     * 货币数量
     *
     * @return the currency number
     */
    public double getCurrencyNumber() {
        return currencyNumber;
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
        return currencyNumberLock;
    }

    /**
     * 冻结数量
     *
     * @param currencyNumberLock the currency number lock
     */
    public void setCurrencyNumberLock(double currencyNumberLock) {
        this.currencyNumberLock = currencyNumberLock;
    }

    /**
     * 添加时间
     *
     * @return the add time
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     *
     * @param addTime the add time
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
