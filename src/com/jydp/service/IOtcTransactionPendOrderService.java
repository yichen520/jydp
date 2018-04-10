package com.jydp.service;

import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;

/**
 * 场外交易挂单记录
 * @author yk
 */
public interface IOtcTransactionPendOrderService {
    /**
     * 新增挂单记录
     * @param userId 用户Id
     * @param paymentType 收支类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param buyFee 买入手续费(卖出时填0)
     * @param pendingPrice 挂单单价
     * @param pendingNumber 挂单数量
     * @param tradePriceSum 交易总价，包括手续费(卖出时填0)
     * @return 操作成功：返回true，操作失败：返回false
     */
    OtcTransactionPendOrderDO insertPendOrder();

    /**
     * 根据记录号查询挂单记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    OtcTransactionPendOrderDO getOtcTransactionPendOrderByOrderNo(String orderNo);

}
