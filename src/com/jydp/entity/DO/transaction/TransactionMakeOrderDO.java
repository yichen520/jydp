package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 做单记录
 * @author fk
 *
 */
public class TransactionMakeOrderDO {

    private String orderNo;  //记录号,业务类型（2）+日期（6）+随机位（10）
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double currencyPrice;  //成交价格
    private double currencyNumber;  //成交数量
    private String backerAccount;  //后台管理员帐号
    private String ipAddress;  //操作时的ip地址
    private int executeStatus;  //执行状态，1：待执行,2:执行中,3:执行完成,4:执行失败
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
     * 成交价格
     * @return the currencyPrice
     */
    public double getCurrencyPrice() {
        return currencyPrice;
    }

    /**
     * 成交价格
     * @param currencyPrice the currencyPrice to set
     */
    public void setCurrencyPrice(double currencyPrice) {
        this.currencyPrice = currencyPrice;
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
