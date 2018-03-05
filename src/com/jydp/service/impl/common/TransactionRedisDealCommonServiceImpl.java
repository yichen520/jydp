package com.jydp.service.impl.common;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionRedisDealCommonService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        List<TransactionCurrencyVO> currencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if (currencyList == null || currencyList.isEmpty()) {
            return ;
        }
        for (TransactionCurrencyVO currency: currencyList) {
            List<TransactionDealRedisDO> dealList = transactionDealRedisService.listTransactionDealRedis(50, currency.getCurrencyId());
            if (dealList != null && !dealList.isEmpty()) {
                redisService.addValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currency.getCurrencyShortName(), dealList);
            }
        }
    }

    /** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,当日成交量)*/
    public void standardMessageForRedis() {
        List<TransactionDealPriceDTO> nowTurnover = transactionDealRedisService.getNowTurnover();  //24小时成交量
        //double nowTurnover = transactionDealRedisService.getNowVolumeOfTransaction();  24小时成交额
        if(nowTurnover != null && nowTurnover.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : nowTurnover){
                redisService.addValue(RedisKeyConfig.DAY_TURNOVER + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
            }

        }

        List<TransactionDealPriceDTO> todayHighestPrice = transactionDealRedisService.getTodayHighestPrice();  //今日最高价
        if(todayHighestPrice != null && todayHighestPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : todayHighestPrice){
                redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
            }

        }

        List<TransactionDealPriceDTO> todayLowestPrice = transactionDealRedisService.getTodayLowestPrice();  //今日最低价
        if(todayLowestPrice != null && todayLowestPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : todayLowestPrice){
                redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
            }

        }

        Timestamp nowDate = DateUtil.getCurrentTime();
        List<TransactionDealPriceDTO> nowLastPrice = transactionDealRedisService.getNowLastPrice(nowDate);  //当前价
        if(nowLastPrice != null && nowLastPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : nowLastPrice){
                redisService.addValue(RedisKeyConfig.NOW_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
            }

            //计算涨幅
            long dateLon = DateUtil.lingchenLong();
            Timestamp date;
            //判断当前时间是八点之前
            long nowDateLong = nowDate.getTime() - RedisKeyConfig.OPENING_TIME;
            if(nowDateLong >= dateLon){
                dateLon = dateLon + RedisKeyConfig.OPENING_TIME - 1;
                date = DateUtil.longToTimestamp(dateLon);
            } else {
                dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME - 1;
                date = DateUtil.longToTimestamp(dateLon);

            }

            List<TransactionDealPriceDTO> closingPrice = transactionDealRedisService.getNowLastPrice(date);  //昨天收盘价
            if(closingPrice != null && closingPrice.size() > 0){
                for(TransactionDealPriceDTO transaction : closingPrice){
                    if(transaction.getTransactionPrice() > 0){
                        for(TransactionDealPriceDTO transactionDealPrice : nowLastPrice){
                            if(transactionDealPrice.getCurrencyId() == transaction.getCurrencyId()){
                                double range = BigDecimalUtil.sub(transactionDealPrice.getTransactionPrice(), transaction.getTransactionPrice()) * 100;
                                String rangeStr = BigDecimalUtil.div(range, transaction.getTransactionPrice(), 4);
                                redisService.addValue(RedisKeyConfig.TODAY_RANGE + transaction.getCurrencyShortName(), todayLowestPrice);
                            }
                        }
                    }
                }
            }
        }
    }

    /** 每日开盘更新最高与最低价更新 */
    public void updateWeeHoursBasisOfPrice(){
        Timestamp nowDate = DateUtil.getCurrentTime();
        List<TransactionDealPriceDTO> nowLastPrice = transactionDealRedisService.getNowLastPrice(nowDate);  //昨日收盘价
        if(nowLastPrice != null && nowLastPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : nowLastPrice){
                redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
                redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
            }

        }
    }

}
