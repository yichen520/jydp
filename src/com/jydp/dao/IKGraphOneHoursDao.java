package com.jydp.dao;

import com.jydp.entity.DO.kgraph.KGraphOneHoursDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * k线图统计数据（一小时节点）
 * @author whx
 */
public interface IKGraphOneHoursDao {

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean insertKGraph(List<KGraphOneHoursDO> kGraphFiveMinutesList);

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    Timestamp getKGraphLatelyTime(int currencyId);

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    KGraphOneHoursDO getKGraphLately(int currencyId);

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    List<KGraphOneHoursDO> listKGraphLately(int currencyId, int num);
}
