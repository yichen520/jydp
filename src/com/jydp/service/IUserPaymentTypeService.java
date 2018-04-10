package com.jydp.service;

import com.jydp.entity.DO.otc.UserPaymentTypeDO;

/**
 * 用户收款记录
 * @author lgx
 */
public interface IUserPaymentTypeService {
    /**
     * 新增 收款记录
     * @param userPaymentType 待新增的 收款记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertUserPaymentType(UserPaymentTypeDO userPaymentType);
}
