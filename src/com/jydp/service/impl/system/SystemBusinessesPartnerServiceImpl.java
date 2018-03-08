package com.jydp.service.impl.system;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.jydp.dao.ISystemBusinessesPartnerDao;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.service.ISystemBusinessesPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;

/**
 * 合作商家
 * @author zym
 *
 */
@Service("systemBusinessesPartnerService")
public class SystemBusinessesPartnerServiceImpl implements ISystemBusinessesPartnerService {

    /** 合作商家 */
    @Autowired
    private ISystemBusinessesPartnerDao systemBusinessesPartnerDao;

    /**
     * web用户端查询合作商家
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    public List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerForWeb() {
        return systemBusinessesPartnerDao.getSystemBusinessesPartnerForWeb();
    }

    /**
     * 新增 合作商家
     * @param systemBusinessesPartnerDO 待新增的 合作商家
     * @return 新增成功：返回true，新增失败：返回false
     */
    @Transactional
    public boolean insertSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO) {
        int result = systemBusinessesPartnerDao.countSystemBusinessesPartner();
        boolean executeSuccess = false;
        if(result != 0){
            executeSuccess = systemBusinessesPartnerDao.updatebusinessesPartnerRankNumber();
            if(executeSuccess){
                executeSuccess = systemBusinessesPartnerDao.insertSystemBusinessesPartner(systemBusinessesPartnerDO);
            }

        }else{
            executeSuccess = systemBusinessesPartnerDao.insertSystemBusinessesPartner(systemBusinessesPartnerDO);
        }

        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;

    }

    /**
     * 根据记录Id拿到 合作商家
     * @param id 合作商家 记录Id
     * @return 查询成功：返回 合作商家，查询失败，返回null
     */
    public SystemBusinessesPartnerDO getSystemBusinessesPartnerById(int id) {
        return systemBusinessesPartnerDao.getSystemBusinessesPartnerById(id);
    }

    /**
     * 获取商家总数
     * @return 查询成功：返回总数，查询失败：返回0
     */
    public int countSystemBusinessesPartner() {
        return systemBusinessesPartnerDao.countSystemBusinessesPartner();
    }

    /**
     * 分页查询合作商家
     * @param pageNumber 查询的页面
     * @param pageSize 每页的信息数量
     * @return 查询成功：返回合作商家列表，查询失败：返回 null
     */
    public List<SystemBusinessesPartnerDO> listSystemBusinessesPartnerByPage(int pageNumber, int pageSize) {
        return systemBusinessesPartnerDao.listSystemBusinessesPartnerByPage(pageNumber, pageSize);
    }

    /**
     * 置顶合作商家
     * @param id 合作商家Id
     * @return 置顶成功：返回true，置顶失败：返回false
     */
    @Transactional
    public boolean topTheBusinessesPartner(int id) {

        SystemBusinessesPartnerDO systemBusinessesPartnerDO = systemBusinessesPartnerDao.getSystemBusinessesPartnerById(id);
        if (systemBusinessesPartnerDO == null) {
            return false;
        }

        int rankNumber = systemBusinessesPartnerDO.getRankNumber() - 1;
        int changeId = systemBusinessesPartnerDao.getIdByRankForBack(rankNumber);

        // 如果是第一个广告就不能上移了
        if (changeId == 0) {
            return false;
        }

        boolean executeSuccess = systemBusinessesPartnerDao.topTheBusinessesPartner(id);
        if(executeSuccess){
            executeSuccess = systemBusinessesPartnerDao.updateRankNumber(systemBusinessesPartnerDO.getRankNumber());
        }
        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 修改 合作商家
     * @param systemBusinessesPartnerDO 合作商家
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO) {
        return systemBusinessesPartnerDao.updateSystemBusinessesPartner(systemBusinessesPartnerDO);
    }

    /**
     * 删除 合作商家, 同时删除合作商家图片
     * @param id 合作商家 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    @Transactional
    public boolean deleteSystemBusinessesPartner(int id) {
        boolean executeSuccess = false;
        SystemBusinessesPartnerDO systemBusinessesPartner = systemBusinessesPartnerDao.getSystemBusinessesPartnerById(id);
        if (systemBusinessesPartner == null) {
            return executeSuccess;
        }

        int max = systemBusinessesPartnerDao.getMaxRankForBack();
        executeSuccess = systemBusinessesPartnerDao.deleteSystemBusinessesPartner(id);
        if (executeSuccess) {
            // 删除成功，处理排名变动
            if (systemBusinessesPartner.getRankNumber() < max) {
                executeSuccess = systemBusinessesPartnerDao.updateBusinessesPartnerRank(systemBusinessesPartner.getRankNumber());
            }
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } else {
            // 删除文件
            FileWriteRemoteUtil.deleteFile(systemBusinessesPartner.getBusinessesImageUrl());
        }

        return executeSuccess;
    }

    /**
     * 上移合作商家
     * @param id 合作商家记录id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean upMoveBusinessesPartnerForBack(int id){
        // 首先查询广告是否存在
        SystemBusinessesPartnerDO systemBusinessesPartnerDO = systemBusinessesPartnerDao.getSystemBusinessesPartnerById(id);
        if (systemBusinessesPartnerDO == null) {
            return false;
        }

        int rankNumber = systemBusinessesPartnerDO.getRankNumber() - 1;
        int changeId = systemBusinessesPartnerDao.getIdByRankForBack(rankNumber);

        // 如果是第一个广告就不能上移了
        if (changeId == 0) {
            return false;
        }
        boolean executeSuccess = systemBusinessesPartnerDao.upMoveBusinessesPartnerForBack(id);
        if (executeSuccess) {
            // 把这个广告之前的一个广告排名+1
            executeSuccess = systemBusinessesPartnerDao.downMoveBusinessesPartnerForBack(changeId);
        }
        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 通过排名获取当前合作商家id
     * @param rankNumber 排名
     * @return 查询成功：返回合作商家id，查询失败：返回0
     */
    public int getIdByRankForBack(int rankNumber) {
        return systemBusinessesPartnerDao.getIdByRankForBack(rankNumber);
    }

    /**
     * 下移合作商家
     * @param id 合作商家id
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean downMoveBusinessesPartnerForBack(int id) {
        // 首先查询广告是否存在
        SystemBusinessesPartnerDO systemBusinessesPartnerDO = systemBusinessesPartnerDao.getSystemBusinessesPartnerById(id);
        if (systemBusinessesPartnerDO == null) {
            return false;
        }

        int changeId = systemBusinessesPartnerDao.getIdByRankForBack(systemBusinessesPartnerDO.getRankNumber() + 1);

        // 如果是最后一个广告就不能下移了
        if (changeId == 0) {
            return false;
        }
        boolean executeSuccess = systemBusinessesPartnerDao.downMoveBusinessesPartnerForBack(id);
        if (executeSuccess) {
            // 把这个广告之前的一个广告排名-1
            executeSuccess = systemBusinessesPartnerDao.upMoveBusinessesPartnerForBack(changeId);
        }
        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 获取当前广告排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    public int getMaxRankForBack(){
        return systemBusinessesPartnerDao.getMaxRankForBack();
    }
}
