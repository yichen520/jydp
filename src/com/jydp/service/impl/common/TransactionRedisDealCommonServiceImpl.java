package com.jydp.service.impl.common;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionRedisDealCommonService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 从数据库拉取 成交记录 到redis
 * @author fk
 *
 */
@Service("transactionRedisDealCommonService")
public class TransactionRedisDealCommonServiceImpl implements ITransactionRedisDealCommonService{

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 将成交记录放进redis */
    @Override
    public void userDealForRedis() {
        List<TransactionCurrencyDO> currencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if (currencyList == null || currencyList.isEmpty()) {
            return ;
        }
        for (TransactionCurrencyDO currency: currencyList) {
            List<TransactionDealRedisDO> dealList = transactionDealRedisService.listTransactionDealRedis(50, currency.getCurrencyId());
            if (dealList != null && !dealList.isEmpty()) {
                redisService.addValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currency.getCurrencyShortName(), dealList);
            }
        }
    }

}
