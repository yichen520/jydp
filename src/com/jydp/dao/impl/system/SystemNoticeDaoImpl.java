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

    /**
     *  查询系统公告总数（web端）
     * @return 查询成功:返回记录总数, 查询失败:返回0
     */
    public int countSystemNoticeForUser() {
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("SystemNotice_countSystemNoticeForUser");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     *  查询系统公告列表（web端）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功:返回公告列表, 查询失败:返回null
     */
    public List<SystemNoticeDO> listSystemNoticeForUser(int pageNumber, int pageSize) {
        List<SystemNoticeDO> resultList = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("SystemNotice_listSystemNoticeForUser", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 修改用户公告排位位置（全部后移一位）
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateNoticeRankNumber(){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemNotice_updateNoticeRankNumber");
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
     * 通过排名获取当前用户公告id
     * @param rankNumber 排名
     * @return 查询成功：返回广告id，查询失败：返回0
     */
    public int getIdByRankForBack(int rankNumber){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.selectOne("SystemNotice_getIdByRankForBack", rankNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return changeNumber;
    }

    /**
     * 上移用户公告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean upMoveNoticeForBack(int id){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemNotice_upMoveNoticeForBack", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (changeNumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 下移用户公告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean downMoveNoticeForBack(int id){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemNotice_downMoveNoticeForBack", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (changeNumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取当前广告排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    public int getMaxRankForBack(){
        int changeNumber = 0;
        try {
            changeNumber = sqlSessionTemplate.selectOne("SystemNotice_getMaxRankForBack");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return changeNumber;
    }

    /**
     * 修改首页广告排名（大于该排名的所有广告排名-1）
     * @param rank 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateNoticeRank(int rank){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemNotice_updateNoticeRank", rank);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (changeNumber == 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 置顶用户公告
     * @param id 记录Id
     * @return 置顶成功：返回true，置顶失败：返回false
     */
    public boolean topSystemNotice(int id){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemNotice_topSystemNotice", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (changeNumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 修改首页广告排名（小于该排名的所有广告排名+1）
     * @param rank 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateRankNumber(int rank){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemNotice_updateRankNumber", rank);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (changeNumber == 0) {
            return false;
        } else {
            return true;
        }
    }
}
