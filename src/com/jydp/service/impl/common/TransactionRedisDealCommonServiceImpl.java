package com.jydp.service.impl.common;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
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
import java.util.*;

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
            redisService.addValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currency.getCurrencyId(), dealList);
        }
    }

    /** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,当日成交量)*/
    public void standardMessageForRedis() {
        List<TransactionDealPriceDTO> nowTurnover = transactionDealRedisService.getNowTurnover();  //今日成交量
        //double nowTurnover = transactionDealRedisService.getNowVolumeOfTransaction();  今日成交额
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
                Object yesterdayPriceStr = redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + transactionDealPrice.getCurrencyId());
                if(yesterdayPriceStr != null){
                    double transactionPrice = Double.parseDouble(yesterdayPriceStr.toString());
                    double range = BigDecimalUtil.sub(transactionDealPrice.getTransactionPrice(), transactionPrice) * 100;
                    String rangeStr = BigDecimalUtil.div(range, transactionPrice, 2);
                    if(!StringUtil.isNotNull(rangeStr)){
                        rangeStr = "0.0";
                    }
                    redisService.addValue(RedisKeyConfig.TODAY_RANGE + transactionDealPrice.getCurrencyId(), Double.parseDouble(rangeStr));
                }
            }
        }
    }

    /** 每日开盘基准信息重置(昨日收盘价)*/  //今日涨跌,今日最高价,今日最低价,当日成交量(待定)
    public void updateWeeHoursBasisOfPrice(){
        Timestamp date;
        double quantity = 0;

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
    }

    /** 刷新交易指导价(昨日收盘价) */
    public void gruidPriceForYesterdayPrice(){
        //判断当前时间是否是凌晨至开盘之前
        long dateLon = DateUtil.lingchenLong();
        long nowDate = DateUtil.getCurrentTime().getTime() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            //八点至凌晨
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
        } else {
            //凌晨至八点
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
        }

        //昨日收盘价计算
        dateLon = dateLon - 1;
        Timestamp date = DateUtil.longToTimestamp(dateLon);

        List<TransactionDealPriceDTO> nowLastPrice = transactionDealRedisService.getNowLastPrice(date);
        List<TransactionCurrencyDO> transactionCurrencyDOS = transactionCurrencyService.listTransactionCurrencyAll();
        if (nowLastPrice != null && !nowLastPrice.isEmpty()  && transactionCurrencyDOS != null && !transactionCurrencyDOS.isEmpty()) {
            List<Integer> lowInt = new ArrayList<>();
            List<Integer> powInt = new ArrayList<>();
            Map<Integer, Double> map = new HashMap<>();

            for (TransactionDealPriceDTO currDTO: nowLastPrice) {
                lowInt.add(currDTO.getCurrencyId());
            }
            for (TransactionCurrencyDO curr: transactionCurrencyDOS) {
                powInt.add(curr.getCurrencyId());
                map.put(curr.getCurrencyId(), curr.getGuidancePrice());
            }

            powInt.removeAll(lowInt);

            for (Integer cuId: powInt) {
                Double guidPrice = map.get(cuId);
                redisService.addValue(RedisKeyConfig.YESTERDAY_PRICE + cuId, guidPrice);
            }
        }
    }
}
