package com.jydp.entity.VO;

/**
 * wap端用户个人信息
 * Created by xushilong3623 on 2018/3/22.
 */
public class WapUserVO {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户可用余额
     */
    private double userBalance;

    /**
     * 用户冻结余额
     */
    private double userBalanceLock;

    /**
     * 用户id
     */
    public int getUserId() {
        return userId;
    }
    /**
     * 用户id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * 用户账号
     */
    public String getUserAccount() {
        return userAccount;
    }
    /**
     * 用户账号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    /**
     * 用户可用余额
     */
    public double getUserBalance() {
        return userBalance;
    }
    /**
     * 用户可用余额
     */
    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }
    /**
     * 用户冻结余额
     */
    public Double getUserBalanceLock() {
        return userBalanceLock;
    }
    /**
     * 用户冻结余额
     */
    public void setUserBalanceLock(Double userBalanceLock) {
        this.userBalanceLock = userBalanceLock;
    }
}
