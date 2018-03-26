package com.iqmkj.config;

import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionRedisDealCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import com.jydp.service.*;
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

    /** web端首页 */
    @Autowired
    private IHomePageRedisService homePageRedisService;

    /** 执行初始化 */
    public void executeInit() {
        //将成交记录放进redis
        transactionRedisDealCommonService.userDealForRedis();
        //每日凌晨更新最高与最低价更新
        transactionRedisDealCommonService.updateWeeHoursBasisOfPrice();
        //从数据库拉取 挂单记录 到redis
        ITransactionPendOrderCommonService.getPendOrder();
        //从数据库拉取首页数据到redis
        homePageRedisService.getHomePageData();
        //k线图参数存入redis
        transactionRedisDealCommonService.graphDataForRedis();
        //启动刷新是否需要上市指导价
        transactionRedisDealCommonService.gruidPriceForYesterdayPrice();
        //启动统计交易
        transactionRedisDealCommonService.exeStatistics();
    }

}
