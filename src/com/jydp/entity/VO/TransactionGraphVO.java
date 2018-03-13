package com.jydp.entity.VO;

import java.sql.Timestamp;

/**
 * 交易中心k线图相关数据
 *
 * @author sy
 */
public class TransactionGraphVO {
    private double openPrice; //开盘价
    private double closPrice; //收盘价
    private double maxPrice; //最高价
    private double minPrice; //最低价
    private double countPrice; //成交量
    private Timestamp dealDate;  //交易时间

    /**
     * 开盘价
     *
     * @return the open price
     */
    public double getOpenPrice() {
        return openPrice;
    }

    /**
     * 开盘价
     *
     * @param openPrice the open price
     */
    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    /**
     * 收盘价
     *
     * @return the clos price
     */
    public double getClosPrice() {
        return closPrice;
    }

    /**
     * 收盘价
     *
     * @param closPrice the clos price
     */
    public void setClosPrice(double closPrice) {
        this.closPrice = closPrice;
    }

    /**
     * 最高价
     *
     * @return the max price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * 最高价
     *
     * @param maxPrice the max price
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * 最低价
     *
     * @return the min price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * 最低价
     *
     * @param minPrice the min price
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * 成交量
     *
     * @return the count price
     */
    public double getCountPrice() {
        return countPrice;
    }

    /**
     * 成交量
     *
     * @param countPrice the count price
     */
    public void setCountPrice(double countPrice) {
        this.countPrice = countPrice;
    }

    /**
     * 交易时间
     *
     * @return the deal date
     */
    public Timestamp getDealDate() {
        return dealDate;
    }

    /**
     * 交易时间
     *
     * @param dealDate the deal date
     */
    public void setDealDate(Timestamp dealDate) {
        this.dealDate = dealDate;
    }
}
