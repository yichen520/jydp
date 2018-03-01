package com.jydp.quartz;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 交易中心成交记录 定时从数据库拉取到redis
 * @author fk
 */
@Component
public class TransactionDealRedisTimer {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 成交记录  每5秒刷新一次 */
    @Scheduled(cron="0/5 * *  * * ? ")
    public void refresh(){

        List<TransactionCurrencyDO> currencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if (currencyList == null || currencyList.isEmpty()) {
            return ;
        }
        for (TransactionCurrencyDO currency: currencyList) {
            List<TransactionDealRedisDO> dealList = transactionDealRedisService.listTransactionDealRedis(50, currency.getCurrencyId());
            if (dealList != null && !dealList.isEmpty()) {
                redisService.addList(RedisKeyConfig.CURRENCY_DEAL_KEY + currency.getCurrencyShortName(), dealList);
            }
        }
    }

}
