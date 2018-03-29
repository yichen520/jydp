package com.jydp.entity.DO.kgraph;

import java.sql.Timestamp;

/**
 * k线图统计数据
 * @author whx
 */
public class KGraphDO {

    private int currencyId;  //币种Id
    private Timestamp nodeTime;  //时间节点
    private double openPrice;  //开盘价
    private double closPrice;  //收盘价
    private double maxPrice;  //最高价
    private double minPrice;  //最低价
    private double transactionTotal;  //成交量

    private long openClosTime;  //开盘，收盘时间戳

    /**
     *开盘，收盘时间戳
     * @return 开盘，收盘时间戳
     */
    public long getOpenClosTime() {
        return openClosTime;
    }

    /**
     * 开盘，收盘时间戳
     * @param openClosTime
     */
    public void setOpenClosTime(long openClosTime) {
        this.openClosTime = openClosTime;
    }

    /**
     *币种Id
     * @return 币种Id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     *币种Id
     * @param currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     *时间节点
     * @return 时间节点
     */
    public Timestamp getNodeTime() {
        return nodeTime;
    }

    /**
     *时间节点
     * @param nodeTime
     */
    public void setNodeTime(Timestamp nodeTime) {
        this.nodeTime = nodeTime;
    }

    /**
     *开盘价
     * @return 开盘价
     */
    public double getOpenPrice() {
        return openPrice;
    }

    /**
     *开盘价
     * @param openPrice
     */
    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    /**
     *收盘价
     * @return 收盘价
     */
    public double getClosPrice() {
        return closPrice;
    }

    /**
     *收盘价
     * @param closPrice
     */
    public void setClosPrice(double closPrice) {
        this.closPrice = closPrice;
    }

    /**
     *最高价
     * @return 最高价
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     *最高价
     * @param maxPrice
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     *最低价
     * @return 最低价
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     *最低价
     * @param minPrice
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     *成交量
     * @return 成交量
     */
    public double getTransactionTotal() {
        return transactionTotal;
    }

    /**
     *成交量
     * @param transactionTotal
     */
    public void setTransactionTotal(double transactionTotal) {
        this.transactionTotal = transactionTotal;
    }
}
