package com.jydp.service;

import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;

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
}
