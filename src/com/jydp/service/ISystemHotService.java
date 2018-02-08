package com.jydp.service;

import com.jydp.entity.DO.system.SystemHotDO;

import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
public interface ISystemHotService {

    /**
     * web用户端热门话题列表查询
     * @return
     */
    List<SystemHotDO> getSystemHotlistForWeb();
}
