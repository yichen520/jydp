package com.iqmkj.config;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.*;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    /** web端首页 */
    @Autowired
    private IHomePageRedisService homePageRedisService;

    /** 执行初始化 */
    public void executeInit() {
        transactionRedisDealCommonService.userDealForRedis();
        transactionRedisDealCommonService.updateWeeHoursBasisOfPrice();
        homePageRedisService.getHomePageData();
    }

}
