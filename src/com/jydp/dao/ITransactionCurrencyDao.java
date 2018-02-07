package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;

import java.sql.Timestamp;

/**
 * 交易币种
 * @author fk
 *
 */
public interface ITransactionCurrencyDao {

    /**
     * 新增交易币种
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionCurrency(TransactionCurrencyDO transactionCurrency);

    /**
     * 根据币种Id获取交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    TransactionCurrencyDO getTransactionCurrencyByCurrencyId(int currencyId);

    /**
     * 修改交易币种信息
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean updateTransactionCurrency(TransactionCurrencyDO transactionCurrency);

    /**
     * 根据币种Id删除交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean deleteTransactionCurrencyByCurrencyId(int currencyId);
}
