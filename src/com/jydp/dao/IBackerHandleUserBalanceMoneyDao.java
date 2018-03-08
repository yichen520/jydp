package com.jydp.dao;

import com.jydp.entity.DO.back.BackerHandleUserBalanceMoneyDO;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 */
public interface IBackerHandleUserBalanceMoneyDao {

    /**
     * 新增 后台管理员增减用户可用币记录
     * @param backerHandleUserBalanceMoney 待新增的 后台管理员增减用户可用币记录
     * @return 新增成功：true，新增失败：返回false
     */
    boolean insertBackerHandleUserBalanceMoney(BackerHandleUserBalanceMoneyDO backerHandleUserBalanceMoney);
}
