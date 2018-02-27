package com.jydp.entity.DO.user;

import java.sql.Timestamp;

/**
 * 用户账户记录
 *
 * @author hht
 */
public class UserBalanceDO {

    private String orderNo;  //记录号：业务类型（2）+日期（6）+随机位（10）
    private int userId;  //用户Id
    private String fromType;  //账户来源
    private int currencyId;  //币种Id,美元id=999
    private String currencyName;  //货币名称
    private double balanceNumber;  //交易数量
    private double frozenNumber;  //冻结数量
    private String remark;  //备注：手续费
    private Timestamp addTime;  //添加时间

    /**
     * 记录号：业务类型（2）+日期（6）+随机位（10）
     * @return the order no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 记录号：业务类型（2）+日期（6）+随机位（10）
     * @param orderNo the order no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 用户Id
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 账户来源
     * @return the from type
     */
    public String getFromType() {
        return fromType;
    }

    /**
     * 账户来源
     * @param fromType the from type
     */
    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    /**
     * 交易数量
     * @return the balance number
     */
    public double getBalanceNumber() {
        return balanceNumber;
    }

    /**
     * 交易数量
     * @param balanceNumber the balance number
     */
    public void setBalanceNumber(double balanceNumber) {
        this.balanceNumber = balanceNumber;
    }

    /**
     * 备注：手续费
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注：手续费
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 添加时间
     * @return the addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime the addTime
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    /**
     * 币种Id,美元id=999
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id,美元id=999
     * @param currencyId the currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 冻结数量
     * @return the frozenNumber
     */
    public double getFrozenNumber() {
        return frozenNumber;
    }

    /**
     * 冻结数量
     * @param frozenNumber the frozenNumber
     */
    public void setFrozenNumber(double frozenNumber) {
        this.frozenNumber = frozenNumber;
    }

    /**
     * 货币名称
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     * @param currencyName the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

}
