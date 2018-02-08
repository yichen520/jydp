package com.jydp.dao;

import com.jydp.entity.DO.user.UserBalanceDO;

import java.util.List;

/**
 * Description:用户账户记录
 * Author: hht
 * Date: 2018-02-07 15:29
 */
public interface IUserBalanceDao {

    /**
     * 新增用户账户记录
     * @param userBalanceDO 用户账户记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertUserBalance(UserBalanceDO userBalanceDO);

    /**
     * 查询用户账户记录
     * @param orderNo 记录号
     * @return 查询成功：返回用户账户记录，查询失败或无数据：返回null
     */
    UserBalanceDO getUserBalanceByOrderNo(String orderNo);

    /**
     * 查询用户账户记录列表
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户账户记录列表；查询失败：返回null
     */
    List<UserBalanceDO> getUserBalancelist(int userId, int pageNumber, int pageSize);

}
