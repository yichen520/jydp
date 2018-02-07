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

}
