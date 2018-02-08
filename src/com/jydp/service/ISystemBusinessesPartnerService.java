package com.jydp.service;

import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;

import java.util.List;

/**
 * 合作商家
 * @author zym
 *
 */
public interface ISystemBusinessesPartnerService {

    /**
     * web用户端查询合作商家
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerForWeb();
}
