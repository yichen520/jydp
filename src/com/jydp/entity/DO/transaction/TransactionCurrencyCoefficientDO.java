package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 币种系数
 * @author fk
 *
 */
public class TransactionCurrencyCoefficientDO {

    private String orderNo;  //记录号,业务类型（2）+日期（6）+随机位（10）
    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double currencyCoefficient;  //币种系数
    private String backerAccount;  //后台管理员帐号
    private String ipAddress;  //操作时的ip地址
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
     * 币种系数
     * @return the currencyCoefficient
     */
    public double getCurrencyCoefficient() {
        return currencyCoefficient;
    }

    /**
     * 币种系数
     * @param currencyCoefficient the currencyCoefficient to set
     */
    public void setCurrencyCoefficient(double currencyCoefficient) {
        this.currencyCoefficient = currencyCoefficient;
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
