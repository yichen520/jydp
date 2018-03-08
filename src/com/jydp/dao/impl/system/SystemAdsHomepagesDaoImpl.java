package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemAdsHomepagesDao;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
@Repository
public class SystemAdsHomepagesDaoImpl implements ISystemAdsHomepagesDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 列表首页广告
     * @return 操作成功：返回首页广告数据列表，操作失败或无数据：返回null
     */
    public List<SystemAdsHomepagesDO> getAdsHomepagesForBack(){
        List<SystemAdsHomepagesDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("SystemAdsHomepages_getAdsHomepagesForBack");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
    }

    /**
     * 获取当前广告排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    public int getMaxRankForBack(){
        int changeNumber = 0;
        try {
            changeNumber = sqlSessionTemplate.selectOne("SystemAdsHomepages_getMaxRankForBack");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return changeNumber;
    }

    /**
     * 新增 首页广告
     * @param systemAdsHomepagesDO 待新增的 首页广告
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SystemAdsHomepages_insertSystemAdsHomePages", systemAdsHomepagesDO);
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
     * 根据记录Id拿到 首页广告
     * @param id 首页广告 记录Id
     * @return 查询成功：返回首页广告，查询失败，返回null
     */
    public SystemAdsHomepagesDO getSystemAdsHomePagesById(int id){
        SystemAdsHomepagesDO systemAdsHomepagesDO = null;

        try {
            systemAdsHomepagesDO = sqlSessionTemplate.selectOne("SystemAdsHomepages_getSystemAdsHomePagesById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemAdsHomepagesDO;
    }

    /**
     * 修改 首页广告
     * @param systemAdsHomepagesDO 首页广告
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemAdsHomepages_updateSystemAdsHomePages", systemAdsHomepagesDO);
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
     * 删除 首页广告
     * @param id 首页广告 的id
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemAdsHomePages(int id){
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("SystemAdsHomepages_deleteSystemAdsHomePages", id);
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
     * 修改首页广告排名（大于该排名的所有广告排名-1）
     * @param rank 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateAdsHomepagesRank(int rank){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemAdsHomepages_updateAdsHomepagesRank", rank);
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
     * 通过排名获取当前广告id
     * @param rankNumber 排名
     * @return 查询成功：返回广告id，查询失败：返回0
     */
    public int getIdByRankForBack(int rankNumber){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.selectOne("SystemAdsHomepages_getIdByRankForBack", rankNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return changeNumber;
    }

    /**
     * 上移首页广告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean upMoveAdsHomepagesForBack(int id){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemAdsHomepages_upMoveAdsHomepagesForBack", id);
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
     * 下移首页广告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean downMoveAdsHomepagesForBack(int id){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemAdsHomepages_downMoveAdsHomepagesForBack", id);
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
     * web端用户查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    @Override
    public List<SystemAdsHomepagesDO> getSystemAdsHomepageslistForWeb() {
        List<SystemAdsHomepagesDO> systemAdsHomepagesList = null;

        try {
            systemAdsHomepagesList = sqlSessionTemplate.selectList("SystemAdsHomepages_getSystemAdsHomepageslistForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return systemAdsHomepagesList;
    }

    /**
     * 置顶首页广告
     * @param id 记录Id
     * @return 置顶成功：返回true，置顶失败：返回false
     */
    public boolean topAdsHomepages(int id){
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemAdsHomepages_topAdsHomepages", id);
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
            changeNumber = sqlSessionTemplate.update("SystemAdsHomepages_updateRankNumber", rank);
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
