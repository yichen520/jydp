package com.jydp.dao;

import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.SylUserDO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import com.jydp.entity.DTO.UserDTO;

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
    UserDO insertUser (UserDO userDO);

    /**
     * 查询用户账号
     * @param userId 用户Id
     * @return 查询成功：返回用户信息，查询失败或无数据：返回null
     */
    UserDO getUserByUserId (int userId);

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
    int countUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus,
                            int authenticationStatus, Timestamp startTime, Timestamp endTime);

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
    List<UserDO> listUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus, int authenticationStatus,
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
     * 修改用户账号实名认证状态
     * @param userId 用户Id
     * @param authenticationStatus 实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交
     * @param oldAuthenticationStatus 原实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateUserAuthenticationStatus (int userId, int authenticationStatus, int oldAuthenticationStatus);

    /**
     * 修改用户信息
     * @param userAccount 用户账户
     * @param password 新密码
     * @return 修改成功：返回true，修改失败：返回false
    */
    boolean updateUser (String userAccount, String password);

    /**
     * 忘记支付密码
     * @param userId 用户id
     * @param password 新密码（密文）
     * @return  修改成功：返回true，修改失败：返回false
     */
    boolean forgetPayPwd(int userId, String password);

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
     * 根据手机号及密码查询用户信息
     * @param phoneAreaCode 用户手机区号
     * @param phoneNumber 用户手机号
     * @param password 用户密码
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    SylUserDO getUserByPhoneAndPassword(String phoneAreaCode, String phoneNumber, String password);


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

    /**
     * 查询用户账户错误总数（定时器对账操作）
     * @param currencyId 币种id（美元）
     * @param checkAmount 可用资产最大差额（美元）
     * @param checkAmountLock 锁定资产最大差额（美元）
     * @return 查询成功：返回用户账户错误总数，查询失败：返回0
     */
    int countCheckUserAmountForTimer(int currencyId, double checkAmount, double checkAmountLock);

    /**
     * 查询用户账户错误列表信息（定时器对账操作）
     * @param currencyId 币种id（美元）
     * @param checkAmount 可用资产最大差额（美元）
     * @param checkAmountLock 锁定资产最大差额（美元）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功：返回用户账户错误列表信息，查询失败：返回null
     */
    List<UserAmountCheckDTO> listCheckUserAmountForTimer(int currencyId, double checkAmount, double checkAmountLock,
                                                         int pageNumber, int pageSize);

    /**
     * 修改用户账号支付密码状态
     * @param userId 用户Id
     * @param payPasswordStatus 支付密码状态：1：每笔交易都输入交易密码，2：每次登录只输入一次交易密码
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateUserPayPasswordStatus (int userId, int payPasswordStatus);

    /**
     * 验证用户账号和手机号是否匹配
     * @param userAccount 用户账号
     * @param phoneAreaCode 区域号
     * @param phoneNumber 手机号
     * @return 查询成功：返回用户信息，查询失败：返回null
     */
    UserDO validateUserPhoneNumber(String userAccount, String phoneAreaCode, String phoneNumber);

}
