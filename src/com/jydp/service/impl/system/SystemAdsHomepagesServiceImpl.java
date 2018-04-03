package com.jydp.service.impl.system;

import com.iqmkj.utils.FileWriteRemoteUtil;
import com.jydp.dao.ISystemAdsHomepagesDao;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.service.ISystemAdsHomepagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
@Service("systemAdsHomepagesService")
public class SystemAdsHomepagesServiceImpl implements ISystemAdsHomepagesService {

    /**首页广告*/
    @Autowired
    private ISystemAdsHomepagesDao systemAdsHomepagesDao;

    /**
     * 列表首页广告
     * @return 操作成功：返回首页广告数据列表，操作失败或无数据：返回null
     */
    public List<SystemAdsHomepagesDO> getAdsHomepagesForBack(){
        return systemAdsHomepagesDao.getAdsHomepagesForBack();
    }

    /**
     * 获取当前广告排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    public int getMaxRankForBack(){
        return systemAdsHomepagesDao.getMaxRankForBack();
    }

    /**
     * 新增 首页广告
     * @param systemAdsHomepagesDO 待新增的 首页广告
     * @return 新增成功：返回true，新增失败：返回false
     */
    @Transactional
    public boolean insertSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO){
        int result = systemAdsHomepagesDao.getMaxRankForBack();
        boolean executeSuccess = false;
        if(result != 0){
            executeSuccess = systemAdsHomepagesDao.updateAdsHomepagesRankNumber();
            if(executeSuccess){
                executeSuccess = systemAdsHomepagesDao.insertSystemAdsHomePages(systemAdsHomepagesDO);
            }

        }else{
            executeSuccess = systemAdsHomepagesDao.insertSystemAdsHomePages(systemAdsHomepagesDO);
        }

        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 根据记录Id拿到 首页广告
     * @param id 首页广告 记录Id
     * @return 查询成功：返回首页广告，查询失败，返回null
     */
    public SystemAdsHomepagesDO getSystemAdsHomePagesById(int id){
        return systemAdsHomepagesDao.getSystemAdsHomePagesById(id);
    }

    /**
     * 修改 首页广告
     * @param systemAdsHomepagesDO 首页广告
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO){
        return systemAdsHomepagesDao.updateSystemAdsHomePages(systemAdsHomepagesDO);
    }

    /**
     * 删除 首页广告
     * @param systemAdsHomepagesDO 首页广告
     * @return 删除成功：返回true，删除失败：返回false
     */
    @Transactional
    public boolean deleteSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO) {
        boolean executeSuccess;

        int max = systemAdsHomepagesDao.getMaxRankForBack();
        executeSuccess = systemAdsHomepagesDao.deleteSystemAdsHomePages(systemAdsHomepagesDO.getId());
        if (executeSuccess) {
            // 删除成功，处理排名变动
            if (systemAdsHomepagesDO.getRankNumber() < max) {
                executeSuccess = systemAdsHomepagesDao.updateAdsHomepagesRank(systemAdsHomepagesDO.getRankNumber());
            }
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 上移首页广告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean upMoveAdsHomepagesForBack(int id) {
        // 首先查询广告是否存在
        SystemAdsHomepagesDO systemAdsHomepagesDO = systemAdsHomepagesDao.getSystemAdsHomePagesById(id);
        if (systemAdsHomepagesDO == null) {
            return false;
        }

        int rankNumber = systemAdsHomepagesDO.getRankNumber() - 1;
        int changeId = systemAdsHomepagesDao.getIdByRankForBack(rankNumber);

        // 如果是第一个广告就不能上移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemAdsHomepagesDao.upMoveAdsHomepagesForBack(id);
        if (executeSuccess) {
            // 把这个广告之前的一个广告排名+1
            executeSuccess = systemAdsHomepagesDao.downMoveAdsHomepagesForBack(changeId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 通过排名获取当前广告id
     * @param rankNumber 排名
     * @return 查询成功：返回广告id，查询失败：返回0
     */
    public int getIdByRankForBack(int rankNumber) {
        return systemAdsHomepagesDao.getIdByRankForBack(rankNumber);
    }

    /**
     * 下移首页广告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean downMoveAdsHomepagesForBack(int id) {
        // 首先查询广告是否存在
        SystemAdsHomepagesDO systemAdsHomepagesDO = systemAdsHomepagesDao.getSystemAdsHomePagesById(id);
        if (systemAdsHomepagesDO == null) {
            return false;
        }

        int changeId = systemAdsHomepagesDao.getIdByRankForBack(systemAdsHomepagesDO.getRankNumber() + 1);

        // 如果是最后一个广告就不能下移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemAdsHomepagesDao.downMoveAdsHomepagesForBack(id);
        if (executeSuccess) {
            // 把这个广告之前的一个广告排名-1
            executeSuccess = systemAdsHomepagesDao.upMoveAdsHomepagesForBack(changeId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * web端用户查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    @Override
    public List<SystemAdsHomepagesDO> getSystemAdsHomepageslistForWeb() {
        return systemAdsHomepagesDao.getSystemAdsHomepageslistForWeb();
    }

    /**
     * 置顶首页广告
     * @param id 记录Id
     * @return 置顶成功：返回true，置顶失败：返回false
     */
   public boolean topAdsHomepages(int id){
       SystemAdsHomepagesDO systemAdsHomepagesDO = systemAdsHomepagesDao.getSystemAdsHomePagesById(id);
       if (systemAdsHomepagesDO == null) {
           return false;
       }

       int rankNumber = systemAdsHomepagesDO.getRankNumber() - 1;
       int changeId = systemAdsHomepagesDao.getIdByRankForBack(rankNumber);

       // 如果是第一个广告就不能上移了
       if (changeId == 0) {
           return false;
       }

       boolean executeSuccess = systemAdsHomepagesDao.topAdsHomepages(id);
       if(executeSuccess){
           executeSuccess = systemAdsHomepagesDao.updateRankNumber(systemAdsHomepagesDO.getRankNumber());
       }
       // 数据回滚
       if (!executeSuccess) {
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
       }

       return executeSuccess;
   }
}
