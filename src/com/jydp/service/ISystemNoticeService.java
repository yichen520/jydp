package com.jydp.service;

import com.jydp.entity.DO.system.SystemNoticeDO;

import java.util.List;

/**
 * 系统公告
 * @author zym
 *
 */
public interface ISystemNoticeService {

    /**
     * web用户端查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    List<SystemNoticeDO> getSystemNoticlistForWeb();
}
