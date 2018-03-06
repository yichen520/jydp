package com.jydp.service.impl.common;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
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
        for (TransactionCurrencyDO currency: currencyList) {
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
                redisService.addValue(RedisKeyConfig.DAY_TURNOVER + transactionDealPrice.getCurrencyId(),
                        transactionDealPrice.getTransactionPrice());
            }

        }

        List<TransactionDealPriceDTO> todayHighestPrice = transactionDealRedisService.getTodayHighestPrice();  //今日最高价
        if(todayHighestPrice != null && todayHighestPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : todayHighestPrice){
                redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionDealPrice.getCurrencyId(),
                        transactionDealPrice.getTransactionPrice());
            }

        }

        List<TransactionDealPriceDTO> todayLowestPrice = transactionDealRedisService.getTodayLowestPrice();  //今日最低价
        if(todayLowestPrice != null && todayLowestPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : todayLowestPrice){
                redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionDealPrice.getCurrencyId(),
                        transactionDealPrice.getTransactionPrice());
            }

        }

        Timestamp nowDate = DateUtil.getCurrentTime();
        List<TransactionDealPriceDTO> nowLastPrice = transactionDealRedisService.getNowLastPrice(nowDate);  //当前价
        if(nowLastPrice != null && nowLastPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : nowLastPrice){
                redisService.addValue(RedisKeyConfig.NOW_PRICE + transactionDealPrice.getCurrencyId(),
                        transactionDealPrice.getTransactionPrice());
            }

            //涨跌幅度计算
            for(TransactionDealPriceDTO transactionDealPrice : nowLastPrice){
                if(redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + transactionDealPrice.getCurrencyId()) != null){
                    double transactionPrice = (double) redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + transactionDealPrice.getCurrencyId());
                    double range = BigDecimalUtil.sub(transactionDealPrice.getTransactionPrice(), transactionPrice * 100);
                    String rangeStr = BigDecimalUtil.div(range, transactionPrice, 4);
                    redisService.addValue(RedisKeyConfig.TODAY_RANGE + transactionDealPrice.getCurrencyId(), rangeStr);
                }
            }
        }
    }

    /** 每日开盘基准信息重置(今日涨跌,今日最高价,今日最低价,昨日收盘价)*/
    public void updateWeeHoursBasisOfPrice(){
        Timestamp date;
        //最高最低价取昨日收盘价
/*        Timestamp nowDate = DateUtil.getCurrentTime();
        List<TransactionDealPriceDTO> nowLastPrice = transactionDealRedisService.getNowLastPrice(nowDate);  //昨日收盘价
        if(nowLastPrice != null && nowLastPrice.size() > 0){
            for(TransactionDealPriceDTO transactionDealPrice : nowLastPrice){
                redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
                redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionDealPrice.getCurrencyShortName(),
                        transactionDealPrice.getTransactionPrice());
            }

        }*/

        Timestamp getDate = DateUtil.getCurrentTime();
        List<TransactionDealPriceDTO> closing = transactionDealRedisService.getNowLastPrice(getDate);
        //判断当前时间是否是凌晨至开盘之前
        long dateLon = DateUtil.lingchenLong();
        long nowDate = getDate.getTime() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            //八点至凌晨
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
        } else {
            //凌晨至八点
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
        }

        //昨日收盘价计算
        dateLon = dateLon - 1;
        date = DateUtil.longToTimestamp(dateLon);
        List<TransactionDealPriceDTO> closingPrice = transactionDealRedisService.getNowLastPrice(date);  //昨天收盘价
        if(closingPrice != null && closingPrice.size() > 0) {
            for (TransactionDealPriceDTO transaction : closingPrice) {
                redisService.addValue(RedisKeyConfig.YESTERDAY_PRICE + transaction.getCurrencyId(),
                        transaction.getTransactionPrice());
            }
        }

        //获取币种信息
        List<TransactionCurrencyVO> transactionUserDeal= transactionCurrencyService.getTransactionCurrencyListForWeb();
        if(transactionUserDeal != null && transactionUserDeal.size() > 0) {
            if(closing == null || closing.size() <= 0){
                for(TransactionCurrencyDO transactionUser : transactionUserDeal){
                    redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionUser.getCurrencyId(), 0);
                    redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionUser.getCurrencyId(), 0);
                    redisService.addValue(RedisKeyConfig.TODAY_RANGE + transactionUser.getCurrencyId(), "0");
                }
            } else {
                for (TransactionCurrencyDO transactionUser : transactionUserDeal) {
                    for(TransactionDealPriceDTO transactionDealPrice : closing){
                        if (transactionUser.getCurrencyId() != transactionDealPrice.getCurrencyId()) {
                            redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionUser.getCurrencyId(), 0);
                            redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionUser.getCurrencyId(), 0);
                            redisService.addValue(RedisKeyConfig.TODAY_RANGE + transactionUser.getCurrencyId(), "0");
                        }
                    }
                }
            }
        }
    }
}
