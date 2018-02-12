package com.jydp.service.impl.system;

import com.jydp.dao.ISystemHelpDao;
import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.service.ISystemHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 帮助中心
 * @author zym
 *
 */
@Service("systemHelpService")
public class SystemHelpServiceImpl implements ISystemHelpService {

    /**帮助中心*/
    @Autowired
    private ISystemHelpDao systemHelpDao;

    /**
     * 新增 帮助中心
     * @param systemHelpDO 待新增的 帮助中心
     * @return 新增成功：返回true,新增失败：返回false
     */
    public boolean insertSystemHelp(SystemHelpDO systemHelpDO) {
        return systemHelpDao.insertSystemHelp(systemHelpDO);
    }

    /**
     * 根据记录Id拿到 帮助中心
     * @param id 帮助中心 记录Id
     * @return 查询成功：返回 帮助中心，查询失败，返回null
     */
    public SystemHelpDO getSystemHelpById(int id) {
        return systemHelpDao.getSystemHelpById(id);
    }

    /**
     * 修改 帮助中心
     * @param systemHelpDO 帮助中心
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemHelp(SystemHelpDO systemHelpDO) {
        return systemHelpDao.updateSystemHelp(systemHelpDO);
    }

    /**
     * 删除 帮助中心
     * @param id 帮助中心 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemHelp(int id) {
        return systemHelpDao.deleteSystemHelp(id);
    }
}
