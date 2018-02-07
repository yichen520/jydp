package com.jydp.service;

import com.jydp.entity.DO.user.UserBalanceDO;

/**
 * Description:用户认证记录
 * Author: hht
 * Date: 2018-02-07 15:42
 */
public interface UserBalanceService {

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

}
