package com.jydp.service;

import com.jydp.entity.VO.TransactionUserDealVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
public interface ITransactionUserDealService {

    /**
     * 查询用户成交记录(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionUserDealVO> getTransactionUserDeallist(int userId, int pageNumber, int pageSize);


    /**
     * wap查询用户成交记录(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionUserDealVO> getTransactionUserDealListForWap(int userId, int pageNumber, int pageSize);

    /**
     * 新增成交记录
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingOrderNo  挂单记录号
     * @param userId  用户Id
     * @param userAccount  用户账户
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种Id
     * @param currencyName  货币名称
     * @param transactionPrice 成交单价
     * @param currencyNumber  成交数量
     * @param feeNumber  成交费率
     * @param currencyTotalPrice  成交总价
     * @param remark  备注
     * @param pendTime  挂单时间
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionUserDeal(String orderNo, String pendingOrderNo, int userId, String userAccount, int paymentType,
                                      int currencyId, String currencyName, double transactionPrice, double currencyNumber,
                                      double feeNumber, double currencyTotalPrice, String remark, Timestamp pendTime, Timestamp addTime);

    /**
     * 查询成交记录条数(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种id,查询全部填0
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @return  操作成功：返回成交记录条数，操作失败：返回0
     */
    int countTransactionUserDealForBack(String userAccount, int paymentType, int currencyId,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime);

    /**
     * 查询成交记录(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种id,查询全部填0
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录，操作失败：返回null
     */
    List<TransactionUserDealVO> listTransactionUserDealForBack(String userAccount, int paymentType, int currencyId,
                                                               Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime,
                                                               int pageNumber, int pageSize);

    /**
     * 根据挂单记录号查询成交记录条数
     * @param pendNo  挂单记录号
     * @param userId  用户Id
     * @return  操作成功：返回成交记录条数，操作失败:返回0
     */
    int countTransactionUserDealByPendNo(String pendNo, int userId);

    /**
     * 根据挂单记录号查询成交记录
     * @param pendNo  挂单记录号
     * @param userId  用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录集合，操作失败:返回null
     */
    List<TransactionUserDealVO> listTransactionUserDealByPendNo(String pendNo, int userId, int pageNumber, int pageSize);


    /**
     * wap端根据挂单记录号查询成交记录
     * @param pendNo  挂单记录号
     * @param userId  用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录集合，操作失败:返回null
     */
    List<TransactionUserDealVO> listTransactionUserDealByPendNoForWap(String pendNo, int userId, int pageNumber, int pageSize);

    /**
     * 查询用户成交记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数，查询失败：返回0
     */
    int countUserDealForWeb(int userId);

    /**
     * wap端查询用户成交记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数，查询失败：返回0
     */
    int countUserDealForWap(int userId);
}
