package com.jydp.dao;


import com.jydp.entity.DO.otc.UserPaymentTypeDO;

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
    boolean insertUserPaymentType(UserPaymentTypeDO userPaymentType);
}
