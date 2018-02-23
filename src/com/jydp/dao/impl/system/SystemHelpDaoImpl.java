package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemHelpDao;
import com.jydp.entity.DO.system.SystemHelpDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 帮助中心
 * @author zym
 *
 */
@Repository
public class SystemHelpDaoImpl implements ISystemHelpDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 帮助中心
     * @param systemHelpDO 待新增的 帮助中心
     * @return 新增成功：返回true，新增失败：返回null
     */
    public boolean insertSystemHelp(SystemHelpDO systemHelpDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SystemHelp_insertSystemHelp", systemHelpDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据记录Id拿到 帮助中心
     * @param id 帮助中心 记录Id
     * @return 查询成功：返回 帮助中心，查询失败，返回null
     */
    public SystemHelpDO getSystemHelpById(int id) {
        SystemHelpDO systemHelpDO = null;

        try {
            systemHelpDO = sqlSessionTemplate.selectOne("SystemHelp_getSystemHelpById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemHelpDO;
    }

    /**
     * 修改 帮助中心
     * @param systemHelpDO 帮助中心
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemHelp(SystemHelpDO systemHelpDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemHelp_updateSystemHelp", systemHelpDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除 帮助中心
     * @param id 帮助中心 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemHelp(int id) {
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("SystemHelp_deleteSystemHelp", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
