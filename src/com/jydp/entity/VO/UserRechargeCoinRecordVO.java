package com.jydp.entity.VO;

import java.sql.Timestamp;

/**
 * Description: 用户充币记录
 * Author: hht
 * Date: 2018-04-16 11:28
 */
public class UserRechargeCoinRecordVO {

    private String walletOrderNo; //钱包订单号
    private String currencyName;//货币名称
    private double currencyNumber;//币种数量
    private Timestamp orderTime;//订单时间
    private String remark;//备注

    /**
     * 钱包订单号
     *
     * @return the wallet order no
     */
    public String getWalletOrderNo() {
        return walletOrderNo;
    }

    /**
     * 钱包订单号
     *
     * @param walletOrderNo the wallet order no
     */
    public void setWalletOrderNo(String walletOrderNo) {
        this.walletOrderNo = walletOrderNo;
    }

    /**
     * 货币名称
     *
     * @return the currency name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     *
     * @param currencyName the currency name
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 币种数量
     *
     * @return the currency number
     */
    public double getCurrencyNumber() {
        return currencyNumber;
    }

    /**
     * 币种数量
     *
     * @param currencyNumber the currency number
     */
    public void setCurrencyNumber(double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    /**
     * 订单时间
     *
     * @return the order time
     */
    public Timestamp getOrderTime() {
        return orderTime;
    }

    /**
     * 订单时间
     *
     * @param orderTime the order time
     */
    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 备注
     *
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
