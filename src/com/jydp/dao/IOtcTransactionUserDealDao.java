package com.jydp.dao;

import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;

/**
 * 场外交易成交记录
 * @author yk
 */
public interface IOtcTransactionUserDealDao {
    /**
     * 根据记录好查询成交记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    OtcTransactionUserDealDO getOtcTransactionUsealBytcOrderNo(String orderNo);
}
