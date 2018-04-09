package com.jydp.dao;

import com.jydp.entity.DO.otc.OtcDealerUserDO;

/**
 * 用户标识经销商相关操作
 *
 * @author sy
 */
public interface IOtcDealerUserDao {

    /**
     * 新增 用户标识经销商
     * @param otcDealerUser 待新增的 用户标识经销商
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertOtcDealerUser(OtcDealerUserDO otcDealerUser);
}
