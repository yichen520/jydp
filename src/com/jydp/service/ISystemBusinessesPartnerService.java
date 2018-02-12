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
     * 新增 合作商家
     * @param systemBusinessesPartnerDO 待新增的 合作商家
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO);

    /**
     * 根据记录Id拿到 合作商家
     * @param id 合作商家 记录Id
     * @return 查询成功：返回合作商家，查询失败：返回null
     */
    SystemBusinessesPartnerDO getSystemBusinessesPartnerById(int id);

    /**
     * 获取商家总数
     * @return 查询成功：返回总数，查询失败：返回0
     */
    int countSystemBusinessesPartner();

    /**
     * 分页查询合作商家
     * @param pageNumber 查询的页面
     * @param pageSize 每页的信息数量
     * @return 查询成功：返回合作商家列表，查询失败：返回 null
     */
    List<SystemBusinessesPartnerDO> listSystemBusinessesPartnerByPage(int pageNumber, int pageSize);

    /**
     * 置顶合作商家
     * @param id 合作商家Id
     * @return 置顶成功：返回true，置顶失败：返回false
     */
    boolean topTheBusinessesPartner(int id);

    /**
     * 修改 合作商家
     * @param systemBusinessesPartnerDO 合作商家
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO);

    /**
     * 删除 合作商家, 同时删除合作商家图片
     * @param id 合作商家 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    boolean deleteSystemBusinessesPartner(int id);
}
