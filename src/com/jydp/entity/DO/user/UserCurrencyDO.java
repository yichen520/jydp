package com.jydp.entity.DO.user;


import java.sql.Timestamp;

/**
 * 用户货币记录
 *
 * @author hht
 */
public class UserCurrencyDO {

    private String orderNo;  //记录号：业务类型（2）+日期（6）+随机位（10）
    private long userId;  //用户Id
    private long paymentType;  //收支类型：1：增加，2：减少
    private String fromType;  //账户来源
    private double currencyNumber;  //货币数量
    private long currencyId;  //币种Id
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
     * 收支类型：1：增加，2：减少
     *
     * @return the payment type
     */
    public long getPaymentType() {
        return paymentType;
    }

    /**
     * 收支类型：1：增加，2：减少
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
     * 币种Id
     *
     * @return the currency id
     */
    public long getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     *
     * @param currencyId the currency id
     */
    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }


    /**
     * 备注：手续费
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
     * 添加时间
     *
     * @return the add time
     */
    public java.sql.Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     *
     * @param addTime the add time
     */
    public void setAddTime(java.sql.Timestamp addTime) {
        this.addTime = addTime;
    }

}
