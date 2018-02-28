package com.jydp.service;

/**
 * 匹配交易
 * @author hz
 *
 */
public interface ITradeCommonService {
    /**
     * 匹配交易
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param paymentType 收支类型,1：买入，2：卖出
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean trade(int userId, int currencyId, int paymentType);

}
