package com.jydp.service;

import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;

import java.sql.Timestamp;

/**
 * 场外交易成交记录
 * @author yk
 */
public interface IOtcTransactionUserDealService {

    /**
     * 根据记录号查询成交记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    OtcTransactionUserDealDO getOtcTransactionUsealByOrderNo(String orderNo);

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
