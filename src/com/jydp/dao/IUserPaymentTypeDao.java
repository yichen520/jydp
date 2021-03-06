package com.jydp.dao;


import com.jydp.entity.DO.otc.UserPaymentTypeDO;

import java.util.List;

/**
 * 用户收款方式
 *
 * @author lgx
 */
public interface IUserPaymentTypeDao {

    /**
     * 新增 收款方式
     * @param userPaymentType 待新增的 收款方式
     * @return 新增成功：返回true, 新增失败：返回false
     */
    UserPaymentTypeDO insertUserPaymentType(UserPaymentTypeDO userPaymentType);

    /**
     * 根据用户id、挂单号、支付方式查询 收款记录
     * @param userId  用户id
     * @param otcPendingOrderNo 挂单号
     * @param paymentType 支付方式
     * @return 查询成功：返回用户收款记录, 查询失败：返回null
     */
    UserPaymentTypeDO getUserPaymentType(int userId, String otcPendingOrderNo, int paymentType);

    /**
     * 根据用户id、挂单号查询 收款记录列表
     * @param userId  用户id
     * @param otcPendingOrderNo 挂单号
     * @return 查询成功：返回用户收款记录, 查询失败：返回null
     */
    List<UserPaymentTypeDO> listUserPaymentType(int userId, String otcPendingOrderNo);
}
