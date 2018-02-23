package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;

/**
 * Description: 用户账号
 * Author: hht
 * Date: 2018-02-07 16:24
 */
public interface IUserService {

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
     * 忘记密码
     * @param userAccount 用户账户
     * @param password 新密码
     * @return  修改成功：返回true，修改失败：返回false
     */
    boolean forgetPwd(String userAccount, String password);

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
     * 验证用户信息合法性
     * @param userAccount 用户名
     * @param password 密码
     * @param userPhone 用户手机号
     * @param refereeAccount 推荐人账号
     * @return 查询成功：返回验证结果; 查询失败：返回null
     */
    JsonObjectBO validateUserInfo(String userAccount, String password, String userPhone, String refereeAccount);

    /**
     * 用户注册
     * @param userDO 用户注册信息
     * @return 操作成功：返回true; 操作失败：返回false
     */
    boolean register(UserDO userDO);
}
