package com.jydp.service;

import com.jydp.entity.DO.user.UserIdentificationImageDO;

import java.util.List;

/**
 * Description: 用户认证详情图
 * Author: hht
 * Date: 2018-02-07 17:13
 */
public interface IUserIdentificationImageService {

    /**
     * 批量新增用户认证详情图列表
     * @param userIdentificationImageList 用户认证记录列表
     * @return 查询成功：返回用户认证详情图列表，查询失败：返回null
     */
    boolean insertUserIdentificationImageList(List<UserIdentificationImageDO> userIdentificationImageList);

    /**
     * 查询用户认证详情图列表
     * @param identificationId 用户认证记录Id
     * @return 查询成功：返回用户认证详情图列表，查询失败：返回null
     */
    List<UserIdentificationImageDO> listUserIdentificationImageByIdentificationId(long identificationId);

}
