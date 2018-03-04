package com.jydp.service;


/**
 * 从数据库拉取 成交记录 到redis
 * @author fk
 *
 */
public interface ITransactionRedisDealCommonService {

    /** 将成交记录放进redis */
    void userDealForRedis();

    /** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,24小时成交量)*/
    void standardMessageForRedis();

    /** 每日凌晨更新最高与最低价更新 */
    void updateWeeHoursBasisOfPrice();
}
