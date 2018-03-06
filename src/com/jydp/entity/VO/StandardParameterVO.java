package com.jydp.entity.VO;

/**
 * 交易大盘基准信息
 *
 * @author sy
 */
public class StandardParameterVO {
    private double nowPrice; //当前成交单价
    private double buyOne; //买一价
    private double sellOne; //卖一价
    private double todayMax; //今日最高价
    private double todayMin; //今日最低价
    private double todayRange; //今日涨幅
    private double yesterdayPrice; //昨日收盘价
    private double dayTurnove; //24小时成交量

    /**
     * 今日涨幅
     *
     * @return the today range
     */
    public double getTodayRange() {
        return todayRange;
    }

    /**
     * 今日涨幅
     *
     * @param todayRange the today range
     */
    public void setTodayRange(double todayRange) {
        this.todayRange = todayRange;
    }

    /**
     * 当前成交单价
     *
     * @return the now price
     */
    public double getNowPrice() {
        return nowPrice;
    }

    /**
     * 当前成交单价
     *
     * @param nowPrice the now price
     */
    public void setNowPrice(double nowPrice) {
        this.nowPrice = nowPrice;
    }

    /**
     * 买一价
     *
     * @return the buy one
     */
    public double getBuyOne() {
        return buyOne;
    }

    /**
     * 买一价
     *
     * @param buyOne the buy one
     */
    public void setBuyOne(double buyOne) {
        this.buyOne = buyOne;
    }

    /**
     * 卖一价
     *
     * @return the sell one
     */
    public double getSellOne() {
        return sellOne;
    }

    /**
     * 卖一价
     *
     * @param sellOne the sell one
     */
    public void setSellOne(double sellOne) {
        this.sellOne = sellOne;
    }

    /**
     * 今日最高价
     *
     * @return the today max
     */
    public double getTodayMax() {
        return todayMax;
    }

    /**
     * 今日最高价
     *
     * @param todayMax the today max
     */
    public void setTodayMax(double todayMax) {
        this.todayMax = todayMax;
    }

    /**
     * 今日最低价
     *
     * @return the today min
     */
    public double getTodayMin() {
        return todayMin;
    }

    /**
     * 今日最低价
     *
     * @param todayMin the today min
     */
    public void setTodayMin(double todayMin) {
        this.todayMin = todayMin;
    }

    /**
     * 昨日收盘价
     *
     * @return the yesterday price
     */
    public double getYesterdayPrice() {
        return yesterdayPrice;
    }

    /**
     * 昨日收盘价
     *
     * @param yesterdayPrice the yesterday price
     */
    public void setYesterdayPrice(double yesterdayPrice) {
        this.yesterdayPrice = yesterdayPrice;
    }

    /**
     * 24小时成交量
     *
     * @return the day turnove
     */
    public double getDayTurnove() {
        return dayTurnove;
    }

    /**
     * 24小时成交量
     *
     * @param dayTurnove the day turnove
     */
    public void setDayTurnove(double dayTurnove) {
        this.dayTurnove = dayTurnove;
    }
}
