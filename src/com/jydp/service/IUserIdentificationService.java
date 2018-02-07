package com.jydp.service;

import com.jydp.entity.DO.user.UserIdentificationDO;

/**
 * Description: 用户认证
 * Author: hht
 * Date: 2018-02-07 16:56
 */
public interface IUserIdentificationService {

    /**
     * 新增用户认证
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean insertUserIdentification (UserIdentificationDO userIdentificationDO);

    /**
     * 查询用户认证信息
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    UserIdentificationDO getUserIdentificationById(long id);

}
