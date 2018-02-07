package com.jydp.entity.DO.user;

import java.sql.Timestamp;

/**
 * 用户账户记录
 *
 * @author hht
 */
public class UserBalanceDO {

    private String orderNo;  //记录号：业务类型（2）+日期（6）+随机位（10）
    private long userId;  //用户Id
    private long paymentType;  //收支类型：1：买入，2：卖出
    private String fromType;  //账户来源
    private double balanceNumber;  //交易金额：单位(美刀$)
    private String remark;  //备注：手续费
    private Timestamp addTime;  //添加时间

    /**
     * 记录号：业务类型（2）+日期（6）+随机位（10）
     *
     * @return the order no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 记录号：业务类型（2）+日期（6）+随机位（10）
     *
     * @param orderNo the order no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 用户Id
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * 用户Id
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * 收支类型：1：买入，2：卖出
     *
     * @return the payment type
     */
    public long getPaymentType() {
        return paymentType;
    }

    /**
     * 收支类型：1：买入，2：卖出
     *
     * @param paymentType the payment type
     */
    public void setPaymentType(long paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 账户来源
     *
     * @return the from type
     */
    public String getFromType() {
        return fromType;
    }

    /**
     * 账户来源
     *
     * @param fromType the from type
     */
    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    /**
     * 交易金额：单位(美刀$)
     *
     * @return the balance number
     */
    public double getBalanceNumber() {
        return balanceNumber;
    }

    /**
     * 交易金额：单位(美刀$)
     *
     * @param balanceNumber the balance number
     */
    public void setBalanceNumber(double balanceNumber) {
        this.balanceNumber = balanceNumber;
    }

    /**
     * Gets remark.
     *
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注：手续费
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 备注：手续费
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
