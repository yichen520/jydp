package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionStatisticsDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Description:交易统计记录表
 * Author: hht
 * Date: 2018-03-16 9:08
 */
public interface ITransactionStatisticsDao {
    /**
     * 获取盛源交易所 历史当日成交总价*历史当日系数，历史当日成交总数量
     * @param currencyId 币种Id
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    TransactionBottomPriceDTO getBottomPricePast(int currencyId);

    /**
     * 查询交易统计记录条数(后台)
     *
     * @param currencyId 货币Id，查询全部为0
     * @param startTime  统计开始时间，可为null
     * @param endTime    统计接受时间，可为null
     * @return 操作成功：返回交易统计记录条数，操作失败：返回0
     */
    int countTransactionStatisticsForBack(int currencyId, Timestamp startTime, Timestamp endTime);

    /**
     * 查询交易统计记录列表(后台)
     *
     * @param currencyId 货币Id，查询全部为0
     * @param startTime  统计开始时间，可为null
     * @param endTime    统计接受时间，可为null
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return 操作成功：返回交易统计记录，操作失败：返回null
     */
    List<TransactionStatisticsDO> listTransactionStatisticsForBack(int currencyId, Timestamp startTime,
                                                                   Timestamp endTime, int pageNumber, int pageSize);

    /**
     * 批量新增交易统计
     * @param transactionStatisticsDOS  交易统计集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionStatisticsList(List<TransactionStatisticsDO> transactionStatisticsDOS);

    /**
     * 获取最后一条添加的时间
     * @return  操作成功：返回添加的时间，操作失败：返回null
     */
    Timestamp getLastAddTime();
}
