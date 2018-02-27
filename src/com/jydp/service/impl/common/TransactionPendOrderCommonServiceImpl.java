package com.jydp.service.impl.common;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderCommonService;
import com.jydp.service.ITransactionPendOrderService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 从数据库拉取 挂单记录 到redis
 * @author hz
 *
 */
@Service("transactionPendOrderService")
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
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if(transactionCurrencyList.isEmpty()){
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

            List<Object> setBuyList = new ArrayList<>();
            if(transactionPendOrderBuyList.size() > 0){
                for (TransactionPendOrderDTO transactionPendOrder: transactionPendOrderBuyList
                        ) {
                    setBuyList.add(transactionPendOrder);
                }
            }

            if(setBuyList.size() == transactionPendOrderBuyList.size() && setBuyList.size() > 0){
                //删除挂单记录key
                redisService.deleteValue(buyKey);
                //插入最新挂单记录
                redisService.addList(buyKey,setBuyList);
                //删除买一价key
                redisService.deleteValue(buyOneKey);
                //插入最新买一价
                redisService.addValue(buyOneKey,transactionPendOrderBuyList.get(0).getPendingPrice());
            }

            //卖出记录
            List<TransactionPendOrderDTO> transactionPendOrderSellList =
                    transactionPendOrderService.listLatestRecords(2,transactionCurrency.getCurrencyId(),15);

            //设置卖出挂单记录key
            String sellKey = RedisKeyConfig.SELL_KEY + transactionCurrency.getCurrencyId();
            //设置卖一价key
            String sellOneKey = RedisKeyConfig.SELL_ONE_KEY + transactionCurrency.getCurrencyId();

            List<Object> setSellList = new ArrayList<>();
            if(transactionPendOrderSellList.size() > 0){
                for (TransactionPendOrderDTO transactionPendOrder: transactionPendOrderSellList
                        ) {
                    setSellList.add(transactionPendOrder);
                }
            }

            if(setSellList.size() == transactionPendOrderSellList.size() && setSellList.size() > 0){
                //清空该key
                redisService.deleteValue(sellKey);
                //插入最新数据
                redisService.addList(sellKey,setSellList);
                //删除卖一价key
                redisService.deleteValue(sellOneKey);
                //插入最新卖一价
                redisService.addValue(sellOneKey,transactionPendOrderSellList.get(0).getPendingPrice());
            }
        }
    }

}
