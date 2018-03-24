package com.jydp.entity.DO.transfer;

import java.sql.Timestamp;

/**
 * JYDP币种转出管理
 * @Author: wqq
 */
public class JydpCoinConfigDO {

    private String recordNo; //币种转出管理记录号 业务类型（2）+日期（6）+随机位（7）
    private int currencyId; //币种Id
    private String currencyName; //币种名称
    private double freeCurrencyNumber; //免审数量
    private double minCurrencyNumber; //最低数量
    private String ipAddress; //操作时的ip地址
    private int backerId; //后台管理员Id
    private String backerAccount; //后台管理员帐号
    private String remark; //备注
    private int status; //状态 1:正常, 2:已删除
    private Timestamp addTime; //添加时间

    /**
     * 币种转出管理记录号 业务类型（2）+日期（6）+随机位（7）
     * @return the recordNo
     */
    public String getRecordNo() {
        return recordNo;
    }

    /**
     * 币种转出管理记录号 业务类型（2）+日期（6）+随机位（7）
     * @param recordNo the recordNo to set
     */
    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
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
     * 币种名称
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 币种名称
     * @param currencyName the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 免审数量
     * @return the freeCurrencyNumber
     */
    public double getFreeCurrencyNumber() {
        return freeCurrencyNumber;
    }

    /**
     * 免审数量
     * @param freeCurrencyNumber the freeCurrencyNumber to set
     */
    public void setFreeCurrencyNumber(double freeCurrencyNumber) {
        this.freeCurrencyNumber = freeCurrencyNumber;
    }

    /**
     * 最低数量
     * @return the minCurrencyNumber
     */
    public double getMinCurrencyNumber() {
        return minCurrencyNumber;
    }

    /**
     * 最低数量
     * @param minCurrencyNumber the minCurrencyNumber to set
     */
    public void setMinCurrencyNumber(double minCurrencyNumber) {
        this.minCurrencyNumber = minCurrencyNumber;
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
     * 后台管理员Id
     * @return the backerId
     */
    public int getBackerId() {
        return backerId;
    }

    /**
     * 后台管理员Id
     * @param backerId the backerId to set
     */
    public void setBackerId(int backerId) {
        this.backerId = backerId;
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
     * 状态 1:正常, 2:已删除
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * 状态 1:正常, 2:已删除
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
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
