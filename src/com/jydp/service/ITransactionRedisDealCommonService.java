package com.jydp.service;


import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;

import java.sql.Timestamp;

/**
 * 从数据库拉取 成交记录 到redis
 * @author fk
 *
 */
public interface ITransactionRedisDealCommonService {

    /** 将成交记录放进redis */
    void userDealForRedis();

    /** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,当日成交量)*/  //今日涨跌,今日最高价,今日最低价,当日成交量(待定)
    void standardMessageForRedis();

    /** 每日开盘基准信息重置(昨日收盘价) */
    void updateWeeHoursBasisOfPrice();

    /** k线图参数存入redis(时间节点：5分钟 5m、15分钟 15m、30分钟 30m、1小时 1h、4小时 4h、1天 1d 、1周 1w) */
    void graphDataForRedis();

    /** 刷新交易指导价(昨日收盘价) */
    void gruidPriceForYesterdayPrice();

    /** 每日交易统计 (注：该方法暂时停用)*/
    boolean statistics();

    /**
     * 后台做单执行统计每天总成交量，总成交金额（定时器执行，系统初始化）
     * @return 执行成功：返回true，执行失败：返回false
     */
    boolean exeStatistics();

    /**
     * 后台做单统计每天总成交量，总成交金额（专用接口，禁止其他接口调用）
     * @param currencyId 币种id
     * @param toDayDawnLong 当日凌晨时间戳
     * @param curTime 当前时间
     * @param statisticsTimeLong 统计时间戳
     * @param transactionCurrencyCoefficient 币种系数
     * @param ordernNoPrefix 记录号前缀
     * @return 执行成功：返回true，执行失败：返回false
     */
    boolean statistics(int currencyId, long toDayDawnLong, Timestamp curTime, long statisticsTimeLong,
                       TransactionCurrencyCoefficientDO transactionCurrencyCoefficient, String ordernNoPrefix);

}
