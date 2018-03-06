package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;

/**
 * 匹配交易（迭代）
 * @author hz
 *
 */
public interface ITradeCommonService {
    /**
     * 匹配交易（迭代）
     * @param order 挂单信息
     * @return 操作成功：返回true，操作失败：返回false
     */
    JsonObjectBO trade(TransactionPendOrderDO order);

}
