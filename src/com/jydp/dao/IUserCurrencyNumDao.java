package com.jydp.dao;

import com.jydp.entity.DO.user.UserCurrencyDO;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
public interface IUserCurrencyNumDao {

    /**
     * 根据用户id查询币种记录
     * @param userId 用户Id
     * @return 查询成功：返回用户币种记录信息，查询失败：返回null
     */
    List<UserCurrencyDO> getUserCurrencyByUserId (int userId);
}
