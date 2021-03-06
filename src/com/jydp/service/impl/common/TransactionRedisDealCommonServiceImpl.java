package com.jydp.service.impl.common;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionStatisticsDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.DTO.TransactionCurrencyDealPriceDTO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.TransactionGraphVO;
import com.jydp.service.*;
import config.RedisKeyConfig;
import config.SystemCommonConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

    /** 交易统计 */
    @Autowired
    private ITransactionStatisticsService transactionStatisticsService;

    /** 币种系数 */
    @Autowired
    private ITransactionCurrencyCoefficientService transactionCurrencyCoefficientService;

    /** 将成交记录放进redis */
    @Override
    public void userDealForRedis() {
        List<Integer> currencyIdList = transactionCurrencyService.listcurrencyId();
        if (currencyIdList == null || currencyIdList.isEmpty()) {
            return ;
        }
        for(Integer currencyId : currencyIdList){
            List<TransactionDealRedisDO> dealList = transactionDealRedisService.listTransactionDealRedis(50, currencyId);
            if (dealList != null && !dealList.isEmpty()) {
                redisService.addValue(RedisKeyConfig.NOW_PRICE + currencyId, dealList.get(0).getTransactionPrice());
            }
            redisService.addValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currencyId, dealList);
        }
    }

    /** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,当日成交量)*/
//    public void standardMessageForRedis() {
//        List<TransactionDealPriceDTO> nowTurnover = transactionDealRedisService.getNowTurnover();  //今日成交量
//        //double nowTurnover = transactionDealRedisService.getNowVolumeOfTransaction();  今日成交额
//        if(nowTurnover != null && nowTurnover.size() > 0){
//            for(TransactionDealPriceDTO transactionDealPrice : nowTurnover){
//                redisService.addValue(RedisKeyConfig.DAY_TURNOVER + transactionDealPrice.getCurrencyId(),
//                        transactionDealPrice.getTransactionPrice());
//            }
//
//        }
//
//        List<TransactionDealPriceDTO> todayHighestPrice = transactionDealRedisService.getTodayHighestPrice();  //今日最高价
//        if(todayHighestPrice != null && todayHighestPrice.size() > 0){
//            for(TransactionDealPriceDTO transactionDealPrice : todayHighestPrice){
//                redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionDealPrice.getCurrencyId(),
//                        transactionDealPrice.getTransactionPrice());
//            }
//
//        }
//
//        List<TransactionDealPriceDTO> todayLowestPrice = transactionDealRedisService.getTodayLowestPrice();  //今日最低价
//        if(todayLowestPrice != null && todayLowestPrice.size() > 0){
//            for(TransactionDealPriceDTO transactionDealPrice : todayLowestPrice){
//                redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionDealPrice.getCurrencyId(),
//                        transactionDealPrice.getTransactionPrice());
//            }
//
//        }
//
//        //获取币种信息
//        List<TransactionCurrencyVO> transactionUserDeal= transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
//        if(transactionUserDeal == null || transactionUserDeal.size() <= 0){
//            return ;
//        }
//        //涨跌幅度计算
//        for(TransactionCurrencyVO transactionDealPrice : transactionUserDeal){
//            Object yesterdayPriceStr = redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + transactionDealPrice.getCurrencyId());
//            Object nowPriceStr = redisService.getValue(RedisKeyConfig.NOW_PRICE + transactionDealPrice.getCurrencyId());
//            if(yesterdayPriceStr != null && nowPriceStr != null){
//                double transactionPrice = Double.parseDouble(yesterdayPriceStr.toString());
//                double nowPrice = Double.parseDouble(nowPriceStr.toString());
//                double range = BigDecimalUtil.sub(nowPrice, transactionPrice);
//                range = BigDecimalUtil.mul(range, 100);
//                String rangeStr = BigDecimalUtil.div(range, transactionPrice, 2);
//                if(!StringUtil.isNotNull(rangeStr)){
//                    rangeStr = "0.0";
//                }
//                redisService.addValue(RedisKeyConfig.TODAY_RANGE + transactionDealPrice.getCurrencyId(), Double.parseDouble(rangeStr));
//            }
//        }
//
//    }

    /** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,当日成交量)*/
    public void standardMessageForRedis() {
        List<TransactionCurrencyDealPriceDTO> transactionCurrencyDealPriceList = transactionDealRedisService.getTransactionCurrencyDealPrice();
        if(transactionCurrencyDealPriceList != null && transactionCurrencyDealPriceList.size() > 0){
            for(TransactionCurrencyDealPriceDTO transactionCurrencyDealPrice : transactionCurrencyDealPriceList){
                //今日成交量
                redisService.addValue(RedisKeyConfig.DAY_TURNOVER + transactionCurrencyDealPrice.getCurrencyId(),
                        transactionCurrencyDealPrice.getTurnover());
                //今日最高价
                redisService.addValue(RedisKeyConfig.TODAY_MAX_PRICE + transactionCurrencyDealPrice.getCurrencyId(),
                        transactionCurrencyDealPrice.getHighestPrice());
                //今日最低价
                redisService.addValue(RedisKeyConfig.TODAY_MIN_PRICE + transactionCurrencyDealPrice.getCurrencyId(),
                        transactionCurrencyDealPrice.getLowestPrice());
                //今日成交额度
                redisService.addValue(RedisKeyConfig.DAY_VOLUME_OF_TRANSACTION + transactionCurrencyDealPrice.getCurrencyId(),
                        transactionCurrencyDealPrice.getDayTransaction());
            }
        }

        //获取币种信息
        List<Integer> currencyIdList = transactionCurrencyService.listcurrencyId();
        if(currencyIdList == null || currencyIdList.size() <= 0){
            return ;
        }

        //涨跌幅度计算
        for(Integer currencyId : currencyIdList){
            Object yesterdayPriceStr = redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId);
            Object nowPriceStr = redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId);
            if(yesterdayPriceStr != null && nowPriceStr != null){
                double transactionPrice = Double.parseDouble(yesterdayPriceStr.toString());
                double nowPrice = Double.parseDouble(nowPriceStr.toString());
                double range = BigDecimalUtil.sub(nowPrice, transactionPrice);
                range = BigDecimalUtil.mul(range, 100);
                String rangeStr = BigDecimalUtil.div(range, transactionPrice, 2);
                if(!StringUtil.isNotNull(rangeStr)){
                    rangeStr = "0.0";
                }
                redisService.addValue(RedisKeyConfig.TODAY_RANGE + currencyId, Double.parseDouble(rangeStr));
            }
        }

    }

    /** 每日开盘基准信息重置(昨日收盘价)*/  //今日涨跌,今日最高价,今日最低价,当日成交量(待定)
    public void updateWeeHoursBasisOfPrice(){
        Timestamp date;
        double quantity = 0;

        Timestamp getDate = DateUtil.getCurrentTime();
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
        if (transactionCurrencyDOS != null && !transactionCurrencyDOS.isEmpty()) {
            List<Integer> lowInt = new ArrayList<>();
            List<Integer> powInt = new ArrayList<>();
            Map<Integer, Double> map = new HashMap<>();

            for (TransactionCurrencyDO curr: transactionCurrencyDOS) {
                powInt.add(curr.getCurrencyId());
                map.put(curr.getCurrencyId(), curr.getGuidancePrice());
            }

            if (nowLastPrice != null && !nowLastPrice.isEmpty()) {
                for (TransactionDealPriceDTO currDTO: nowLastPrice) {
                    lowInt.add(currDTO.getCurrencyId());
                }
                powInt.removeAll(lowInt);
            }

            for (Integer cuId: powInt) {
                Double guidPrice = map.get(cuId);
                redisService.addValue(RedisKeyConfig.YESTERDAY_PRICE + cuId, guidPrice);
            }
        }
    }

    /**
     * 后台做单执行统计每天总成交量，总成交金额（定时器执行，系统初始化）
     * @return 执行成功：返回true，执行失败：返回false
     */
    public boolean exeStatistics() {
        //获取所有币种信息
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();
        if (CollectionUtils.isEmpty(transactionCurrencyList)) {
            return true;
        }

        Timestamp curTime = DateUtil.getCurrentTime();//当前时间
        long toDayDawnLong = DateUtil.lingchenLong();//今日凌晨时间戳
        Timestamp toDayDawnTime = DateUtil.longToTimestamp(toDayDawnLong);//今日凌晨
        //统计记录号
        String ordernNoPrefix = SystemCommonConfig.TRANSACTION_STATISTICS +
                DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10);
        for (TransactionCurrencyDO transactionCurrency : transactionCurrencyList) {
            int currencyId = transactionCurrency.getCurrencyId();
            //获取当前币种今日凌晨之前最近的系数
            TransactionCurrencyCoefficientDO transactionCurrencyCoefficient = transactionCurrencyCoefficientService.
                    getCurrencyCoefficientByCurrencyId(currencyId, toDayDawnTime);
            if (transactionCurrencyCoefficient == null) {
                continue;
            }
            //查询当前币种已有统计记录的统计时间
            Timestamp statisticsTime = transactionStatisticsService.getLastAddTimeByCurrencyId(currencyId);
            //当前币种不存在统计记录
            if (statisticsTime == null) {
                //查询当前币种后台做单的redis成交记录---最早的记录时间
                Timestamp redisTime = transactionDealRedisService.getEarliestTime(currencyId, SystemCommonConfig.
                        TRANSACTION_MAKE_ORDER);
                if (redisTime == null || redisTime.getTime() >= toDayDawnLong) {//判定是否为当日的成交记录
                    continue;
                }
                //统计
                statistics(currencyId, toDayDawnLong, curTime, redisTime.getTime(), transactionCurrencyCoefficient, ordernNoPrefix);
                continue;
            }
            //当前币种存在统计记录
            long statisticsTimeLong = statisticsTime.getTime();
            if (statisticsTimeLong >= toDayDawnLong) {//判定上一次统计时间与当日统计时间
                continue;
            }
            //统计
            statistics(currencyId, toDayDawnLong, curTime, statisticsTimeLong,  transactionCurrencyCoefficient,
                    ordernNoPrefix);
        }
        return true;
    }

    /**
     * 后台做单统计每天总成交量，总成交金额（专用接口，禁止其他接口调用）
     * @param currencyId 币种id
     * @param toDayDawnLong 当日凌晨时间戳
     * @param curTime 当前时间
     * @param statisticsTimeLong 统计时间戳
     * @param transactionCurrencyCoefficient 币种系数
     * @param ordernNoPrefix 记录号前缀
     * @return 执行成功：返回true，执行失败：返回false
     */
    public boolean statistics(int currencyId, long toDayDawnLong, Timestamp curTime, long statisticsTimeLong,
                              TransactionCurrencyCoefficientDO transactionCurrencyCoefficient, String ordernNoPrefix) {
        long oneDayLong = 1L * 24 * 60 * 60 * 1000;//24小时时间戳
        long statisticsDawnLong =  DateUtil.timeStrToLong(DateUtil.longToTimeStr(statisticsTimeLong, DateUtil.dateFormat4) +
                " 00:00:00.0");
        long statisticsDay = (toDayDawnLong - statisticsDawnLong) / oneDayLong;//未统计的天数
        if (statisticsDay <= 0) {
            return true;
        }

        List<TransactionStatisticsDO> transactionStatisticsList = new ArrayList<>();
        for (int i = 1; i <= statisticsDay; i++) {
            long startTimeLong = toDayDawnLong - i * 24 * 60 * 60 * 1000L;//从当日的前一天开始
            Timestamp startTime = DateUtil.longToTimestamp(startTimeLong);//开始时间
            //结束时间，23:59:59秒结束
            Timestamp endTime = DateUtil.longToTimestamp(startTimeLong + ((23 * 60 + 59) * 60 + 59) * 1000L);
            //统计
            TransactionBottomPriceDTO transactionBottomPrice = transactionDealRedisService.
                    getBottomPrice(currencyId, SystemCommonConfig.TRANSACTION_MAKE_ORDER, startTime, endTime);
            if (transactionBottomPrice == null) {
                continue;
            }

            String ordernNo = ordernNoPrefix + NumberUtil.createNumberStr(10);//记录号
            TransactionStatisticsDO transactionStatistics = new TransactionStatisticsDO();
            transactionStatistics.setOrderNo(ordernNo);
            transactionStatistics.setStatisticsDate(startTime);
            transactionStatistics.setCurrencyId(currencyId);
            transactionStatistics.setCurrencyName(transactionCurrencyCoefficient.getCurrencyName());
            transactionStatistics.setTransactionTotalNumber(transactionBottomPrice.getTotalNumber());
            transactionStatistics.setTransactionTotalPrice(transactionBottomPrice.getTotalPrice());
            transactionStatistics.setCurrencyCoefficient(transactionCurrencyCoefficient.getCurrencyCoefficient());
            transactionStatistics.setAddTime(curTime);
            transactionStatisticsList.add(transactionStatistics);
        }

        if (CollectionUtils.isEmpty(transactionStatisticsList)) {
            return true;
        }
        //新增统计记录
        return transactionStatisticsService.insertTransactionStatisticsList(transactionStatisticsList);
    }

}
