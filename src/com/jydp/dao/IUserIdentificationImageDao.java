package com.jydp.dao;

import com.jydp.entity.DO.user.UserIdentificationImageDO;

import java.util.List;

/**
 * Description: 用户认证详情图
 * Author: hht
 * Date: 2018-02-07 17:11
 */
public interface IUserIdentificationImageDao {

    /**
     * 查询用户认证详情图列表
     * @param identificationId 用户认证记录Id
     * @return 查询成功：返回用户认证详情图列表，查询失败：返回null
     */
    List<UserIdentificationImageDO> listUserIdentificationImageByIdentificationId(long identificationId);

}
