package com.jydp.dao;

import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.DTO.UserAmountCheckDTO;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
public interface IUserCurrencyNumDao {

    /**
     * 新增用户币账户
     * @param userCurrencyNum 用户币账户
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean insertUserCurrencyNum(UserCurrencyNumDO userCurrencyNum);

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
     * 查询用户币数量，带货币名称带货币名称(用户端)
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    List<BackerUserCurrencyNumDTO> getUserCurrencyNumByUserIdForWeb (int userId);

    /**
     * 根据currencyId查询用户币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    UserCurrencyNumDO getUserCurrencyNumByUserIdAndCurrencyId(int userId, int currencyId);

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @param userCurrencyNumList 用户币种信息表
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumList);

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

    /**
     * 查询用户币种账户错误总数（定时器对账操作）
     * @param checkAmount 可用资产最大差额（数字货币）
     * @param checkAmountLock 锁定资产最大差额（数字货币）
     * @return 查询成功：返回用户币种账户错误总数，查询失败：返回0
     */
    int countCheckUserAmountForTimer(double checkAmount, double checkAmountLock);

    /**
     * 查询用户币种账户错误列表信息（定时器对账操作）
     * @param checkAmount 可用资产最大差额（数字货币）
     * @param checkAmountLock 锁定资产最大差额（数字货币）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功：返回用户币种账户错误列表信息，查询失败：返回null
     */
    List<UserAmountCheckDTO> listCheckUserAmountForTimer(double checkAmount, double checkAmountLock,
                                                         int pageNumber, int pageSize);
}
