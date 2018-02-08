package com.jydp.dao;

import com.jydp.entity.DO.system.SystemHotDO;

import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
public interface ISystemHotDao {

    /**
     * web用户端热门话题列表查询
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    List<SystemHotDO> getSystemHotlistForWeb();
}
