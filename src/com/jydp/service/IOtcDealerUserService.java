package com.jydp.service;

import com.jydp.entity.DO.otc.OtcDealerUserDO;

/**
 * 用户标识经销商相关操作
 * @author sy
 */
public interface IOtcDealerUserService {
    /**
     * 新增 用户标识经销商
     * @param otcDealerUser 待新增的 用户标识经销商
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertOtcDealerUser(OtcDealerUserDO otcDealerUser);

    /**
     * 根据用户id查询标识信息
     * @param userId 用户id
     * @return 查询成功：返回标识信息, 查询失败或者无信息：返回null
     */
    OtcDealerUserDO getOtcDealerUserByUserId(int userId);
}
