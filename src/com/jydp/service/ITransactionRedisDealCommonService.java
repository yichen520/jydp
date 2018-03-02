package com.jydp.service;

import org.springframework.stereotype.Service;

/**
 * 从数据库拉取 成交记录 到redis
 * @author fk
 *
 */
public interface ITransactionRedisDealCommonService {

    /** 将成交记录放进redis */
    void userDealForRedis();

    /** 组装基准信息参数并存入redis */
    void standardMessageForRedis();
}
