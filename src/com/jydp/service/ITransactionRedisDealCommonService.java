package com.jydp.service;


/**
 * 从数据库拉取 成交记录 到redis
 * @author fk
 *
 */
public interface ITransactionRedisDealCommonService {

    /** 将成交记录放进redis */
    void userDealForRedis();

    /** 每日开盘基准信息重置(昨日收盘价)*/  //今日涨跌,今日最高价,今日最低价,当日成交量(待定)
    void standardMessageForRedis();

    /** 每日凌晨更新最高与最低价更新 */
    void updateWeeHoursBasisOfPrice();
}
