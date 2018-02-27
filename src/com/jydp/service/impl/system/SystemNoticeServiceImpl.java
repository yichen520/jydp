package com.jydp.service.impl.system;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.jydp.dao.ISystemNoticeDao;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean insertSystemNotice(SystemNoticeDO systemNoticeDO) {
        return systemNoticeDao.insertSystemNotice(systemNoticeDO);
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
    public boolean topSystemNotice(int id) {
        SystemNoticeDO systemNoticeDO = new SystemNoticeDO();
        systemNoticeDO.setId(id);
        systemNoticeDO.setTopTime(DateUtil.getCurrentTime());

        return systemNoticeDao.updateSystemNotice(systemNoticeDO);
    }

    /**
     * 删除用户公告
     * @param id 系统公告 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemNotice(int id) {
        SystemNoticeDO systemNoticeDO = systemNoticeDao.getSystemNoticeById(id);
        if (systemNoticeDO == null) {
            return false;
        }

        boolean deleteResult = systemNoticeDao.deleteSystemNotice(id);
        if (deleteResult) {
            // 删除封面图
            FileWriteRemoteUtil.deleteFile(systemNoticeDO.getNoticeUrl());
            return true;
        } else {
            return false;
        }
    }

}

