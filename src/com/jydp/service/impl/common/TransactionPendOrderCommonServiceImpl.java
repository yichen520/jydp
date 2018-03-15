package com.jydp.service.impl.common;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderCommonService;
import com.jydp.service.ITransactionPendOrderService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 从数据库拉取 挂单记录 到redis
 * @author hz
 *
 */
@Service("transactionPendOrderCommonService")
public class TransactionPendOrderCommonServiceImpl implements ITransactionPendOrderCommonService{

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**
     * 从数据库拉取 挂单记录,买一价，卖一价 到redis
     */
    public void getPendOrder(){
        //获取所有币种
        List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if(transactionCurrencyList == null){
            return;
        }

        for (TransactionCurrencyDO transactionCurrency: transactionCurrencyList) {
            //买入记录
            List<TransactionPendOrderDTO> transactionPendOrderBuyList =
                    transactionPendOrderService.listLatestRecords(1,transactionCurrency.getCurrencyId(),15);

            //设置买入挂单记录key
            String buyKey = RedisKeyConfig.BUY_KEY + transactionCurrency.getCurrencyId();
            //设置买一价key
            String buyOneKey = RedisKeyConfig.BUY_ONE_KEY + transactionCurrency.getCurrencyId();

            if(transactionPendOrderBuyList.size() > 0){
                //插入最新挂单记录
                redisService.addValue(buyKey,transactionPendOrderBuyList);
                //插入最新买一价
                redisService.addValue(buyOneKey,transactionPendOrderBuyList.get(0).getPendingPrice());
            }else if(transactionPendOrderBuyList.size() == 0){
                //查无数据都存null
                redisService.addValue(buyKey,null);
                redisService.addValue(buyOneKey,null);
            }

            //卖出记录
            List<TransactionPendOrderDTO> transactionPendOrderSellList =
                    transactionPendOrderService.listLatestRecords(2,transactionCurrency.getCurrencyId(),15);

            //设置卖出挂单记录key
            String sellKey = RedisKeyConfig.SELL_KEY + transactionCurrency.getCurrencyId();
            //设置卖一价key
            String sellOneKey = RedisKeyConfig.SELL_ONE_KEY + transactionCurrency.getCurrencyId();

            if(transactionPendOrderSellList.size() > 0){
                //插入最新数据
                redisService.addValue(sellKey,transactionPendOrderSellList);
                //插入最新卖一价
                redisService.addValue(sellOneKey,transactionPendOrderSellList.get(0).getPendingPrice());
            }else if(transactionPendOrderSellList.size() == 0){
                //查无数据都存null
                redisService.addValue(sellKey,null);
                redisService.addValue(sellOneKey,null);
            }
        }
    }

}
