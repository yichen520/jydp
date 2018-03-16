package com.jydp.dao;

import com.jydp.entity.DTO.TransactionBottomPriceDTO;

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
}
