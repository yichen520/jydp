package com.jydp.service;

import com.jydp.entity.DO.syl.SylUserBoundDO;

/**
 * 盛源链账号绑定表
 *
 * @author sy
 */
public interface ISylUserBoundService {

    /**
     * 新增盛源链账号绑定记录
     * @param sylUserBound 待新增的 盛源链账号绑定记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertSylUserBound(SylUserBoundDO sylUserBound);

    /**
     * 查询用户盛源链账号,根据用户id
     * @param userId 用户id
     * @return 查询成功:返回用户盛源链信息, 查询失败:返回null
     */
    SylUserBoundDO getSylUserBoundByUserId(int userId);
}
