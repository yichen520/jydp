package com.jydp.dao;

import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
/**
 * 用户标识经销商相关操作
 *
 * @author lgx
 */
public interface IOtcTransactionPendOrderDao {

    /**
     * 新增 场外交易挂单记录
     * @param otcTransactionPendOrderDO 待新增的 场外交易挂单记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertOtcTransactionPendOrder(OtcTransactionPendOrderDO otcTransactionPendOrderDO);
}
