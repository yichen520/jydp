package com.jydp.service;

import com.jydp.entity.DO.kgraph.*;
import com.jydp.entity.DTO.KGraphDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;

import java.util.List;
import java.util.Map;

/**
 * k线图统计数据公共服务
 * @author whx
 */
public interface IKGraphCommonService {

    /**
     * 系统初始化执行k线图数据存入redis
     */
    void exeKLineGraphForInit();

    /**
     * 执行k线图统计（定时器执行）
     */
    void exeKLineGraphForTimer();

    /**
     * 将k线图数据存入redis
     */
    void exeKLineGraphForRedisTimer(int currencyId);

    /**
     * k线图统计数据
     * @param curTimeLong 当前时间戳
     * @param redisTimeLong redis最早成交记录时间戳
     * @param currencyId 币种id
     */
    void exeKGraphAll(long curTimeLong, long redisTimeLong, int currencyId);

    /**
     * 新增五分钟统计数据
     * @param currencyId 币种id
     * @param initKGraph k线图统计数据（最近五分钟节点)
     * @param kGraphMap 五分钟统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphFiveMinutes(int currencyId, KGraphFiveMinutesDO initKGraph, Map<Long, KGraphFiveMinutesDO> kGraphMap);

    /**
     * 新增十五分钟统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 十五分钟统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphFifteenMinutes(int currencyId, double fiveLastTotal, Map<Long, KGraphFifteenMinutesDO> kGraphMap);

    /**
     * 新增三十分钟统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 三十分钟统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphHalfhour(int currencyId, double fiveLastTotal, Map<Long, KGraphHalfhourDO> kGraphMap);

    /**
     * 新增一小时统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 一小时统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphOneHour(int currencyId, double fiveLastTotal, Map<Long, KGraphOneHoursDO> kGraphMap);

    /**
     * 新增四小时统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 四小时统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphFourHours(int currencyId, double fiveLastTotal, Map<Long, KGraphFourHoursDO> kGraphMap);

    /**
     * 新增一天统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 一天统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphOneDay(int currencyId, double fiveLastTotal, Map<Long, KGraphOneDayDO> kGraphMap);

    /**
     * 新增一周统计数据
     * @param currencyId 币种id
     * @param fiveLastTotal 最近的五分钟节点的成交量
     * @param kGraphMap 一周统计结果集
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean exeKGraphOneWeek(int currencyId, double fiveLastTotal, Map<Long, KGraphOneWeekDO> kGraphMap);

    /**
     * 统计k线图不同时间节点的数据
     * @param currencyId 币种id
     * @param transactionDealRedisList 成交记录redis
     * @return 操作成功：返回统计结果，操作失败：返回null
     */
    KGraphDTO statisticsKLineGraph(int currencyId,
                                          List<TransactionDealRedisDTO> transactionDealRedisList);

}
