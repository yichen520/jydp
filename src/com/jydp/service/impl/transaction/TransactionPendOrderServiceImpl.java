package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionPendOrderDao;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 挂单记录
 * @author hz
 *
 */
@Service("transactionPendOrderService")
public class TransactionPendOrderServiceImpl implements ITransactionPendOrderService{

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderDao transactionPendOrderDao;

    /**
     * 新增挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param userId 用户Id
     * @param paymentType 收支类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param pendingPrice 挂单单价
     * @param pendingNumber 挂单数量
     * @param dealNumber 成交数量
     * @param pendingStatus 挂单状态，1：挂单中，2：部分完成，3：已完成，4：已撤销
     * @param remark 备注
     * @param addTime 添加时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertPendOrder(String pendingOrderNo, int userId, int paymentType, int currencyId,
                                   String currencyName, double pendingPrice, double pendingNumber,
                                   double dealNumber, int pendingStatus, String remark, Timestamp addTime){
        TransactionPendOrderDO transactionPendOrder = new TransactionPendOrderDO();
        transactionPendOrder.setPendingOrderNo(pendingOrderNo);
        transactionPendOrder.setUserId(userId);
        transactionPendOrder.setPaymentType(paymentType);
        transactionPendOrder.setCurrencyId(currencyId);
        transactionPendOrder.setCurrencyName(currencyName);
        transactionPendOrder.setPendingPrice(pendingPrice);
        transactionPendOrder.setPendingNumber(pendingNumber);
        transactionPendOrder.setDealNumber(dealNumber);
        transactionPendOrder.setPendingStatus(pendingStatus);
        transactionPendOrder.setRemark(remark);
        transactionPendOrder.setAddTime(addTime);

        return transactionPendOrderDao.insertPendOrder(transactionPendOrder);
    }

    /**
     * 修改挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param dealNumber 成交数量
     * @param pendingStatus 挂单状态，1：挂单中，2：部分完成，3：已完成，4：已撤销
     * @param remark 备注
     * @param endTime 完成时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePendOrder(String pendingOrderNo, double dealNumber, int pendingStatus, String remark, Timestamp endTime){
        TransactionPendOrderDO transactionPendOrder = new TransactionPendOrderDO();
        transactionPendOrder.setPendingOrderNo(pendingOrderNo);
        transactionPendOrder.setDealNumber(dealNumber);
        transactionPendOrder.setPendingStatus(pendingStatus);
        transactionPendOrder.setRemark(remark);
        transactionPendOrder.setEndTime(endTime);

        return transactionPendOrderDao.updatePendOrder(transactionPendOrder);
    }

    /**
     * 根据挂单记录号查询挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return 操作成功：返回挂单记录，操作失败：返回null
     */
    public TransactionPendOrderDO getPendOrderByPendingOrderNo(String pendingOrderNo){
        return transactionPendOrderDao.getPendOrderByPendingOrderNo(pendingOrderNo);
    }

    /**
     * 根据用户id查询挂单记录个数
     * @param userId 用户Id
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    public int countPendOrderByUserId(int userId){
        return transactionPendOrderDao.countPendOrderByUserId(userId);
    }

    /**
     * 根据用户id分页查询挂单记录
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listPendOrderByUserId(int userId, int pageNumber, int pageSize){
        return transactionPendOrderDao.listPendOrderByUserId(userId, pageNumber, pageSize);
    }

    /**
     * 修改挂单状态
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingStatus 挂单状态，1：挂单中，2：部分完成，3：已完成，4：已撤销
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePendingStatus(String pendingOrderNo, int pendingStatus){
        return transactionPendOrderDao.updatePendingStatus(pendingOrderNo,pendingStatus);
    }

    /**
     * 查询最近num条挂单记录（价格相同的合并，用于交易中心显示）
     * @param paymentType 交易类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param num 需要查询的条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listLatestRecords(int paymentType, int currencyId, int num){
        return transactionPendOrderDao.listLatestRecords(paymentType, currencyId, num);
    }

    /**
     * 查询挂单记录个数（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id
     * @param paymentType 交易类型,1：买入，2：卖出
     * @param pendingStatus 挂单状态，1：挂单中，2：部分完成，3：已完成，4：已撤销
     * @param startAddTime 挂单起始时间
     * @param endAddTime 挂单结束时间
     * @param startFinishTime 完成起始时间
     * @param endFinishTime 完成结束时间
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    public int countPendOrderForBack(String userAccount, int currencyId, int paymentType, int pendingStatus,
                              Timestamp startAddTime, Timestamp endAddTime,
                              Timestamp startFinishTime, Timestamp endFinishTime) {
        return transactionPendOrderDao.countPendOrderForBack(userAccount, currencyId, paymentType, pendingStatus,
                startAddTime, endAddTime, startFinishTime, endFinishTime);
    }

    /**
     * 分页查询挂单记录列表（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id
     * @param paymentType 交易类型,1：买入，2：卖出
     * @param pendingStatus 挂单状态，1：挂单中，2：部分完成，3：已完成，4：已撤销
     * @param startAddTime 挂单起始时间
     * @param endAddTime 挂单结束时间
     * @param startFinishTime 完成起始时间
     * @param endFinishTime 完成结束时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listPendOrderForBack(String userAccount, int currencyId, int paymentType,
                                                      int pendingStatus, Timestamp startAddTime, Timestamp endAddTime,
                                                      Timestamp startFinishTime, Timestamp endFinishTime,
                                                      int pageNumber, int pageSize){
        return transactionPendOrderDao.listPendOrderForBack(userAccount, currencyId, paymentType, pendingStatus, startAddTime, endAddTime,
                startFinishTime, endFinishTime, pageNumber, pageSize);
    }

}
