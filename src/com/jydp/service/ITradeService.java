package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;

/**
 * 匹配交易（单笔）
 * @author hz
 *
 */
public interface ITradeService {
    /**
     * 匹配交易（单笔）
     * @param order 挂单信息
     * @param matchOrder 匹配的挂单信息
     * @param sellFee 卖出手续费
     * @return JsonObjectBO 交易成功与否信息，若成功，同时返回成交后的此笔挂单信息
     */
    JsonObjectBO tradeHandle(TransactionPendOrderDO order, TransactionPendOrderDO matchOrder, double sellFee);

}
