package com.jydp.service.impl.common;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.entity.DO.kgraph.*;
import com.jydp.entity.DTO.KGraphCurrencyDTO;
import com.jydp.entity.DTO.KGraphDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;
import com.jydp.entity.VO.TransactionGraphVO;
import com.jydp.service.*;
import config.KGraphConfig;
import config.RedisKeyConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    /** k线图统计数据（五分钟节点） */
    @Autowired
    private IKGraphFiveMinutesService kGraphFiveMinutesService;

    /** k线图统计数据（十五分钟节点） */
    @Autowired
    private IKGraphFifteenMinutesService kGraphFifteenMinutesService;

    /** k线图统计数据（三十分钟节点） */
    @Autowired
    private IKGraphHalfhourService kGraphHalfhourService;

    /** k线图统计数据（一小时节点） */
    @Autowired
    private IKGraphOneHoursService kGraphOneHoursService;

    /** k线图统计数据（四小时节点） */
    @Autowired
    private IKGraphFourHoursService kGraphFourHoursService;

    /** k线图统计数据（一天节点） */
    @Autowired
    private IKGraphOneDayService kGraphOneDayService;

    /** k线图统计数据（一周节点） */
    @Autowired
    private IKGraphOneWeekService kGraphOneWeekService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**
     * 系统初始化执行k线图数据存入redis
     */
    public void exeKLineGraphForInit() {
        //查询所有币种
        List<KGraphCurrencyDTO> currencyList = transactionCurrencyService.listKGraphCurrency();
        if (CollectionUtils.isEmpty(currencyList)) {
            return;
        }
        for (KGraphCurrencyDTO kGraphCurrency : currencyList) {
            if (kGraphCurrency.getUpStatus() == 1 || kGraphCurrency.getUpStatus() == 4) {
                continue;
            }
            int currencyId = kGraphCurrency.getCurrencyId();
            //将k线图数据存入redis
            exeKLineGraphForRedisTimer(currencyId);
        }
    }

    /**
     * 执行k线图统计（定时器执行）
     */
    public void exeKLineGraphForTimer() {
        //查询所有币种
        List<KGraphCurrencyDTO> currencyList = transactionCurrencyService.listKGraphCurrency();
        if (CollectionUtils.isEmpty(currencyList)) {
            return;
        }

        Timestamp curTime = DateUtil.getCurrentTime();
        long curTimeLong = curTime.getTime();
        for (KGraphCurrencyDTO kGraphCurrency : currencyList) {
            if (kGraphCurrency.getUpStatus() == 1 || kGraphCurrency.getUpStatus() == 4) {
                continue;
            }
            int currencyId = kGraphCurrency.getCurrencyId();
            Timestamp nodeLatelyTime = kGraphFiveMinutesService.getKGraphLatelyTime(currencyId);
            //nodeLatelyTime为null，未进行五分钟统计
            if (nodeLatelyTime == null) {
                //该币种未进行过交易
                Timestamp redisTime = transactionDealRedisService.getEarliestTime(currencyId, null);
                if (redisTime == null) {
                    continue;
                }
                //k线图统计数据，新增
                exeKGraphAll(curTimeLong, redisTime.getTime(), currencyId);
                //将k线图数据存入redis
                exeKLineGraphForRedisTimer(currencyId);
                continue;
            }
            //已统计过，则查询redis成交记录（上次统计时间到当前时间）
            List<TransactionDealRedisDTO> transactionDealRedisList = transactionDealRedisService.
                    listTransactionDealRedisForTimer(currencyId, nodeLatelyTime, curTime);
            if (CollectionUtils.isEmpty(transactionDealRedisList)) {
                continue;
            }
            //统计结果
            KGraphDTO kGraph = statisticsKLineGraph(currencyId, transactionDealRedisList);
            if (kGraph == null) {
                continue;
            }
            //判定最近的五分钟节点有无交易信息
            KGraphFiveMinutesDO fiveMinutesKGraph = kGraphFiveMinutesService.getKGraphLately(currencyId);
            if (fiveMinutesKGraph == null || CollectionUtils.isEmpty(kGraph.getFiveMap().entrySet())) {
                continue;
            }
            long fiveMinutesNode = fiveMinutesKGraph.getNodeTime().getTime();
            //long nextNode = fiveMinutesNode + KGraphConfig.FIVE;//下一个节点戳
            KGraphFiveMinutesDO fiveMinutes = kGraph.getFiveMap().get(fiveMinutesNode);
            //最近的五分钟节点无交易信息，并且无下一个节点
            if (kGraph.getFiveMap().size() == 1 && fiveMinutes != null
                    && fiveMinutesKGraph.getTransactionTotal() <= fiveMinutes.getTransactionTotal()) {
                continue;
            }

            double fiveLastTotal = fiveMinutesKGraph.getTransactionTotal();//最近的五分钟节点的成交量
            //新增统计数据，如果有重复节点则修改
            exeKGraphFiveMinutes(currencyId, fiveMinutesKGraph, kGraph.getFiveMap());
            exeKGraphFifteenMinutes(currencyId, fiveLastTotal, kGraph.getFifteenMap());
            exeKGraphHalfhour(currencyId, fiveLastTotal, kGraph.getHalfhourMap());
            exeKGraphOneHour(currencyId, fiveLastTotal, kGraph.getOneHoursMap());
            exeKGraphFourHours(currencyId, fiveLastTotal, kGraph.getFourHoursMap());
            exeKGraphOneDay(currencyId, fiveLastTotal, kGraph.getOneDayMap());
            exeKGraphOneWeek(currencyId, fiveLastTotal, kGraph.getOneWeekMap());
            //将k线图数据存入redis
            exeKLineGraphForRedisTimer(currencyId);
        }
    }

    /**
     * 将k线图数据存入redis
     */
    public void exeKLineGraphForRedisTimer(int currencyId) {
        int num = 100;
        //五分钟节点
        List<TransactionGraphVO> kGraphFiveMinutesList = kGraphFiveMinutesService.
                listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "5m",
                kGraphFiveMinutesList);
        //十五分钟节点
        List<TransactionGraphVO> kGraphFifteenMinutesList = kGraphFifteenMinutesService.
                listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "15m",
                kGraphFifteenMinutesList);
        //三十分钟节点
        List<TransactionGraphVO> kGraphHalfhourList = kGraphHalfhourService.listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "30m",
                kGraphHalfhourList);
        //一小时节点
        List<TransactionGraphVO> kGraphOneHoursList = kGraphOneHoursService.listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "1h",
                kGraphOneHoursList);
        //四小时节点
        List<TransactionGraphVO> kGraphFourHoursList = kGraphFourHoursService.listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "4h",
                kGraphFourHoursList);
        //一天节点
        List<TransactionGraphVO> kGraphOneDayList = kGraphOneDayService.listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "1d",
                kGraphOneDayList);
        //一周节点
        List<TransactionGraphVO> kGraphOneWeekList = kGraphOneWeekService.listKGraphLately(currencyId, num);
        redisService.addValue(RedisKeyConfig.GRAPH_DATA + currencyId + "1w",
                kGraphOneWeekList);
    }

    /**
     * k线图统计数据
     * @param curTimeLong 当前时间戳
     * @param redisTimeLong redis最早成交记录时间戳
     * @param currencyId 币种id
     */
    public void exeKGraphAll(long curTimeLong, long redisTimeLong, int currencyId) {
        long nodeTotal = (curTimeLong - redisTimeLong) / KGraphConfig.ONEWEEK;
        for (int node = 0; node <= nodeTotal; node++) {
            long initNodeLong = weekNodeTime(KGraphConfig.ONEWEEK, curTimeLong) -
                    node * KGraphConfig.ONEWEEK;;//一周开始时间
            long endNodeLong = 0;
            if (node == 0) {
                endNodeLong = curTimeLong;//当前周
            } else {
                endNodeLong = initNodeLong + (((6 * 24 + 23) * 60 + 59) * 60 + 59) * 1000L;//一周结束时间
            }
            Timestamp initNodeTime = DateUtil.longToTimestamp(initNodeLong);
            Timestamp endNodeTime = DateUtil.longToTimestamp(endNodeLong);

            List<TransactionDealRedisDTO> transactionDealRedisList = transactionDealRedisService.
                    listTransactionDealRedisForTimer(currencyId, initNodeTime, endNodeTime);
            if (CollectionUtils.isEmpty(transactionDealRedisList)) {
                return;
            }
            //统计结果
            KGraphDTO kGraph = statisticsKLineGraph(currencyId, transactionDealRedisList);
            if (kGraph == null) {
                return;
            }

            if (CollectionUtils.isNotEmpty(kGraph.getFiveMap().entrySet())) {
                List<KGraphFiveMinutesDO> kGraphList =
                        new ArrayList<KGraphFiveMinutesDO>(kGraph.getFiveMap().values());
                kGraphFiveMinutesService.insertKGraph(kGraphList);
            }
            if (CollectionUtils.isNotEmpty(kGraph.getFifteenMap().entrySet())) {
                List<KGraphFifteenMinutesDO> kGraphList =
                        new ArrayList<KGraphFifteenMinutesDO>(kGraph.getFifteenMap().values());
                kGraphFifteenMinutesService.insertKGraph(kGraphList);
            }
            if (CollectionUtils.isNotEmpty(kGraph.getHalfhourMap().entrySet())) {
                List<KGraphHalfhourDO> kGraphList =
                        new ArrayList<KGraphHalfhourDO>(kGraph.getHalfhourMap().values());
                kGraphHalfhourService.insertKGraph(kGraphList);
            }
            if (CollectionUtils.isNotEmpty(kGraph.getOneHoursMap().entrySet())) {
                List<KGraphOneHoursDO> kGraphList =
                        new ArrayList<KGraphOneHoursDO>(kGraph.getOneHoursMap().values());
                kGraphOneHoursService.insertKGraph(kGraphList);
            }
            if (CollectionUtils.isNotEmpty(kGraph.getFourHoursMap().entrySet())) {
                List<KGraphFourHoursDO> kGraphList =
                        new ArrayList<KGraphFourHoursDO>(kGraph.getFourHoursMap().values());
                kGraphFourHoursService.insertKGraph(kGraphList);
            }
            if (CollectionUtils.isNotEmpty(kGraph.getOneDayMap().entrySet())) {
                List<KGraphOneDayDO> kGraphList =
                        new ArrayList<KGraphOneDayDO>(kGraph.getOneDayMap().values());
                kGraphOneDayService.insertKGraph(kGraphList);
            }
            if (CollectionUtils.isNotEmpty(kGraph.getOneWeekMap().entrySet())) {
                List<KGraphOneWeekDO> kGraphList =
                        new ArrayList<KGraphOneWeekDO>(kGraph.getOneWeekMap().values());
                kGraphOneWeekService.insertKGraph(kGraphList);
            }
        }
    }

    /**
     * 新增五分钟统计数据
     * @param currencyId 币种id
     * @param kGraphMap 五分钟统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphFiveMinutes(int currencyId, KGraphFiveMinutesDO initKGraph,
                                        Map<Long, KGraphFiveMinutesDO> kGraphMap) {
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = minuteNodeTime(KGraphConfig.FIVE, kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphFiveMinutesDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //成交总量
                double total = NumberUtil.doubleFormat(oldKGraph.getTransactionTotal(), 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphFiveMinutesService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);//移除该节点
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphFiveMinutesDO> kGraphList = new ArrayList<KGraphFiveMinutesDO>(kGraphMap.values());
        return kGraphFiveMinutesService.insertKGraph(kGraphList);
    }

    /**
     * 新增十五分钟统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 十五分钟统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphFifteenMinutes(int currencyId, double fiveLastTotal,
                                           Map<Long, KGraphFifteenMinutesDO> kGraphMap) {
        KGraphFifteenMinutesDO initKGraph = kGraphFifteenMinutesService.getKGraphLately(currencyId);
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = minuteNodeTime(KGraphConfig.FIFTEEN, kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphFifteenMinutesDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //新增的成交量
                double initTotal = BigDecimalUtil.sub(initKGraph.getTransactionTotal(), fiveLastTotal);
                //成交总量
                double total = BigDecimalUtil.add(oldKGraph.getTransactionTotal(), initTotal);
                total = NumberUtil.doubleFormat(total, 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphFifteenMinutesService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphFifteenMinutesDO> kGraphList = new ArrayList<KGraphFifteenMinutesDO>(kGraphMap.values());
        return kGraphFifteenMinutesService.insertKGraph(kGraphList);
    }

    /**
     * 新增三十分钟统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 三十分钟统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphHalfhour(int currencyId, double fiveLastTotal, Map<Long, KGraphHalfhourDO> kGraphMap) {
        KGraphHalfhourDO initKGraph = kGraphHalfhourService.getKGraphLately(currencyId);
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = minuteNodeTime(KGraphConfig.HALFHOUR, kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphHalfhourDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //新增的成交量
                double initTotal = BigDecimalUtil.sub(initKGraph.getTransactionTotal(), fiveLastTotal);
                //成交总量
                double total = BigDecimalUtil.add(oldKGraph.getTransactionTotal(), initTotal);
                total = NumberUtil.doubleFormat(total, 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphHalfhourService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphHalfhourDO> kGraphList = new ArrayList<KGraphHalfhourDO>(kGraphMap.values());
        return kGraphHalfhourService.insertKGraph(kGraphList);
    }

    /**
     * 新增一小时统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 一小时统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphOneHour(int currencyId, double fiveLastTotal, Map<Long, KGraphOneHoursDO> kGraphMap) {
        KGraphOneHoursDO initKGraph = kGraphOneHoursService.getKGraphLately(currencyId);
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = hoursNodeTime(KGraphConfig.ONEHOURS, kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphOneHoursDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //新增的成交量
                double initTotal = BigDecimalUtil.sub(initKGraph.getTransactionTotal(), fiveLastTotal);
                //成交总量
                double total = BigDecimalUtil.add(oldKGraph.getTransactionTotal(), initTotal);
                total = NumberUtil.doubleFormat(total, 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphOneHoursService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphOneHoursDO> kGraphList = new ArrayList<KGraphOneHoursDO>(kGraphMap.values());
        return kGraphOneHoursService.insertKGraph(kGraphList);
    }

    /**
     * 新增四小时统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 四小时统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphFourHours(int currencyId, double fiveLastTotal, Map<Long, KGraphFourHoursDO> kGraphMap) {
        KGraphFourHoursDO initKGraph = kGraphFourHoursService.getKGraphLately(currencyId);
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = foreHoursNodeTime(KGraphConfig.FOREHOURS, kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphFourHoursDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //新增的成交量
                double initTotal = BigDecimalUtil.sub(initKGraph.getTransactionTotal(), fiveLastTotal);
                //成交总量
                double total = BigDecimalUtil.add(oldKGraph.getTransactionTotal(), initTotal);
                total = NumberUtil.doubleFormat(total, 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphFourHoursService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphFourHoursDO> kGraphList = new ArrayList<KGraphFourHoursDO>(kGraphMap.values());
        return kGraphFourHoursService.insertKGraph(kGraphList);
    }

    /**
     * 新增一天统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 一天统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphOneDay(int currencyId, double fiveLastTotal, Map<Long, KGraphOneDayDO> kGraphMap) {
        KGraphOneDayDO initKGraph = kGraphOneDayService.getKGraphLately(currencyId);
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = dayNodeTime(kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphOneDayDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //新增的成交量
                double initTotal = BigDecimalUtil.sub(initKGraph.getTransactionTotal(), fiveLastTotal);
                //成交总量
                double total = BigDecimalUtil.add(oldKGraph.getTransactionTotal(), initTotal);
                total = NumberUtil.doubleFormat(total, 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphOneDayService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphOneDayDO> kGraphList = new ArrayList<KGraphOneDayDO>(kGraphMap.values());
        return kGraphOneDayService.insertKGraph(kGraphList);
    }

    /**
     * 新增一周统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 一周统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean exeKGraphOneWeek(int currencyId, double fiveLastTotal, Map<Long, KGraphOneWeekDO> kGraphMap) {
        KGraphOneWeekDO initKGraph = kGraphOneWeekService.getKGraphLately(currencyId);
        if (initKGraph == null || CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        long kGraphLong = initKGraph.getNodeTime().getTime();
        long kGraphNode = weekNodeTime(KGraphConfig.ONEWEEK, kGraphLong);
        //统计节点重复，则重新对比计算该节点
        if (kGraphMap.containsKey(kGraphNode)) {
            KGraphOneWeekDO oldKGraph = kGraphMap.put(kGraphNode, initKGraph);
            if (oldKGraph != null) {
                if (kGraphLong < oldKGraph.getOpenClosTime()) {
                    initKGraph.setClosPrice(oldKGraph.getClosPrice());//收盘价
                } else {
                    initKGraph.setOpenPrice(oldKGraph.getOpenPrice());//开盘价
                }
                //最高价
                if (initKGraph.getMaxPrice() < oldKGraph.getMaxPrice()) {
                    initKGraph.setMaxPrice(oldKGraph.getMaxPrice());
                }
                //最低价
                if (initKGraph.getMinPrice() > oldKGraph.getMinPrice()) {
                    initKGraph.setMinPrice(oldKGraph.getMinPrice());
                }
                //新增的成交量
                double initTotal = BigDecimalUtil.sub(initKGraph.getTransactionTotal(), fiveLastTotal);
                //成交总量
                double total = BigDecimalUtil.add(oldKGraph.getTransactionTotal(), initTotal);
                total = NumberUtil.doubleFormat(total, 8);
                initKGraph.setTransactionTotal(total);
            }
            //修改重复节点
            kGraphOneWeekService.updateKGraph(kGraphMap.get(kGraphNode));
            kGraphMap.remove(kGraphNode);
        }
        //新增节点
        if (CollectionUtils.isEmpty(kGraphMap.entrySet())) {
            return true;
        }
        List<KGraphOneWeekDO> kGraphList = new ArrayList<KGraphOneWeekDO>(kGraphMap.values());
        return kGraphOneWeekService.insertKGraph(kGraphList);
    }

    /**
     * 统计k线图不同时间节点的数据
     * @param currencyId 币种id
     * @param transactionDealRedisList 成交记录redis
     * @return 操作成功：返回统计结果，操作失败：返回null
     */
    public KGraphDTO statisticsKLineGraph(int currencyId,
                                          List<TransactionDealRedisDTO> transactionDealRedisList) {
        if (CollectionUtils.isEmpty(transactionDealRedisList)) {
            return null;
        }

        Map<Long, KGraphFiveMinutesDO> fiveMap = new HashMap<>();//五分钟节点
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
                                  long addTimeLong, Map<Long, KGraphFiveMinutesDO> fiveMap) {
        KGraphFiveMinutesDO initGraph = new KGraphFiveMinutesDO();
        initGraph.setCurrencyId(currencyId);
        initGraph.setNodeTime(DateUtil.longToTimestamp(nodeTime));
        initGraph.setOpenPrice(transactionPrice);
        initGraph.setClosPrice(transactionPrice);
        initGraph.setMaxPrice(transactionPrice);
        initGraph.setMinPrice(transactionPrice);
        initGraph.setTransactionTotal(currencyNumber);
        initGraph.setOpenClosTime(addTimeLong);
        //已统计的数据
        KGraphFiveMinutesDO oldGraph = fiveMap.put(nodeTime, initGraph);
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
        long hoursTimeLong = DateUtil.longToTimestampByFormat(addTime, "yyyy-MM-dd 08:00:00.0").
                getTime();
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
        long hoursTimeLong = DateUtil.longToTimestampByFormat(addTime, "yyyy-MM-dd 08:00:00.0")
                .getTime();
        long resultTime = (minuteTimeLong - hoursTimeLong) / node;//计算时间节点
        return hoursTimeLong + node * resultTime - 8 * 60 * 60 * 1000L;
        //return hoursTimeLong + node * resultTime;
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
        //String initWeek = "2007-01-01 00:00:00.0";//2007-01-01为星期一
        long initWeekLong = DateUtil.timeStrToLong(initWeek);
        timeLong = timeLong - 8 * 60 * 60 * 1000L;//8点开盘
        long weekTimeLong = DateUtil.longToTimestampByFormat(timeLong, "yyyy-MM-dd 08:00:00.0")
                .getTime();
        long resultTime = (weekTimeLong - initWeekLong) / node;//计算时间节点
        return initWeekLong + node * resultTime;
    }

}
