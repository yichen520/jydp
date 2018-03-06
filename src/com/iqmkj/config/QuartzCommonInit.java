package com.iqmkj.config;

import com.jydp.service.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定时器初始化
 * @author fk
 */
public class QuartzCommonInit {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 定时器公共服务 */
    @Autowired
    private ITransactionRedisDealCommonService transactionRedisDealCommonService;

    /** 从数据库拉取 挂单记录 到redis */
    @Autowired
    private ITransactionPendOrderCommonService ITransactionPendOrderCommonService;

    /** 执行初始化 */
    public void executeInit() {
        //将成交记录放进redis
        transactionRedisDealCommonService.userDealForRedis();
        //从数据库拉取 挂单记录 到redis
        ITransactionPendOrderCommonService.getPendOrder();

    }

}
