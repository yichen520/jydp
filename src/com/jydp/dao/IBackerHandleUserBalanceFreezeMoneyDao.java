package com.jydp.dao;

import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;

/**
 * 后台管理员增减用户冻结币记录
 * @author sy
 */
public interface IBackerHandleUserBalanceFreezeMoneyDao {

    /**
     * 新增 后台管理员增减用户冻结币记录
     * @param backerHandleUserBalanceFreezeMoney 待新增的 后台管理员增减用户冻结币记录
     * @return 新增成功：true，新增失败：返回false
     */
    boolean insertBackerHandleUserBalanceFreezeMoney(BackerHandleUserBalanceFreezeMoneyDO backerHandleUserBalanceFreezeMoney);
}
