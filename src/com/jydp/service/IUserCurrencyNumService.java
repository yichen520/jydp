package com.jydp.service;

import com.jydp.entity.DO.user.UserCurrencyDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
public interface IUserCurrencyNumService {

    /**
     * 查询用户币数量
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    List<UserCurrencyNumDO> getUserCurrencyNumByUserId (int userId);

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return
     */
    boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList);
}
