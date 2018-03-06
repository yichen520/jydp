package com.jydp.service;

/**
 *  web端首页Redis服务
 *  @author yk
 */
public interface IHomePageRedisService {

    /**
     * 从数据库查询所有首页数据存储到redis中(除币种行情外)
     */
    void getHomePageData();

    /**
     * 从数据库查询所有币种行情信息存储到redis中
     */
    void getCurrencyMarketData();
}
