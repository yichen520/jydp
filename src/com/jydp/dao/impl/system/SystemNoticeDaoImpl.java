package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemNoticeDao;
import com.jydp.entity.DO.system.SystemNoticeDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统公告
 * @author zym
 *
 */
@Repository
public class SystemNoticeDaoImpl implements ISystemNoticeDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * web用户端查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    @Override
    public List<SystemNoticeDO> getSystemNoticlistForWeb() {
        List<SystemNoticeDO> systemNoticeList = null;

        try {
            systemNoticeList = sqlSessionTemplate.selectList("SystemNotice_getSystemNoticlistForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return systemNoticeList;
    }

    /**
     * 新增用户公告
     * @param systemNoticeDO 待新增的 系统公告
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertSystemNotice(SystemNoticeDO systemNoticeDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SystemNotice_insertSystemNotice", systemNoticeDO);
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
     * 根据记录Id拿到用户公告
     * @param id 系统公告 记录Id
     * @return 查询成功：返回 系统公告，查询失败，返回null
     */
    public SystemNoticeDO getSystemNoticeById(int id) {
        SystemNoticeDO systemNoticeDO = null;

        try {
            systemNoticeDO = sqlSessionTemplate.selectOne("SystemNotice_getSystemNoticeById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemNoticeDO;
    }

    /**
     * 根据条件分页查询用户公告信息
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @param pageNumber 查询的页面
     * @param pageSize 每页的信息数量
     * @return 成功返回：公告列表，失败或无数据返回：null
     */
    public List<SystemNoticeDO> getNoticeForBack(String noticeType, String noticeTitle, int pageNumber, int pageSize) {
        List<SystemNoticeDO> systemNoticeDOList = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noticeType", noticeType);
        map.put("noticeTitle", noticeTitle);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            systemNoticeDOList = sqlSessionTemplate.selectList("SystemNotice_getNoticeForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemNoticeDOList;
    }

    /**
     * 查询用户公告总数
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @return 成功返回：总数，失败或无数据返回：0
     */
    public int sumNoticeForBack(String noticeType, String noticeTitle) {
        int sumNumber = 0;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noticeType", noticeType);
        map.put("noticeTitle", noticeTitle);

        try {
            sumNumber = sqlSessionTemplate.selectOne("SystemNotice_sumNoticeForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return sumNumber;
    }

    /**
     * 修改用户公告
     * @param systemNoticeDO 系统公告
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemNotice(SystemNoticeDO systemNoticeDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemNotice_updateSystemNotice", systemNoticeDO);
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
     * 删除用户公告
     * @param id 系统公告 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemNotice(int id) {
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("SystemNotice_deleteSystemNotice", id);
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
