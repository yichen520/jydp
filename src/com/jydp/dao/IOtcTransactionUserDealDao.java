package com.jydp.dao;

import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;

import java.sql.Timestamp;
import java.util.List;

import java.sql.Timestamp;

/**
 * 场外交易成交记录
 * @author yk
 */
public interface IOtcTransactionUserDealDao {

    /**
     * 新增场外交易成交记录
     * @param otcTransactionUserDeal 场外交易成交记录
     * @return 新增成功：返回true; 新增失败：返回false
     */
    boolean insertOtcTransactionUserDeal(OtcTransactionUserDealDO otcTransactionUserDeal);

    /**
     * 根据记录号查询成交记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    OtcTransactionUserDealDO getOtcTransactionUsealByOrderNo(String orderNo);

    /**
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealType 交易类型（非必填）收支类型：1：买入，2：卖出，3：撤销
     * @param dealStatus 交易状态（非必填） //状态：1：待付款，2：已付款（待确认），3：已完成，4：用户取消，5：商家取消
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @return 查询成功：返回记录信息数量, 查询失败或者没有相应记录：返回0
     */
    int numberOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealType,int dealStatus, Timestamp startAddTime,
                                                 Timestamp endAddTime);

    /**
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @param pageNumber 当前页（必填）
     * @param pageSize 每页条数（必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    List<OtcTransactionUserDealVO> listOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealStatus, Timestamp startAddTime,
                                                                          Timestamp endAddTime, int pageNumber, int pageSize);

    /**
     * 根据记录号修改成交记录状态
     * @param otcOrderNo 记录号
     * @param dealStatus 原成交记录状态
     * @param changedStatus 修改后的成交记录状态
     * @param updateTime 修改时间
     * @return  修改成功：返回true; 修改失败：返回false
     */
    boolean updateDealStatusByOtcOrderNo(String otcOrderNo, int dealStatus, int changedStatus, Timestamp updateTime);

    /**
     * 根据经销商Id查询经销商成交记录
     * @param userId 经销商id (必填)
     * @param userAccount 用户账号（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @param paymentType 收款方式 （非必填）
     * @param dealType 交易类型 （非必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    int countOtcTransactionUserDeallistByDealerId(int userId, String userAccount, int currencyId,
                                                  int dealStatus, Timestamp startAddTime,Timestamp endAddTime,int paymentType,int dealType);

    /**
     * 根据经销商Id查询经销商成交记录
     * @param userId 经销商id (必填)
     * @param userAccount 用户账号（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @param paymentType 收款方式 （非必填）
     * @param dealType 交易类型 （非必填）
     * @param pageNumber 当前页（必填）
     * @param pageSize 每页条数（必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    List<OtcTransactionUserDealVO> getOtcTransactionUserDeallistByDealerId(int userId, String userAccount, int currencyId, int dealStatus, Timestamp startAddTime,
                                                                       Timestamp endAddTime,int paymentType, int dealType, int pageNumber, int pageSize);

}
