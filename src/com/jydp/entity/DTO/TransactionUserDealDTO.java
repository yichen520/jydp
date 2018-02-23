package com.jydp.entity.DTO;

/**
 * 成交记录数据传输类
 * @Author yk
 */
public class TransactionUserDealDTO {
    private int currencyId; //币种Id
    private String currencyName;//货币名称
    private String currencyShortName;//货币简称
    private double latestPrice; //最新成交价
    private double buyOnePrice; //买一价
    private double sellOnePrice; //卖一价
    private double volume; //24小时成交量
    private double Change; //24小时涨跌

    /**
     * 获取 币种Id
     * @return 币种Id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 设置 币种Id
     * @param currencyId 币种Id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 获取 货币名称
     * @return 货币名称
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 设置 货币名称
     * @param currencyName 货币名称
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 获取 货币简称
     * @return 货币简称
     */
    public String getCurrencyShortName() {
        return currencyShortName;
    }

    /**
     * 设置 货币简称
     * @param currencyShortName 货币简称
     */
    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }

    /**
     * 获取 最新成交价
     * @return 最新成交价
     */
    public double getLatestPrice() {
        return latestPrice;
    }

    /**
     * 设置 最新成交价
     * @param latestPrice 最新成交价
     */
    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    /**
     * 获取 买一价
     * @return 买一价
     */
    public double getBuyOnePrice() {
        return buyOnePrice;
    }

    /**
     * 设置 买一价
     * @param buyOnePrice 买一价
     */
    public void setBuyOnePrice(double buyOnePrice) {
        this.buyOnePrice = buyOnePrice;
    }

    /**
     * 获取 卖一价
     * @return 卖一价
     */
    public double getSellOnePrice() {
        return sellOnePrice;
    }

    /**
     * 设置 卖一价
     * @param sellOnePrice 卖一价
     */
    public void setSellOnePrice(double sellOnePrice) {
        this.sellOnePrice = sellOnePrice;
    }

    /**
     * 获取 24小时成交量
     * @return 24小时成交量
     */
    public double getVolume() {
        return volume;
    }

    /**
     * 设置 24小时成交量
     * @param volume 24小时成交量
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * 获取 24小时涨跌
     * @return 24小时涨跌
     */
    public double getChange() {
        return Change;
    }

    /**
     * 设置 24小时涨跌
     * @param change 24小时涨跌
     */
    public void setChange(double change) {
        Change = change;
    }
}
