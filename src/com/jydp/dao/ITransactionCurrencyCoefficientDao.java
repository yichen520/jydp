package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 币种系数
 * @author fk
 *
 */
public interface ITransactionCurrencyCoefficientDao {

    /**
     * 新增币种系数
     * @param transactionCurrencyCoefficient  币种系数
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionCurrencyCoefficient(TransactionCurrencyCoefficientDO transactionCurrencyCoefficient);

    /**
     * 根据记录号查询币种系数
     * @param orderNo  记录号
     * @return  操作成功：返回币种系数，操作失败：返回null
     */
    TransactionCurrencyCoefficientDO getTransactionCurrencyCoefficientByOrderNo(String orderNo);

    /**
     * 查询币种系数条数
     * @param currencyName  币种名称
     * @param startAddTime  起始添加时间
     * @param endAddTime  结束添加时间
     * @return  操作成功：返回币种系数条数，操作失败：返回0
     */
    int countTransactionCurrencyCoeffieientForBack(String currencyName, Timestamp startAddTime, Timestamp endAddTime);

    /**
     * 查询币种系数集合
     * @param currencyName  币种名称
     * @param startAddTime  起始添加时间
     * @param endAddTime  结束添加时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回币种系数集合，操作失败：返回null
     */
    List<TransactionCurrencyCoefficientDO> listTransactionCurrencyCoefficientForBack(String currencyName, Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize);

    /**
     * 根据币种Id查询最新的币种系数
     * @param currencyId  币种Id
     * @return  操作成功：返回币种系数，操作失败：返回null
     */
    TransactionCurrencyCoefficientDO getCurrencyCoefficientByCurrencyId(int currencyId);

    /**
     * 根据记录号删除币种系数
     * @param orderNo  记录号
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean deleteTransactionCurrencyCoefficientByOrderNo(String orderNo);

    /**
     * 查询每个币种最近的系数
     * @param date  起始时间  非null则这个时间点之前最近的一条
     * @return  操作成功：返回币种系数集合，操作失败：返回null
     */
    List<TransactionCurrencyCoefficientDO> listTransactionCurrencyCoefficientForNew(Timestamp date);
}
