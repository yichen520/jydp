package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 做单记录
 * @author fk
 *
 */
public interface ITransactionMakeOrderDao {

    /**
     * 新增做单记录
     * @param transactionMakeOrder 做单记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertMakeOrder(TransactionMakeOrderDO transactionMakeOrder);

    /**
     * 根据记录号查询做单记录
     * @param orderNo  记录号
     * @return  操作成功：返回做单记录，操作失败：返回null
     */
    TransactionMakeOrderDO getTransactionMakeOrderByOrderNo(String orderNo);

    /**
     * 查询做单记录个数(后台)
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param paymentType 操作类型
     * @param backerAccount 后台管理员帐号
     * @param startAddTime 起始生成时间
     * @param endAddTime 结束生成时间
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    int countTransactionMakeOrderForBack(String currencyName, int executeStatus, int paymentType, String backerAccount,
                                         Timestamp startAddTime, Timestamp endAddTime,
                                         Timestamp startExecuteTime, Timestamp endExecuteTime);

    /**
     * 查询做单记录集合(后台)
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param paymentType 操作类型
     * @param backerAccount 后台管理员帐号
     * @param startAddTime 起始生成时间
     * @param endAddTime 结束生成时间
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    List<TransactionMakeOrderDO> listTransactionMakeOrderForBack(String currencyName, int executeStatus, int paymentType, String backerAccount,
                                                                 Timestamp startAddTime, Timestamp endAddTime,
                                                                 Timestamp startExecuteTime, Timestamp endExecuteTime,
                                                                 int pageNumber, int pageSize);
}
