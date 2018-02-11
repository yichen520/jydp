package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 做单记录
 * @author fk
 *
 */
public class TransactionMakeOrderDO {

    private String orderNo;  //记录号,业务类型（2）+日期（6）+随机位（10）
    private int paymentType;  //收支类型,1：买入，2：卖出
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double currencyNumber;  //成交数量
    private double currencyTotalPrice;  //成交总价
    private String backerAccount;  //后台管理员帐号
    private String ipAddress;  //操作时的ip地址
    private int executeStatus;  //执行状态，1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
    private String remark;  //备注
    private Timestamp executeTime;  //执行时间
    private Timestamp addTime;  //添加时间

    /**
     * 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 收支类型,1：买入，2：卖出
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 收支类型,1：买入，2：卖出
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
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

    /**
     * 成交数量
     * @return the currencyNumber
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 成交数量
     * @param currencyNumber the currencyNumber to set
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 成交总价
     * @return the currencyTotalPrice
     */
    public double getCurrencyTotalPrice() {
        return currencyTotalPrice;
    }

    /**
     * 成交总价
     * @param currencyTotalPrice the currencyTotalPrice to set
     */
    public void setCurrencyTotalPrice(double currencyTotalPrice) {
        this.currencyTotalPrice = currencyTotalPrice;
    }

    /**
     * 后台管理员帐号
     * @return the backerAccount
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 后台管理员帐号
     * @param backerAccount the backerAccount to set
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    /**
     * 操作时的ip地址
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 操作时的ip地址
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 执行状态，1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @return the executeStatus
     */
    public int getExecuteStatus() {
        return executeStatus;
    }

    /**
     * 执行状态，1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param executeStatus the executeStatus to set
     */
    public void setExecuteStatus(int executeStatus) {
        this.executeStatus = executeStatus;
    }

    /**
     * 备注
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 执行时间
     * @return the executeTime
     */
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    /**
     * 执行时间
     * @param executeTime the executeTime to set
     */
    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
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
     * @param addTime the addTime to set
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
