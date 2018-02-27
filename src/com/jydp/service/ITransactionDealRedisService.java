package com.jydp.service;

import com.jydp.entity.DO.transaction.TransactionDealRedisDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * redis成交记录
 * @author fk
 *
 */
public interface ITransactionDealRedisService {

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId);

    /**
     * 新增成交记录
     * @param orderNo  记录号
     * @param paymentType  收支类型
     * @param currencyId  币种Id
     * @param transactionPrice  成交单价
     * @param currencyNumber  成交数量
     * @param currencyTotalPrice  成交总价
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionDealRedis(String orderNo, int paymentType, int currencyId,
                                       double transactionPrice, double currencyNumber, double currencyTotalPrice,
                                        Timestamp addTime);

}
