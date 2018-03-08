package com.jydp.service.impl.system;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.jydp.dao.ISystemNoticeDao;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 用户公告管理
 * @author zym
 *
 */
@Service("systemNoticeService")
public class SystemNoticeServiceImpl implements ISystemNoticeService {

    /** 用户公告管理 */
    @Autowired
    private ISystemNoticeDao systemNoticeDao;

    /**
     * web用户端查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    @Override
    public List<SystemNoticeDO> getSystemNoticlistForWeb() {
        return systemNoticeDao.getSystemNoticlistForWeb();
    }

    /**
     * 新增 用户公告
     * @param systemNoticeDO 待新增的 系统公告
     * @return 新增成功：返回true；新增失败：返回false
     */
    @Transactional
    public boolean insertSystemNotice(SystemNoticeDO systemNoticeDO) {
        int result = systemNoticeDao.sumNoticeForBack(null, null);
        boolean executeSuccess = false;
        if(result != 0){
            executeSuccess = systemNoticeDao.updateNoticeRankNumber();
            if(executeSuccess){
                executeSuccess = systemNoticeDao.insertSystemNotice(systemNoticeDO);
            }
        }else{
            executeSuccess = systemNoticeDao.insertSystemNotice(systemNoticeDO);
        }
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 根据记录Id拿到 用户公告
     * @param id 系统公告 记录Id
     * @return 查询成功：返回 系统公告，查询失败，返回null
     */
    public SystemNoticeDO getSystemNoticeById(int id) {
        return systemNoticeDao.getSystemNoticeById(id);
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
        return systemNoticeDao.getNoticeForBack(noticeType, noticeTitle, pageNumber, pageSize);
    }

    /**
     *  查询系统公告总数（web端）
     * @return 查询成功:返回记录总数, 查询失败:返回0
     */
    public int countSystemNoticeForUser() {
        return systemNoticeDao.countSystemNoticeForUser();
    }

    /**
     *  查询系统公告列表（web端）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功:返回公告列表, 查询失败:返回null
     */
    public List<SystemNoticeDO> listSystemNoticeForUser(int pageNumber, int pageSize) {
        return systemNoticeDao.listSystemNoticeForUser(pageNumber, pageSize);
    }

    /**
     * 查询用户公告总数
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @return 成功返回：总数，失败或无数据返回：0
     */
    public int sumNoticeForBack(String noticeType, String noticeTitle) {
        return systemNoticeDao.sumNoticeForBack(noticeType, noticeTitle);
    }

    /**
     * 修改 用户公告
     * @param systemNoticeDO 系统公告
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemNotice(SystemNoticeDO systemNoticeDO) {
        return systemNoticeDao.updateSystemNotice(systemNoticeDO);
    }

    /**
     * 置顶用户公告
     * @param id 记录Id
     * @return 置顶成功：返回true，置顶失败：返回false
     */
    @Transactional
    public boolean topSystemNotice(int id) {
        SystemNoticeDO systemNoticeDO = systemNoticeDao.getSystemNoticeById(id);
        if (systemNoticeDO == null) {
            return false;
        }

        int rankNumber = systemNoticeDO.getRankNumber() - 1;
        int changeId = systemNoticeDao.getIdByRankForBack(rankNumber);

        // 如果是第一个广告就不能上移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemNoticeDao.topSystemNotice(id);
        if(executeSuccess){
            executeSuccess = systemNoticeDao.updateRankNumber(systemNoticeDO.getRankNumber());
        }
        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 删除用户公告
     * @param id 系统公告 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    @Transactional
    public boolean deleteSystemNotice(int id) {
        boolean executeSuccess = false;
        SystemNoticeDO systemNoticeDO = systemNoticeDao.getSystemNoticeById(id);
        if (systemNoticeDO == null) {
            return false;
        }

        int max = systemNoticeDao.getMaxRankForBack();
        executeSuccess = systemNoticeDao.deleteSystemNotice(id);
        if (executeSuccess) {
            // 删除成功，处理排名变动
            if (systemNoticeDO.getRankNumber() < max){
                executeSuccess = systemNoticeDao.updateNoticeRank(systemNoticeDO.getRankNumber());
            }
        }
        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 上移用户公告
     * @param id 合作商家id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean upMoveNoticeForBack(int id){
        // 首先查询广告是否存在
        SystemNoticeDO systemNoticeDO = systemNoticeDao.getSystemNoticeById(id);
        if (systemNoticeDO == null) {
            return false;
        }

        int rankNumber = systemNoticeDO.getRankNumber() - 1;
        int changeId = systemNoticeDao.getIdByRankForBack(rankNumber);

        // 如果是第一个广告就不能上移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemNoticeDao.upMoveNoticeForBack(id);
        if (executeSuccess) {
            // 把这个广告之前的一个广告排名+1
            executeSuccess = systemNoticeDao.downMoveNoticeForBack(changeId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;

    }

    /**
     * 通过排名获取当前用户公告id
     * @param rankNumber 排名
     * @return 查询成功：返回广告id，查询失败：返回0
     */
    public int getIdByRankForBack(int rankNumber) {
        return systemNoticeDao.getIdByRankForBack(rankNumber);
    }

    /**
     * 下移用户公告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean downMoveNoticeForBack(int id){
        // 首先查询广告是否存在
        SystemNoticeDO systemNoticeDO = systemNoticeDao.getSystemNoticeById(id);
        if (systemNoticeDO == null) {
            return false;
        }

        int changeId = systemNoticeDao.getIdByRankForBack(systemNoticeDO.getRankNumber() + 1);

        // 如果是最后一个广告就不能下移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemNoticeDao.downMoveNoticeForBack(id);
        if (executeSuccess) {
            // 把这个广告之前的一个广告排名-1
            executeSuccess = systemNoticeDao.upMoveNoticeForBack(changeId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

}

