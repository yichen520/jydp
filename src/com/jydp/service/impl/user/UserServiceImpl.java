package com.jydp.service.impl.user;

import com.jydp.dao.IUserDao;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 用户账户
 * Author: hht
 * Date: 2018-02-07 16:24
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    /** 用户账户 */
    @Autowired
    private IUserDao userDao;

    /**
     * 新增用户账号
     * @param userDO 用户账号
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertUser (UserDO userDO) {
        return userDao.insertUser(userDO);
    }

    /**
     * 查询用户账号
     * @param userId 用户Id
     * @return 查询成功：返回用户信息，查询失败或无数据：返回null
     */
    public UserDO getUserByUserId (int userId) {
        return userDao.getUserByUserId(userId);
    }

    /**
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态
     * @param oldAccountStatus 用户原来的状态
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserAccountStatus (int userId, int accountStatus, int oldAccountStatus) {
        return userDao.updateUserAccountStatus(userId, accountStatus, oldAccountStatus);
    }

}
