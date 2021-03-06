package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserDao;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.SylUserDO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import com.jydp.entity.DTO.UserDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
     * @return 新增成功：返回用户实体信息，新增失败：返回false
     */
    public UserDO insertUser(UserDO userDO) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("User_insertUser", userDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        if (result > 0) {
            return userDO;
        } else {
            return null;
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
     * 查询用户账号总数（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneAreaCode 手机号区号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态，1：启用，2：禁用，查询全部填0
     * @param authenticationStatus 实名认证状态，1：待审核，2：审核通过，3：审核拒绝， 4：未提交，查询全部填0
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 查询成功：返回用户账户总数，查询失败：返回0
     */
    public int countUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber,
                                   int accountStatus, int authenticationStatus, Timestamp startTime, Timestamp endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("phoneAreaCode", phoneAreaCode);
        map.put("phoneNumber", phoneNumber);
        map.put("accountStatus", accountStatus);
        map.put("authenticationStatus", authenticationStatus);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("User_countUserForBacker", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户账号列表（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneAreaCode 手机号区号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态，1：启用，2：禁用，查询全部填0
     * @param authenticationStatus 实名认证状态，1：待审核，2：审核通过，3：审核拒绝， 4：未提交，查询全部填0
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 查询成功：返回用户账户列表，查询失败：返回null
     */
    public List<UserDO> listUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus, int authenticationStatus,
                                           Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("phoneAreaCode", phoneAreaCode);
        map.put("phoneNumber", phoneNumber);
        map.put("accountStatus", accountStatus);
        map.put("authenticationStatus", authenticationStatus);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<UserDO> result = null;
        try {
            result = sqlSessionTemplate.selectList("User_listUserForBacker", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态，1：启用，2：禁用，-1：删除
     * @param oldAccountStatus 用户原来的状态，1：启用，2：禁用，-1：删除
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
     * 修改用户账号实名认证状态
     * @param userId 用户Id
     * @param authenticationStatus 实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交
     * @param oldAuthenticationStatus 原实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserAuthenticationStatus (int userId, int authenticationStatus, int oldAuthenticationStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("authenticationStatus", authenticationStatus);
        map.put("oldAuthenticationStatus", oldAuthenticationStatus);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updateUserAuthenticationStatus", map);
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
     * @param userAccount 用户账户
     * @param password 新密码
     * @return 修改成功：返回true，修改失败：返回false
     */
    @Override
    public boolean updateUser(String userAccount, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("password", password);

        int result = 0;

        try {
            result = sqlSessionTemplate.update("User_updateUser", map);
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
     * 忘记支付密码
     * @param userId 用户id
     * @param payPassword 新密码（密文）
     * @return  修改成功：返回true，修改失败：返回false
     */
    public boolean forgetPayPwd(int userId, String payPassword){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("payPassword", payPassword);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_forgetPayPwd", map);
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
     * 修改绑定手机号
     * @param userId 用户id
     * @param areaCode  手机区号
     * @param phone  手机号
     * @return  修改成功：返回true，修改失败：返回false
     */
    public boolean updatePhone(int userId, String areaCode, String phone){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("areaCode", areaCode);
        map.put("phone", phone);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updatePhone", map);
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
     * 验证用户支付密码
     * @param userAccount 用户账号
     * @param payPassword 支付密码（密文）
     * @return 验证成功：返回true，验证失败：返回false
     */
    public boolean validateUserPay(String userAccount, String payPassword){
        int result = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("payPassword", payPassword);

        try {
            result = sqlSessionTemplate.selectOne("User_validateUserPay",map);
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

    /**
     * 根据手机号查询用户信息
     * @param phoneNumber 用户手机号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    @Override
    public UserDO getUserByPhone(String phoneNumber) {
        UserDO user = null;

        try {
            user = sqlSessionTemplate.selectOne("User_getUserByPhone",phoneNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return user;
    }

    /**
     * 根据手机号及密码查询用户信息
     * @param phoneAreaCode 用户手机区号
     * @param phoneNumber 用户手机号
     * @param password 用户密码
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    public SylUserDO getUserByPhoneAndPassword(String phoneAreaCode, String phoneNumber, String password){
        Map<String,Object> map = new HashMap<>();
        map.put("phoneAreaCode", phoneAreaCode);
        map.put("phoneNumber", phoneNumber);
        map.put("password", password);

        SylUserDO user = null;
        try {
            user = sqlSessionTemplate.selectOne("User_getUserByPhoneAndPassword",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return user;
    }

    /**
     * 增加用户账户金额
     * @param userId 用户Id
     * @param userBalance 可用资产（增加的值）
     * @param userBalanceLock 锁定资产（增加的值）
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateAddUserAmount(int userId, double userBalance, double userBalanceLock){
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userBalance", userBalance);
        map.put("userBalanceLock", userBalanceLock);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updateAddUserAmount", map);
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
     * 减少用户的可用资产
     * @param userId 用户Id
     * @param userBalance 可用资产(减少的值)
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateReduceUserBalance(int userId, double userBalance){
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userBalance", userBalance);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updateReduceUserBalance", map);
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
     * 减少用户的锁定资产
     * @param userId 用户Id
     * @param userBalanceLock 锁定资产(减少的值)
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateReduceUserBalanceLock(int userId, double userBalanceLock){
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userBalanceLock", userBalanceLock);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updateReduceUserBalanceLock", map);
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
     * 查询用户账户错误总数（定时器对账操作）
     * @param currencyId 币种id（美元）
     * @param checkAmount 可用资产最大差额（美元）
     * @param checkAmountLock 锁定资产最大差额（美元）
     * @return 查询成功：返回用户账户错误总数，查询失败：返回0
     */
    public int countCheckUserAmountForTimer(int currencyId, double checkAmount, double checkAmountLock) {
        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("checkAmount", checkAmount);
        map.put("checkAmountLock", checkAmountLock);

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("User_countCheckUserAmountForTimer", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户账户错误列表信息（定时器对账操作）
     * @param currencyId 币种id（美元）
     * @param checkAmount 可用资产最大差额（美元）
     * @param checkAmountLock 锁定资产最大差额（美元）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功：返回用户账户错误列表信息，查询失败：返回null
     */
    public List<UserAmountCheckDTO> listCheckUserAmountForTimer(int currencyId, double checkAmount, double checkAmountLock,
                                                         int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("checkAmount", checkAmount);
        map.put("checkAmountLock", checkAmountLock);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<UserAmountCheckDTO> result = null;
        try {
            result = sqlSessionTemplate.selectList("User_listCheckUserAmountForTimer", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 修改用户账号支付密码状态
     * @param userId 用户Id
     * @param payPasswordStatus 支付密码状态：1：每笔交易都输入交易密码，2：每次登录只输入一次交易密码
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserPayPasswordStatus (int userId, int payPasswordStatus){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("payPasswordStatus", payPasswordStatus);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("User_updateUserPayPasswordStatus", map);
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
     * 验证用户账号和手机号是否匹配
     * @param userAccount 用户账号
     * @param phoneAreaCode 区域号
     * @param phoneNumber 手机号
     * @return 查询成功：返回用户信息，查询失败：返回null
     */
    @Override
    public UserDO validateUserPhoneNumber(String userAccount, String phoneAreaCode, String phoneNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount",userAccount);
        map.put("phoneAreaCode",phoneAreaCode);
        map.put("phoneNumber",phoneNumber);

        UserDO user = null;
        try {
            user = sqlSessionTemplate.selectOne("User_validateUserPhoneNumber",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return user;
    }
}
