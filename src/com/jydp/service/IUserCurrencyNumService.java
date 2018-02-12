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
     * 根据用户id查询币种记录
     * @param userId 用户Id
     * @return 查询成功：返回用户币种记录信息，查询失败：返回null
     */
    List<UserCurrencyDO> getUserCurrencyByUserId (int userId);

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return
     */
    boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList);
}
