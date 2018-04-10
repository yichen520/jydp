package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DTO.UserPaymentTypeDTO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;

import java.sql.Timestamp;
import java.util.List;

import java.sql.Timestamp;

/**
 * 场外交易成交记录
 * @author yk
 */
public interface IOtcTransactionUserDealService {

    /**
     * 新增成交记录
     * @param otcPendingOrderNo 挂单记录号
     * @param userId 买方用户Id
     * @param dealerId 卖方用户Id
     * @param typeId 收款方式Id
     * @param userAccount 用户帐号
     * @param dealType 收支类型：1：买入，2：卖出，3：撤销
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param pendingRatio 挂单比例
     * @param currencyNumber 成交数量
     * @param currencyTotalPrice 成交总价
     * @param pendTime 挂单时间
     * @param paymentType 收款方式标识：1：银行卡，2：支付宝，3：微信
     * @param userPaymentType 用户收款方式
     * @return 新增成功：返回记录信息, 新增失败：返回null
     */
    JsonObjectBO insertOtcTransactionUserDeal(String otcPendingOrderNo, int userId, int dealerId, int typeId, String userAccount,
                                              int dealType, int currencyId, String currencyName, double pendingRatio, double currencyNumber,
                                              double currencyTotalPrice, Timestamp pendTime, int paymentType, UserPaymentTypeDTO userPaymentType);

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
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endddTime 申请结束时间（非必填）
     * @return 查询成功：返回记录信息数量, 查询失败或者没有相应记录：返回0
     */
    int numberOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealStatus, Timestamp startAddTime,
                                                                   Timestamp endddTime);

    /**
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endddTime 申请结束时间（非必填）
     * @param pageNumber 当前页（必填）
     * @param pageSize 每页条数（必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    List<OtcTransactionUserDealVO> listOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealStatus, Timestamp startAddTime,
                                                                          Timestamp endddTime, int pageNumber, int pageSize);

    /**
     * 用户确认收款
     * @param otcTransactionUserDeal 记录信息
     * @return 确认成功：返回true，确认失败：返回false
     */
    boolean userConfirmationOfReceipts(OtcTransactionUserDealDO otcTransactionUserDeal);

    /**
     * 根据记录号修改成交记录状态
     * @param otcOrderNo 记录号
     * @param dealStatus 原成交记录状态
     * @param changedStatus 修改后的成交记录状态
     * @param updateTime 修改时间
     * @return  修改成功：返回true; 修改失败：返回false
     */
    boolean updateDealStatusByOtcOrderNo(String otcOrderNo, int dealStatus, int changedStatus,Timestamp updateTime);

    /**
     * 经销商回购币-确认收货
     * @param otcOrderNo  成交记录号
     * @param otcPendingOrderNo 挂单记录号
     * @param userId 操作用户Id
     * @return  修改成功：返回true; 修改失败：返回false
     */
    boolean dealerConfirmTakeForBuyBack(String otcOrderNo, String otcPendingOrderNo, int userId);

    /**
     * 经销商出售币-确认收款
     * @param otcTransactionUserDeal 挂单记录
     * @param userId 操作用户Id
     * @return  修改成功：返回true; 修改失败：返回false
     */
    boolean dealerConfirmTakeForSellCoin(OtcTransactionUserDealDO otcTransactionUserDeal, int userId);

}
