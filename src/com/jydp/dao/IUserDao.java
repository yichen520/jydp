package com.jydp.dao;

import com.jydp.entity.DO.user.UserDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Description: 用户帐号
 * Author: hht
 * Date: 2018-02-07 16:02
 */
public interface IUserDao {

    /**
     * 新增用户账号
     * @param userDO 用户账号
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertUser (UserDO userDO);

    /**
     * 查询用户账号
     * @param userId 用户Id
     * @return 查询成功：返回用户信息，查询失败或无数据：返回null
     */
    UserDO getUserByUserId (int userId);

    /**
     * 查询用户账号总数（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态（可为null）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 查询成功：返回用户账户总数，查询失败：返回0
     */
    int countUserForBacker (String userAccount, String phoneNumber, int accountStatus, Timestamp startTime, Timestamp endTime);

    /**
     * 查询用户账号列表（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态（可为null）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 查询成功：返回用户账户列表，查询失败：返回null
     */
    List<UserDO> listUserForBacker (String userAccount, String phoneNumber, int accountStatus,
                                           Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize);

    /**
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态，1：启用，2：禁用，-1：删除
     * @param oldAccountStatus 用户原来的状态，1：启用，2：禁用，-1：删除
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateUserAccountStatus (int userId, int accountStatus, int oldAccountStatus);

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateUser (UserDO user);

    /**
     * 验证用户登录
     * @param userAccount 用户账号
     * @param password 账号密码（密文）
     * @return 验证成功：返回用户信息，验证失败：返回null
     */
    UserDO validateUserLogin(String userAccount, String password);

    /**
     * 根据用户账号查询用户信息
     * @param userAccount 用户账号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    UserDO getUserByUserAccount(String userAccount);

    /**
     * 根据手机号查询用户信息
     * @param phoneNumber 用户手机号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    UserDO getUserByPhone(String phoneNumber);

    /**
     * 增加用户账户金额
     * @param userId 用户Id
     * @param userBalance 可用资产（增加的值）
     * @param userBalanceLock 锁定资产（增加的值）
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateAddUserAmount(int userId, double userBalance, double userBalanceLock);

    /**
     * 减少用户的可用资产
     * @param userId 用户Id
     * @param userBalance 可用资产(减少的值)
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateReduceUserBalance(int userId, double userBalance);

    /**
     * 减少用户的锁定资产
     * @param userId 用户Id
     * @param userBalanceLock 锁定资产(减少的值)
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateReduceUserBalanceLock(int userId, double userBalanceLock);

}
