package com.jydp.service;

import com.jydp.entity.DO.user.UserBalanceDO;

import java.util.List;

/**
 * Description:用户账户记录
 * Author: hht
 * Date: 2018-02-07 15:42
 */
public interface IUserBalanceService {

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
     * 根据用户Id查询用户账户记录(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户账户记录列表；查询失败：返回null
     */
    List<UserBalanceDO> getUserBalancelistForWeb(int userId, int pageNumber, int pageSize);

    /**
     * 根据userId查询用户记录总数(web端)
     * @param userId 用户Id
     * @return 查询成功：返回用户账户记录总数；查询失败：返回0
     */
    int countUserBalanceForWeb(int userId);

    /**
     * 根据userBalanceList批量插入用户记录
     * @param userBalanceList 用户记录集合
     * @return 成功：true；查询失败：false
     */
    boolean insertUserBalanceList(List<UserBalanceDO>userBalanceList);
}
