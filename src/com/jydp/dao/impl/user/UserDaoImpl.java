package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserDao;
import com.jydp.entity.DO.user.UserDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 用户帐号
 * Author: hht
 * Date: 2018-02-07 16:01
 */
@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户账号
     * @param userDO 用户账号
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertUser (UserDO userDO) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("User_insertUser", userDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询用户账号
     * @param userId 用户Id
     * @return 查询成功：返回用户信息，查询失败或无数据：返回null
     */
    public UserDO getUserByUserId (int userId) {
        UserDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("User_getUserByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态
     * @param oldAccountStatus 用户原来的状态
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserAccountStatus (int userId, int accountStatus, int oldAccountStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("accountStatus", accountStatus);
        map.put("oldAccountStatus", oldAccountStatus);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updateUserAccountStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改成功：返回true，修改失败：返回false
     */
    @Override
    public boolean updateUser(UserDO user) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("User_updateUser",user);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证用户登录
     * @param userAccount 用户账号
     * @param password 账号密码（密文）
     * @return 验证成功：返回用户信息，验证失败：返回null
     */
    @Override
    public UserDO validateUserLogin(String userAccount, String password) {
        UserDO user = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("password", password);

        try {
            user = sqlSessionTemplate.selectOne("User_validateUserLogin",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return user;
    }

    /**
     * 根据用户账号查询用户信息
     * @param userAccount 用户账号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    @Override
    public UserDO getUserByUserAccount(String userAccount) {
        UserDO user = null;

        try {
            user = sqlSessionTemplate.selectOne("User_getUserByUserAccount",userAccount);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return user;
    }
}
