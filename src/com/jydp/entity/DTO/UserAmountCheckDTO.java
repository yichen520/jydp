package com.jydp.entity.DTO;

/**
 * 核对用户账户
 * @author whx
 */
public class UserAmountCheckDTO {

    private int userId; //用户id
    private int currencyId; //币种id
    private double beyondAmount; //超出可用美金、币数量的最大值
    private double beyondAmountLock; //超出锁定美金、币数量的最大值

    /**
     * 用户id
     * @return 用户id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 币种id
     * @return 币种id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种id
     * @param currencyId 币种id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 超出可用美金、币数量的最大值
     * @return 超出可用的美金、币数量
     */
    public double getBeyondAmount() {
        return beyondAmount;
    }

    /**
     * 超出可用美金、币数量的最大值
     * @param beyondAmount 超出可用的美金、币数量
     */
    public void setBeyondAmount(double beyondAmount) {
        this.beyondAmount = beyondAmount;
    }

    /**
     * 超出锁定美金、币数量的最大值
     * @return 超出锁定的美金、币数量
     */
    public double getBeyondAmountLock() {
        return beyondAmountLock;
    }

    /**
     * 超出锁定美金、币数量的最大值
     * @param beyondAmountLock 超出锁定的美金、币数量
     */
    public void setBeyondAmountLock(double beyondAmountLock) {
        this.beyondAmountLock = beyondAmountLock;
    }

}
