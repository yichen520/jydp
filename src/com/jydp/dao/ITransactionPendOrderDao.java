package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.transaction.WapTransactionPendOrderDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.VO.TransactionPendOrderVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 挂单记录
 * @author hz
 *
 */
public interface ITransactionPendOrderDao {

    /**
     * 新增挂单记录
     * @param transactionPendOrderDO 待新增的挂单记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    TransactionPendOrderDO insertPendOrder(TransactionPendOrderDO transactionPendOrderDO);

    /**
     * 修改挂单记录
     * @param transactionPendOrderDO 待修改的挂单记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updatePendOrder(TransactionPendOrderDO transactionPendOrderDO);

    /**
     * 根据挂单记录号查询挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return 操作成功：返回挂单记录，操作失败：返回null
     */
    TransactionPendOrderDO getPendOrderByPendingOrderNo(String pendingOrderNo);

    /**
     * 根据用户id查询挂单记录个数
     * @param userId 用户Id
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    int countPendOrderByUserId(int userId);

    /**
     * 根据用户id分页查询挂单记录
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    List<TransactionPendOrderDO> listPendOrderByUserId(int userId, int pageNumber, int pageSize);

    /**
     * wap根据用户id分页查询挂单记录
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    List<WapTransactionPendOrderDO> listPendOrderByUserIdForWap(int userId, int pageNumber, int pageSize);

    /**
     * 修改挂单状态
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updatePendingStatus(String pendingOrderNo, int pendingStatus);

    /**
     * 查询最近num条挂单记录（价格相同的合并，用于交易中心显示）
     * @param paymentType 交易类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param num 需要查询的条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    List<TransactionPendOrderDTO> listLatestRecords(int paymentType, int currencyId, int num);

    /**
     * 查询挂单记录个数（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id（查全部为0）
     * @param paymentType 交易类型,1：买入，2：卖出（查全部为0）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销（查全部为0）
     * @param startAddTime 挂单起始时间，没有值填null
     * @param endAddTime 挂单结束时间，没有值填null
     * @param startFinishTime 完成起始时间，没有值填null
     * @param endFinishTime 完成结束时间，没有值填null
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    int countPendOrderForBack(String userAccount, int currencyId, int paymentType, int pendingStatus,
                              Timestamp startAddTime, Timestamp endAddTime,
                              Timestamp startFinishTime, Timestamp endFinishTime);

    /**
     * 分页查询挂单记录列表（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id（查全部为0）
     * @param paymentType 交易类型,1：买入，2：卖出（查全部为0）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销（查全部为0）
     * @param startAddTime 挂单起始时间，没有值填null
     * @param endAddTime 挂单结束时间，没有值填null
     * @param startFinishTime 完成起始时间，没有值填null
     * @param endFinishTime 完成结束时间，没有值填null
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    List<TransactionPendOrderDO> listPendOrderForBack(String userAccount, int currencyId, int paymentType,
                                                      int pendingStatus, Timestamp startAddTime, Timestamp endAddTime,
                                                      Timestamp startFinishTime, Timestamp endFinishTime,
                                                      int pageNumber, int pageSize);

    /**
     * 修改挂单状态为部分撤销（仅撤单用）
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param revokeNumber 撤销数量
     * @param endTime 撤销时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updatePartRevoke(String pendingOrderNo, double revokeNumber, Timestamp endTime);

    /**
     * 修改挂单状态为全部撤销（仅撤单用）
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param revokeNumber 撤销数量
     * @param endTime 撤销时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateAllRevoke(String pendingOrderNo, double revokeNumber, Timestamp endTime);

    /**
     * 查询最近的一笔正在挂单的挂单记录（仅用于匹配交易）
     * @param userId 用户Id（不根据userId时填0）
     * @param currencyId 币种Id
     * @param paymentType 交易类型,1：买入，2：卖出
     * @return 操作成功：返回挂单记录，操作失败：返回null
     */
    TransactionPendOrderDO getLastTransactionPendOrder(int userId, int currencyId, int paymentType);

    /**
     * 修改挂单状态为部分成交（仅用于匹配交易）
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param dealNumber 成交数量
     * @param restBalanceLock 剩余冻结美金
     * @param endTime 完成时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updatePartDeal(String pendingOrderNo, double dealNumber, double restBalanceLock, Timestamp endTime);

    /**
     * 修改挂单状态为全部成交（仅用于匹配交易）
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param dealNumber 成交数量
     * @param endTime 完成时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateAllDeal(String pendingOrderNo, double dealNumber, Timestamp endTime);

    /**
     * 分页查询委托记录列表（交易中心）
     * @param userId 用户id
     * @param currencyId 币种id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    List<TransactionPendOrderVO> listPendOrderForWeb(int userId, int currencyId, int pageNumber, int pageSize);

    /**
     * 分页查询委托记录列表（wap交易中心）
     * @param userId 用户id
     * @param currencyId 币种id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    List<TransactionPendOrderVO> listPendOrderForWap(int userId, int currencyId, int pageNumber, int pageSize);

}
