package com.jydp.service;

import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceFreezeDO;

/**
 * 后台管理员增减用户冻结余额记录
 * @author sy
 */
public interface IBackerHandleUserRecordBalanceFreezeService {
    /**
     * 新增 后台管理员增减用户冻结余额记录
     * @param backerHandleUserRecordBalanceFreeze 待新增的 后台管理员增减用户冻结余额记录
     * @return 新增成功：true，新增失败：返回false
     */
    boolean insertBackerHandleUserRecordBalanceFreeze(BackerHandleUserRecordBalanceFreezeDO backerHandleUserRecordBalanceFreeze);
}
