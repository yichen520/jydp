package com.jydp.service;

import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
public interface IUserCurrencyNumService {

    /**
     * 查询用户币数量
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    List<UserCurrencyNumDO> getUserCurrencyNumByUserId (int userId);

    /**
     * 查询用户币数量，带货币名称
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    List<BackerUserCurrencyNumDTO> getUserCurrencyNumByUserIdForBacker (int userId);

    /**
     * 根据currencyId查询用户币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    UserCurrencyNumDO getUserCurrencyNumByUserIdAndCurrencyId(int userId, int currencyId);

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return
     */
    boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList);

    /**
     * 增加用户货币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumber 增加的货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean increaseCurrencyNumber(int userId, int currencyId, double currencyNumber);

    /**
     * 减少用户货币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumber 减少的货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean reduceCurrencyNumber(int userId, int currencyId, double currencyNumber);

    /**
     * 增加用户货币冻结数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumberLock 增加的冻结货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean increaseCurrencyNumberLock(int userId, int currencyId, double currencyNumberLock);

    /**
     * 减少用户货币冻结数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumberLock 减少的冻结货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean reduceCurrencyNumberLock(int userId, int currencyId, double currencyNumberLock);

}
