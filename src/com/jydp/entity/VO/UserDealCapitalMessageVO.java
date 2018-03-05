package com.jydp.entity.VO;

/**
 * The type User deal capital message vo.
 */
public class UserDealCapitalMessageVO {
    private double userBalance;  //用户可用美金
    private double userBalanceLock;  //用户锁定美金
    private double currencyNumber;  //用户币数量
    private double currencyNumberLock;  //用户币冻结数量
    private double currencyNumberSum;  //用户币总金额

    /**
     * 用户可用美金
     *
     * @return the user balance
     */
    public double getUserBalance() {
        return userBalance;
    }

    /**
     * 用户可用美金
     *
     * @param userBalance the user balance
     */
    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    /**
     * 用户锁定美金
     *
     * @return the user balance lock
     */
    public double getUserBalanceLock() {
        return userBalanceLock;
    }

    /**
     * 用户锁定美金
     *
     * @param userBalanceLock the user balance lock
     */
    public void setUserBalanceLock(double userBalanceLock) {
        this.userBalanceLock = userBalanceLock;
    }

    /**
     * 用户币数量
     *
     * @return the currency number
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 用户币数量
     *
     * @param currencyNumber the currency number
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 用户币冻结数量
     *
     * @return the currency number lock
     */
    public double getCurrencyNumberLock() {
        return currencyNumberLock;
    }

    /**
     * 用户币冻结数量
     *
     * @param currencyNumberLock the currency number lock
     */
    public void setCurrencyNumberLock(double currencyNumberLock) {
        this.currencyNumberLock = currencyNumberLock;
    }

    /**
     * 用户币总金额
     *
     * @return the currency number sum
     */
    public double getCurrencyNumberSum() {
        return currencyNumberSum;
    }

    /**
     * 用户币总金额
     *
     * @param currencyNumberSum the currency number sum
     */
    public void setCurrencyNumberSum(double currencyNumberSum) {
        this.currencyNumberSum = currencyNumberSum;
    }
}
