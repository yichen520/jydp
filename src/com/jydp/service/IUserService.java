package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

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
     * 忘记密码
     * @param userAccount 用户账户
     * @param password 新密码
     * @return  修改成功：返回true，修改失败：返回false
     */
    boolean forgetPwd(String userAccount, String password);

    /**
     * 忘记支付密码
     * @param userId 用户id
     * @param payPassword 新密码（密文）
     * @return  修改成功：返回true，修改失败：返回false
     */
    boolean forgetPayPwd(int userId, String payPassword);

    /**
     * 修改绑定手机号
     * @param userId 用户id
     * @param areaCode  手机区号
     * @param phone  手机号
     * @return  修改成功：返回true，修改失败：返回false
     */
    boolean updatePhone(int userId, String areaCode, String phone);

    /**
     * 验证用户登录
     * @param userAccount 用户账号
     * @param password 账号密码（密文）
     * @return 验证成功：返回用户信息，验证失败：返回null
     */
    UserDO validateUserLogin(String userAccount, String password);

    /**
     * 验证用户支付密码
     * @param userAccount 用户账号
     * @param payPassword 支付密码（密文）
     * @return 验证成功：返回true，验证失败：返回false
     */
    boolean validateUserPay(String userAccount, String payPassword);

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
     * @return 查询成功：返回验证结果; 查询失败：返回null
     */
    JsonObjectBO validateUserInfo(String userAccount, String password, String userPhone);

    /**
     * 用户注册
     * @param userDO 用户注册信息
     * @return 操作成功：返回true; 操作失败：返回false
     */
    boolean register(UserDO userDO);

    /**
     * 增加用户余额（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param balanceNumber 增加的账户金额
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean addBalanceNumberForBack(int userId, String userAccount, double balanceNumber,
                                           int backerId, String backerAccount, String remarks, String ipAddress);

    /**
     * 减少用户余额（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param balanceNumber 减少的账户金额
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean reduceBalanceNumberForBack(int userId, String userAccount, double balanceNumber,
                                              int backerId, String backerAccount, String remarks, String ipAddress);
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
