package com.jydp.service.impl.common;

import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.*;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

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
            int currencyId = transactionCurrency.getCurrencyId();
            //买入记录
            List<TransactionPendOrderDTO> transactionPendOrderBuyList =
                    transactionPendOrderService.listLatestRecords(1, currencyId,15);

            //未来做单记录
            Timestamp curTime = DateUtil.getCurrentTime();
            List<TransactionDealRedisDO> transactionDealBuyRedisList =
                    transactionDealRedisService.listTransactionDealForPending(2, currencyId, curTime,15);
            transactionDealBuyRedisList.removeAll(Collections.singleton(null)); //移除所有的null元素

            //组装数据
            if(transactionDealBuyRedisList.size() > 0 && transactionDealBuyRedisList != null){
                List<TransactionDealRedisDO> removeList = new ArrayList<>();
                for (TransactionDealRedisDO transactionDealRedisDO: transactionDealBuyRedisList) {
                    for (TransactionPendOrderDTO transactionPendOrderDTO: transactionPendOrderBuyList) {
                        if(transactionDealRedisDO.getTransactionPrice() == transactionPendOrderDTO.getPendingPrice()){
                            transactionPendOrderDTO.setRestNumber(transactionPendOrderDTO.getRestNumber() + transactionDealRedisDO.getCurrencyNumber());
                            removeList.add(transactionDealRedisDO);
                            break;
                        }
                    }
                }
                //移除已经加过的
                transactionDealBuyRedisList.removeAll(removeList);

                if(transactionDealBuyRedisList.size() > 0 && transactionDealBuyRedisList != null){
                    for (TransactionDealRedisDO transactionDealRedis: transactionDealBuyRedisList) {
                                TransactionPendOrderDTO transactionPendOrderDTO = new TransactionPendOrderDTO();
                                transactionPendOrderDTO.setPendingPrice(transactionDealRedis.getTransactionPrice());
                                transactionPendOrderDTO.setRestNumber(transactionDealRedis.getCurrencyNumber());
                                transactionPendOrderDTO.setSumPrice(transactionDealRedis.getCurrencyTotalPrice());

                                transactionPendOrderBuyList.add(transactionPendOrderDTO);
                    }
                }

                //按价格排序并返回前15条记录
                transactionPendOrderBuyList = sortListDESC(transactionPendOrderBuyList);
            }

            //设置买入挂单记录key
            String buyKey = RedisKeyConfig.BUY_KEY + currencyId;
            //设置买一价key
            String buyOneKey = RedisKeyConfig.BUY_ONE_KEY + currencyId;

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
                    transactionPendOrderService.listLatestRecords(2,currencyId,15);

            //未来做单
            List<TransactionDealRedisDO> transactionDealRedisSellList =
                    transactionDealRedisService.listTransactionDealForPending(1, currencyId, curTime,15);
            transactionDealRedisSellList.removeAll(Collections.singleton(null)); //移除所有的null元素

            //组装数据
            if(transactionDealRedisSellList.size() > 0 && transactionDealRedisSellList != null){
                List<TransactionDealRedisDO> removeList = new ArrayList<>();
                for (TransactionDealRedisDO transactionDealRedisDO: transactionDealRedisSellList) {
                    for (TransactionPendOrderDTO transactionPendOrderDTO: transactionPendOrderSellList) {
                        if(transactionDealRedisDO.getTransactionPrice() == transactionPendOrderDTO.getPendingPrice()){
                            transactionPendOrderDTO.setRestNumber(transactionPendOrderDTO.getRestNumber() + transactionDealRedisDO.getCurrencyNumber());
                            removeList.add(transactionDealRedisDO);
                            break;
                        }
                    }
                }
                //移除已经加过的
                transactionDealRedisSellList.removeAll(removeList);

                if(transactionDealBuyRedisList.size() > 0 && transactionDealBuyRedisList != null){
                    for (TransactionDealRedisDO transactionDealRedis: transactionDealRedisSellList) {
                        TransactionPendOrderDTO transactionPendOrderDTO = new TransactionPendOrderDTO();
                        transactionPendOrderDTO.setPendingPrice(transactionDealRedis.getTransactionPrice());
                        transactionPendOrderDTO.setRestNumber(transactionDealRedis.getCurrencyNumber());
                        transactionPendOrderDTO.setSumPrice(transactionDealRedis.getCurrencyTotalPrice());

                        transactionPendOrderSellList.add(transactionPendOrderDTO);
                    }
                }

                //按价格排序并返回前15条记录
                transactionPendOrderSellList = sortListASC(transactionPendOrderSellList);
            }

            //设置卖出挂单记录key
            String sellKey = RedisKeyConfig.SELL_KEY + currencyId;
            //设置卖一价key
            String sellOneKey = RedisKeyConfig.SELL_ONE_KEY + currencyId;

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

    /**
     * 按价格排序并返回前15条记录(倒序)
     * @param list 需要排序的list
     * @return 排序好的list
     */
    public List<TransactionPendOrderDTO> sortListDESC(List<TransactionPendOrderDTO> list){
        Collections.sort(list, new Comparator<TransactionPendOrderDTO>() {
            public int compare(TransactionPendOrderDTO arg0, TransactionPendOrderDTO arg1) {
                double price0 = arg0.getPendingPrice();
                double price1 = arg1.getPendingPrice();
                if (price1 > price0) {
                    return 1;
                } else if (price1 == price0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        if(list.size() <= 15){
            return list;
        }else {
            return list.subList(0,14);
        }
    }

    /**
     * 按价格排序并返回前15条记录(正序)
     * @param list 需要排序的list
     * @return 排序好的list
     */
    public List<TransactionPendOrderDTO> sortListASC(List<TransactionPendOrderDTO> list){
        Collections.sort(list, new Comparator<TransactionPendOrderDTO>() {
            public int compare(TransactionPendOrderDTO arg0, TransactionPendOrderDTO arg1) {
                double price0 = arg0.getPendingPrice();
                double price1 = arg1.getPendingPrice();
                if (price1 < price0) {
                    return 1;
                } else if (price1 == price0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        if(list.size() <= 15){
            return list;
        }else {
            return list.subList(0,14);
        }
    }

}
