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
     * @param orderNo 批次号
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    int countTransactionMakeOrderForBack(String orderNo, String currencyName, int executeStatus, Timestamp startExecuteTime, Timestamp endExecuteTime);

    /**
     * 查询做单记录集合(后台)
     * @param orderNo 批次号
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    List<TransactionMakeOrderDO> listTransactionMakeOrderForBack(String orderNo, String currencyName, int executeStatus,
                                                                 Timestamp startExecuteTime, Timestamp endExecuteTime, int pageNumber, int pageSize);

    /**
     * 批量新增做单记录
     * @param transactionMakeOrderList  做单记录集合
     * @return  操作成功，返回true，操作失败，返回false
     */
    boolean insertTransactionMakeOrderList(List<TransactionMakeOrderDO> transactionMakeOrderList);

    /**
     * 修改记录执行状态
     * @param orderNo  记录号
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param olExecuteStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @return  操作成功，返回true，操作失败，返回false
     */
    boolean updateOrderExecuteStatusByOrderNo(String orderNo, int executeStatus, int olExecuteStatus);

    /**
     * 批量修改记录号状态
     * @param orderNoList  记录号集合
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param olExecuteStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @return  操作成功：true，操作失败：返回false
     */
    boolean updateMakeOrderExecuteStatusByOrderNoList(List<String> orderNoList, int executeStatus, int olExecuteStatus);

    /**
     * 批量根据记录号查询做单记录
     * @param orderNoList  记录号集合
     * @return  操作成功：返回做单记录集合，操作失败：返回null
     */
    List<TransactionMakeOrderDO> listTransactionMakeOrderByOrderNoList(List<String> orderNoList);

    /**
     * 根据记录号删除做单记录
     * @param orderNo 记录号
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean deleteMakeOrderByOrderNo(String orderNo);
}
