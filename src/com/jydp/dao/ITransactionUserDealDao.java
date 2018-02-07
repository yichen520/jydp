package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionUserDealDO;

import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
public interface ITransactionUserDealDao {

    /**
     * 查询用户成交记录
     * @param userId 用户Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionUserDealDO> getTransactionUserDeallist(int userId);
}
