package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.entity.VO.TransactionUserDealVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
public interface ITransactionUserDealDao {

    /**
     * 查询用户成交记录(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionUserDealDO> getTransactionUserDeallist(int userId, int pageNumber, int pageSize);

    /**
     * 新增成交记录
     * @param transactionUserDeal  成交记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionUserDeal(TransactionUserDealDO transactionUserDeal);

    /**
     * 查询成交记录条数(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @return  操作成功：返回成交记录条数，操作失败：返回0
     */
    int countTransactionUserDealForBack(String userAccount, int paymentType, String currencyName,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime);

    /**
     * 查询成交记录(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录，操作失败：返回null
     */
    List<TransactionUserDealVO> listTransactionUserDealForBack(String userAccount, int paymentType, String currencyName,
                                                               Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime,
                                                               int pageNumber, int pageSize);
    /**
     * 根据挂单记录号查询成交记录条数
     * @param pendNo  挂单记录号
     * @return  操作成功：返回成交记录条数，操作失败:返回0
     */
    int countTransactionUserDealByPendNo(String pendNo);

    /**
     * 根据挂单记录号查询成交记录
     * @param pendNo  挂单记录号
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录集合，操作失败:返回null
     */
    List<TransactionUserDealDO> listTransactionUserDealByPendNo(String pendNo, int pageNumber, int pageSize);

    /**
     * 查询用户成交记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数，查询失败：返回0
     */
    int countUserDealForWeb(int userId);
}
