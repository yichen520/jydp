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
    public boolean insertSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO) {
        return systemBusinessesPartnerDao.insertSystemBusinessesPartner(systemBusinessesPartnerDO);
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
    public boolean topTheBusinessesPartner(int id) {
        SystemBusinessesPartnerDO systemBusinessesPartner = new SystemBusinessesPartnerDO();
        systemBusinessesPartner.setId(id);
        systemBusinessesPartner.setTopTime(DateUtil.getCurrentTime());
        return systemBusinessesPartnerDao.updateSystemBusinessesPartner(systemBusinessesPartner);
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
        executeSuccess = systemBusinessesPartnerDao.deleteSystemBusinessesPartner(id);
        if (executeSuccess) {
            //删除成功，删除图片
            executeSuccess = FileWriteRemoteUtil.deleteFile(systemBusinessesPartner.getBusinessesImageUrl());
        }

        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }
}
