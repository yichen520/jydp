package com.jydp.service;

import com.jydp.entity.DO.system.SystemHelpDO;

/**
 * 帮助中心
 * @author zym
 *
 */
public interface ISystemHelpService {

    /**
     * 新增 帮助中心
     * @param systemHelpDO 待新增的 帮助中心
     * @return 新增成功：返回true,新增失败：返回false
     */
    boolean insertSystemHelp(SystemHelpDO systemHelpDO);

    /**
     * 根据记录Id拿到 帮助中心
     * @param id 帮助中心 记录Id
     * @return 查询成功：返回帮助中心，查询失败，返回null
     */
    SystemHelpDO getSystemHelpById(int id);

    /**
     * 修改 帮助中心
     * @param systemHelpDO 帮助中心
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateSystemHelp(SystemHelpDO systemHelpDO);

    /**
     * 删除 帮助中心
     * @param id 帮助中心 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    boolean deleteSystemHelp(int id);
}
