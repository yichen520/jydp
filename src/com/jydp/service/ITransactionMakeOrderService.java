package com.jydp.service;

import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 做单记录
 * @author fk
 *
 */
public interface ITransactionMakeOrderService {

    /**
     * 新增做单记录
     * @param orderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param currencyNumber 成交数量
     * @param currencyTotalPrice 成交价格
     * @param backerAccount 后台管理员帐号
     * @param ipAddress 操作时的ip地址
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param remark 备注
     * @param executeTime 执行时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertMakeOrder(String orderNo, int currencyId, String currencyName, double currencyNumber,
                            double currencyTotalPrice, String backerAccount, String ipAddress, int executeStatus,
                            String remark, Timestamp executeTime, Timestamp addTime);

    /**
     * 根据记录号查询做单记录
     * @param orderNo  记录号
     * @return  操作成功：返回做单记录，操作失败：返回null
     */
    TransactionMakeOrderDO getTransactionMakeOrderByOrderNo(String orderNo);

    /**
     * 查询做单记录个数(后台)
     * @param orderNo 批次号
     * @param currencyId 币种Id,查询全部填0
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    int countTransactionMakeOrderForBack(String orderNo, int currencyId, int executeStatus, Timestamp startExecuteTime, Timestamp endExecuteTime);

    /**
     * 查询做单记录集合(后台)
     * @param orderNo 批次号
     * @param currencyId 币种Id,查询全部填0
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    List<TransactionMakeOrderDO> listTransactionMakeOrderForBack(String orderNo, int currencyId, int executeStatus,
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
     * 执行多条做单
     * @param orderNoList 记录号集合
     * @return  操作成功，返回true，操作失败，返回false
     */
    boolean executeMakeOrderMore(List<String> orderNoList);

    /**
     * 批量修改记录号状态
     * @param orderNoList  记录号集合
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param olExecuteStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @return  操作成功：true，操作失败：返回false
     */
    boolean updateMakeOrderExecuteStatusByOrderNoList(List<String> orderNoList, int executeStatus, int olExecuteStatus);

    /**
     * 根据记录号删除做单记录
     * @param orderNo 记录号
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean deleteMakeOrderByOrderNo(String orderNo);

    /**
     * 根据执行状态查询做单记录
     * @param executeStatus  执行状态
     * @param size  查询条数
     * @return  操作成功：返回做单记录集合，操作失败：返回null
     */
    List<TransactionMakeOrderDO> listMakeOrderByExecuteStatus(int executeStatus, int size);
}
