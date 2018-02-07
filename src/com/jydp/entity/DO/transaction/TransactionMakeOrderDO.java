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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public double getCurrencyTotalPrice() {
        return currencyTotalPrice;
    }

    public void setCurrencyTotalPrice(double currencyTotalPrice) {
        this.currencyTotalPrice = currencyTotalPrice;
    }

    public String getBackerAccount() {
        return backerAccount;
    }

    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(int executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
