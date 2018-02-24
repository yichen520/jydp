package com.jydp.dao;

import com.jydp.entity.DO.user.UserCurrencyNumDO;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
public interface IUserCurrencyNumDao {

    /**
     * 查询用户币数量
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    List<UserCurrencyNumDO> getUserCurrencyNumByUserId (int userId);

    /**
     * 根据currencyId查询用户币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    UserCurrencyNumDO getUserCurrencyNumByUserIdAndCurrencyId(int userId, int currencyId);

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList);

    /**
     * 增加用户货币数量
     * @param userCurrencyNum 增加信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean increaseCurrencyNumber(UserCurrencyNumDO userCurrencyNum);

    /**
     * 减少用户货币数量
     * @param userCurrencyNum 减少信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean reduceCurrencyNumber(UserCurrencyNumDO userCurrencyNum);

    /**
     * 增加用户货币冻结数量
     * @param userCurrencyNum 增加信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean increaseCurrencyNumberLock(UserCurrencyNumDO userCurrencyNum);

    /**
     * 减少用户货币冻结数量
     * @param userCurrencyNum 减少信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean reduceCurrencyNumberLock(UserCurrencyNumDO userCurrencyNum);

}
