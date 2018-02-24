package com.jydp.dao;

import com.jydp.entity.DO.user.UserCurrencyDO;

/**
 * Description: 用户货币记录
 * Author: hht
 * Date: 2018-02-07 17:17
 */
public interface IUserCurrencyDao {

    /**
     * 新增用户货币记录
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean insertUserCurrency(UserCurrencyDO userCurrency);
}
