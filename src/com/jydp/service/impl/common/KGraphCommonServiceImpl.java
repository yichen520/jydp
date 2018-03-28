package com.jydp.service.impl.common;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.entity.DO.kgraph.*;
import com.jydp.entity.DTO.KGraphDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;
import com.jydp.service.IKGraphCommonService;
import com.jydp.service.ITransactionDealRedisService;
import config.KGraphConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * k线图统计数据公共服务
 * @author whx
 */
@Service("kGraphCommonService")
public class KGraphCommonServiceImpl implements IKGraphCommonService {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /**
     * k线图统计（定时器，redis操作）
     */
    public void exeKLineGraphForTimer() {
        int currencyId = 79;
        Timestamp startTime = DateUtil.stringToTimestamp("2018-03-19 08:00:00.0");
        Timestamp endTime = DateUtil.stringToTimestamp("2018-03-23 07:59:59.0");
        List<TransactionDealRedisDTO> transactionDealRedisList = transactionDealRedisService.
                listTransactionDealRedisForTimer(currencyId, startTime, endTime);
        if (CollectionUtils.isEmpty(transactionDealRedisList)) {
            return;
        }

        KGraphDTO kGraph = statisticsKLineGraph(currencyId, transactionDealRedisList);
        return;
    }

    /**
     * 统计k线图不同时间节点的数据
     * @param currencyId 币种id
     * @param transactionDealRedisList 成交记录redis
     * @return 操作成功：返回统计结果，操作失败：返回null
     */
    public KGraphDTO statisticsKLineGraph(int currencyId, List<TransactionDealRedisDTO> transactionDealRedisList) {
        if (CollectionUtils.isEmpty(transactionDealRedisList)) {
            return null;
        }

        Map<Long, KGraphFifteenMinutesDO> fiveMap = new HashMap<>();//五分钟节点
        Map<Long, KGraphFifteenMinutesDO> fifteenMap = new HashMap<>();//十五分钟节点
        Map<Long, KGraphHalfhourDO> halfhourMap = new HashMap<>();//三十分钟节点
        Map<Long, KGraphOneHoursDO> oneHoursMap = new HashMap<>();//一小时节点
        Map<Long, KGraphFourHoursDO> fourHoursMap = new HashMap<>();//四小时节点
        Map<Long, KGraphOneDayDO> oneDayMap = new HashMap<>();//一天节点
        Map<Long, KGraphOneWeekDO> oneWeekMap = new HashMap<>();//一周节点
        for (TransactionDealRedisDTO transactionDeal : transactionDealRedisList) {
            double transactionPrice = transactionDeal.getTransactionPrice();//成交单价
            double currencyNumber = transactionDeal.getCurrencyNumber();//成交数量
            long addTime = transactionDeal.getAddTime().getTime();
            //五分钟节点
            long fiveNode = minuteNodeTime(KGraphConfig.FIVE, addTime);
            kGraphFiveMinutes(fiveNode, currencyId, transactionPrice, currencyNumber, addTime, fiveMap);
            //十五分钟节点
            long fifteenNode = minuteNodeTime(KGraphConfig.FIFTEEN, addTime);
            kGraphFifteenMinutes(fifteenNode, currencyId, transactionPrice, currencyNumber, addTime, fifteenMap);
            //三十分钟节点
            long halfhourNode = minuteNodeTime(KGraphConfig.HALFHOUR, addTime);
            kGraphHalfhour(halfhourNode, currencyId, transactionPrice, currencyNumber, addTime, halfhourMap);
            //一小时节点
            long oneHoursNode = hoursNodeTime(KGraphConfig.ONEHOURS, addTime);
            kGraphOneHours(oneHoursNode, currencyId, transactionPrice, currencyNumber, addTime, oneHoursMap);
            //四小时节点
            long foreHoursNode = foreHoursNodeTime(KGraphConfig.FOREHOURS, addTime);
            kGraphFourHours(foreHoursNode, currencyId, transactionPrice, currencyNumber, addTime, fourHoursMap);
            //一天节点
            long oneDayNode = dayNodeTime(addTime);
            kGraphOneDay(oneDayNode, currencyId, transactionPrice, currencyNumber, addTime, oneDayMap);
            //一周节点
            long oneWeekNode = weekNodeTime(KGraphConfig.ONEWEEK, addTime);
            kGraphOneWeek(oneWeekNode, currencyId, transactionPrice, currencyNumber, addTime, oneWeekMap);
        }

        KGraphDTO kGraph = new KGraphDTO();
        kGraph.setCurrencyId(currencyId);
        kGraph.setFiveMap(fiveMap);
        kGraph.setFifteenMap(fifteenMap);
        kGraph.setHalfhourMap(halfhourMap);
        kGraph.setOneHoursMap(oneHoursMap);
        kGraph.setFourHoursMap(fourHoursMap);
        kGraph.setOneDayMap(oneDayMap);
        kGraph.setOneWeekMap(oneWeekMap);
        return kGraph;
    }

    /**
     * k线图统计数据（五分钟节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param fiveMap k线图统计数据（五分钟节点）
     */
    public void kGraphFiveMinutes(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                                  long addTimeLong, Map<Long, KGraphFifteenMinutesDO> fiveMap) {
        KGraphFifteenMinutesDO initGraph = new KGraphFifteenMinutesDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphFifteenMinutesDO oldGraph = fiveMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * k线图统计数据（十五分钟节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param fifteenMap k线图统计数据（十五分钟节点）
     */
    public void kGraphFifteenMinutes(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                                     long addTimeLong, Map<Long, KGraphFifteenMinutesDO> fifteenMap) {
        KGraphFifteenMinutesDO initGraph = new KGraphFifteenMinutesDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphFifteenMinutesDO oldGraph = fifteenMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * k线图统计数据（三十分钟节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param halfhourMap k线图统计数据（三十分钟节点）
     */
    public void kGraphHalfhour(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                               long addTimeLong, Map<Long, KGraphHalfhourDO> halfhourMap) {
        KGraphHalfhourDO initGraph = new KGraphHalfhourDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphHalfhourDO oldGraph = halfhourMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * k线图统计数据（一小时节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param oneHoursMap k线图统计数据（一小时节点）
     */
    public void kGraphOneHours(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                               long addTimeLong, Map<Long, KGraphOneHoursDO> oneHoursMap) {
        KGraphOneHoursDO initGraph = new KGraphOneHoursDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphOneHoursDO oldGraph = oneHoursMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * k线图统计数据（四小时节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param fourHoursMap k线图统计数据（四小时节点）
     */
    public void kGraphFourHours(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                               long addTimeLong, Map<Long, KGraphFourHoursDO> fourHoursMap) {
        KGraphFourHoursDO initGraph = new KGraphFourHoursDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphFourHoursDO oldGraph = fourHoursMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * k线图统计数据（一天节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param oneDayMap k线图统计数据（一天节点）
     */
    public void kGraphOneDay(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                               long addTimeLong, Map<Long, KGraphOneDayDO> oneDayMap) {
        KGraphOneDayDO initGraph = new KGraphOneDayDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphOneDayDO oldGraph = oneDayMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * k线图统计数据（一周节点）
     * @param nodeTime 时间节点
     * @param currencyId 币种id
     * @param transactionPrice 成交价
     * @param currencyNumber 成交数量
     * @param addTimeLong redis成交记录时间戳
     * @param oneWeekMap k线图统计数据（一周节点）
     */
    public void kGraphOneWeek(long nodeTime, int currencyId, double transactionPrice, double currencyNumber,
                             long addTimeLong, Map<Long, KGraphOneWeekDO> oneWeekMap) {
        KGraphOneWeekDO initGraph = new KGraphOneWeekDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphOneWeekDO oldGraph = oneWeekMap.put(nodeTime, initGraph);
        if (oldGraph != null) {
            if (addTimeLong < oldGraph.getOpenClosTime()) {
                initGraph.setClosPrice(oldGraph.getClosPrice());//收盘价
            } else {
                initGraph.setOpenPrice(oldGraph.getOpenPrice());//开盘价
            }
            //最高价
            if (transactionPrice < oldGraph.getMaxPrice()) {
                initGraph.setMaxPrice(oldGraph.getMaxPrice());
            }
            //最低价
            if (transactionPrice > oldGraph.getMinPrice()) {
                initGraph.setMinPrice(oldGraph.getMinPrice());
            }
            //成交总量
            double total = BigDecimalUtil.add(oldGraph.getTransactionTotal(), currencyNumber);
            total = NumberUtil.doubleFormat(total, 8);
            initGraph.setTransactionTotal(total);
        }
    }

    /**
     * 按分钟计算时间节点
     * @param node 时间节点
     * @param addTime 当前时间
     * @return 返回当前时间节点
     */
    private static long minuteNodeTime(long node, long addTime) {
        long minuteTimeLong = DateUtil.longToTimestampByFormat(addTime, DateUtil.dateFormat12).getTime() ;
        long hoursTimeLong = DateUtil.longToTimestampByFormat(addTime, DateUtil.dateFormat13).getTime();
        long resultTime = (minuteTimeLong - hoursTimeLong) / node; //计算时间节点
        return hoursTimeLong + node * resultTime;
    }

    /**
     * 按小时计算时间节点
     * @param node 时间节点
     * @param addTime 当前时间
     * @return 返回当前时间节点
     */
    private static long hoursNodeTime(long node, long addTime) {
        long minuteTimeLong = DateUtil.longToTimestampByFormat(addTime, DateUtil.dateFormat13).getTime() ;
        long hoursTimeLong = DateUtil.longToTimestampByFormat(addTime, "yyyy-MM-dd 08:00:00.0").getTime();
        long resultTime = (minuteTimeLong - hoursTimeLong) / node;//计算时间节点
        return hoursTimeLong + node * resultTime;
    }

    /**
     * 按4小时计算时间节点
     * @param node 时间节点
     * @param addTime 当前时间
     * @return 返回当前时间节点
     */
    private static long foreHoursNodeTime(long node, long addTime) {
        addTime = addTime + 8 * 60 * 60 * 1000L;//8点开盘
        long minuteTimeLong = DateUtil.longToTimestampByFormat(addTime, DateUtil.dateFormat13).getTime() ;
        long hoursTimeLong = DateUtil.longToTimestampByFormat(addTime, "yyyy-MM-dd 00:00:00.0").getTime();
        long resultTime = (minuteTimeLong - hoursTimeLong) / node;//计算时间节点
        return hoursTimeLong + node * resultTime - 8 * 60 * 60 * 1000L;
    }

    /**
     * 按一天计算时间节点
     * @param timeLong 当前时间
     * @return 返回当前天的早上八点
     */
    private static long dayNodeTime(long timeLong) {
        timeLong = timeLong - 8 * 60 * 60 * 1000L;//8点开盘
        return DateUtil.longToTimestampByFormat(timeLong, "yyyy-MM-dd 08:00:00.0").getTime();
    }

    /**
     * 按一周计算时间节点
     * @param node 时间节点
     * @param timeLong 当前时间
     * @return 返回星期一早上八点
     */
    private static long weekNodeTime(long node, long timeLong) {
        String initWeek = "2007-01-01 08:00:00.0";//2007-01-01为星期一
        long initWeekLong = DateUtil.timeStrToLong(initWeek);
        timeLong = timeLong - 8 * 60 * 60 * 1000L;//8点开盘
        long weekTimeLong = DateUtil.longToTimestampByFormat(timeLong, "yyyy-MM-dd 08:00:00.0").getTime();
        long resultTime = (weekTimeLong - initWeekLong) / node;//计算时间节点
        return initWeekLong + node * resultTime;
    }

}
