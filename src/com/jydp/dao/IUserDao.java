package com.jydp.dao;

import com.jydp.entity.DO.user.UserDO;

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
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态
     * @param oldAccountStatus 用户原来的状态
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

}
