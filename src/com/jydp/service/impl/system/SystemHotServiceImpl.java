package com.jydp.service.impl.system;

import com.jydp.dao.ISystemHotDao;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.service.ISystemHotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
@Service("systemHotService")
public class SystemHotServiceImpl implements ISystemHotService {

    /** 热门话题 */
    @Autowired
    private ISystemHotDao systemHotDao;
    /**
     * web用户端热门话题列表查询
     * @return
     */
    @Override
    public List<SystemHotDO> getSystemHotlistForWeb() {
        return systemHotDao.getSystemHotlistForWeb();
    }

    /**
     * 查询热门话题总数(后台操作)
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @return 成功返回：总数，失败或无数据返回：0
     */
    public int sumHotForBack(String noticeType, String noticeTitle){
        return systemHotDao.sumHotForBack(noticeType, noticeTitle);
    }

    /**
     * 分页查询热门话题列表（后台操作）
     * @param noticeTitle 话题标题,没有值填null
     * @param noticeType 话题类型，没有值填null
     * @param pageNumber 当前页
     * @param pageSize 每页的大小
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    public List<SystemHotDO> listSystemHotForBack(String noticeTitle, String noticeType, int pageNumber, int pageSize){
        return systemHotDao.listSystemHotForBack(noticeTitle, noticeType, pageNumber, pageSize);
    }

    /**
     * 新增热门话题
     * @param noticeTitle 话题标题
     * @param noticeType 话题类型
     * @param noticeUrl 话题封面图地址
     * @param content 话题内容
     * @param rankNumber 话题排名
     * @param addTime 添加时间
     * @return 查询成功：返回true；查询失败：返回false
     */
    @Transactional
    public boolean insertSystemHot(String noticeTitle, String noticeType, String noticeUrl, String content,int rankNumber, Timestamp addTime){
        int totalNumber = systemHotDao.countSystemHotForUser();
        if (totalNumber == 0) {
            return systemHotDao.insertSystemHot(noticeTitle, noticeType, noticeUrl, content, rankNumber, addTime);
        }

        //所有热门话题排名+1
        boolean executeSuccess = systemHotDao.updateSystemHotRankNumber();
        if (executeSuccess) {
            systemHotDao.insertSystemHot(noticeTitle, noticeType, noticeUrl, content, rankNumber, addTime);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 根据记录id查询热门话题
     * @param id 记录id
     * @return 查询成功：热门话题列表；查询失败：返回null
     */
    public SystemHotDO getSystemHotById(int id){
        return systemHotDao.getSystemHotById(id);
    }

    /**
     * 修改热门话题
     * @param id 记录id
     * @param noticeTitle 话题标题
     * @param noticeType 话题类型
     * @param noticeUrl 话题封面图地址
     * @param content 话题内容
     * @return 查询成功：返回true；查询失败：返回false
     */
    public boolean updateSystemHot(int id, String noticeTitle, String noticeType, String noticeUrl, String content){
        return systemHotDao.updateSystemHot(id, noticeTitle, noticeType, noticeUrl, content);
    }

    /**
     * 删除热门话题
     * @param id 记录id
     * @return 查询成功：返回true；查询失败：返回false
     */
    @Transactional
    public boolean deteleSystemHot(int id){
        SystemHotDO systemHot = systemHotDao.getSystemHotById(id);
        // 热门话题不存在
        if (systemHot == null) {
            return false;
        }

        int max = systemHotDao.getMaxRankForBack();

        boolean executeSuccess = systemHotDao.deteleSystemHot(id);
        if (executeSuccess) {
            // 删除成功，处理排名变动
            if (systemHot.getRankNumber() < max) {
                executeSuccess = systemHotDao.updateHotTopicRank(systemHot.getRankNumber());
            }
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 置顶热门话题
     * @param id 记录id
     * @param topTime 置顶时间
     * @return 查询成功：返回true；查询失败：返回false
     */
    @Transactional
    public boolean topHotTopic(int id, Timestamp topTime){
        SystemHotDO systemHot = systemHotDao.getSystemHotById(id);
        // 热门话题不存在
        if (systemHot == null) {
            return false;
        }

        int rankNumber = systemHot.getRankNumber() - 1;
        int changeId = systemHotDao.getIdByRankForBack(rankNumber);

        // 如果是第一个热门话题就不能置顶了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemHotDao.updateHotTopicToRankNumber(systemHot.getId(), 0);
        if (executeSuccess) {
            executeSuccess = systemHotDao.updateHotTopicRankNumberUp(systemHot.getRankNumber());
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
        }

    /**
     * 查询热门话题总数 (web端)
     * @return 查询成功:返回热门话题总数, 查询失败:返回0
     */
    public int countSystemHotForUser() {
        return systemHotDao.countSystemHotForUser();
    }

    /**
     * 查询热门话题列表 (web端)
     * @param pageNumber 当前页数
     * @param pageSize 每面大小
     * @return 查询成功:返回热门话题列表, 查询失败:返回null
     */
    public List<SystemHotDO> listSystemHotForUser(int pageNumber, int pageSize) {
        return systemHotDao.listSystemHotForUser(pageNumber, pageSize);
    }

    /**
     * 上移热门话题
     * @param id 记录Id
     * @return 操作成功:返回true, 操作失败:返回false
     */
    @Transactional
    public boolean upMoveHotTopicForBack(int id) {
        SystemHotDO systemHot = systemHotDao.getSystemHotById(id);
        if (systemHot == null) {
            return false;
        }

        int rankNumber = systemHot.getRankNumber() - 1;
        int changeId = systemHotDao.getIdByRankForBack(rankNumber);

        // 如果是第一个热门话题就不能上移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemHotDao.upMoveHotTopicForBack(id);
        if (executeSuccess) {
            executeSuccess = systemHotDao.downMoveHotTopicForBack(changeId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;

    }

    /**
     * 下移热门话题
     * @param id 记录Id
     * @return 操作成功:返回true, 操作失败:返回false
     */
    @Transactional
    public boolean downMoveHotTopicForBack(int id) {
        SystemHotDO systemHot = systemHotDao.getSystemHotById(id);
        if (systemHot == null) {
            return false;
        }

        int changeId = systemHotDao.getIdByRankForBack(systemHot.getRankNumber() + 1);

        // 如果是最后一个热门话题就不能下移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemHotDao.downMoveHotTopicForBack(id);
        if (executeSuccess) {
            executeSuccess = systemHotDao.upMoveHotTopicForBack(changeId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 通过排名获取当前热门话题id
     * @param rankNumber 排名
     * @return 查询成功:返回热门话题id, 查询失败:返回0
     */
    public int getIdByRankForBack(int rankNumber) {
        return systemHotDao.getIdByRankForBack(rankNumber);
    }
}
