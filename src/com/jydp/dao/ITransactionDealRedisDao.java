package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionDealRedisDO;

import java.util.List;

/**
 * redis成交记录
 * @author hz
 *
 */
public interface ITransactionDealRedisDao {

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId);

    /**
     * 新增成交记录
     * @param transactionDealRedis  成交记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionDealRedis(TransactionDealRedisDO transactionDealRedis);

}
