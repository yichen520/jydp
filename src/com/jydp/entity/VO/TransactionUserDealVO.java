package com.jydp.entity.VO;

import com.iqmkj.utils.NumberUtil;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;

import java.sql.Timestamp;

/**
 * 用户成交记录
 * @author fk
 *
 */
public class TransactionUserDealVO extends TransactionUserDealDO {

    private String userAccount;  //用户账号
    private Timestamp pendTime;  //挂单时间

    private double fee;  //手续费
    private double actualPrice;  //实际价

    /**
     * 用户账号
     * @return the userAccount
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户账号
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 挂单时间
     * @return the pendTime
     */
    public Timestamp getPendTime() {
        return pendTime;
    }

    /**
     * 挂单时间
     * @param pendTime the pendTime to set
     */
    public void setPendTime(Timestamp pendTime) {
        this.pendTime = pendTime;
    }

    /**
     * 手续费
     * @return the fee
     */
    public double getFee() {
        return fee;
    }

    /**
     * 手续费
     * @param fee the fee to set
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * 实际价
     * @return the actualPrice
     */
    public double getActualPrice() {
        return actualPrice;
    }

    /**
     * 实际价
     * @param actualPrice the actualPrice to set
     */
    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }
}
