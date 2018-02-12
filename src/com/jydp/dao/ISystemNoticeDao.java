package com.jydp.dao;

import com.jydp.entity.DO.system.SystemNoticeDO;

import java.util.List;

/**
 * 用户公告管理
 * @author zym
 *
 */
public interface ISystemNoticeDao {

    /**
     * web用户端查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    List<SystemNoticeDO> getSystemNoticlistForWeb();

    /**
     * 新增 用户公告
     * @param systemNoticeDO 待新增的 系统公告
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertSystemNotice(SystemNoticeDO systemNoticeDO);

    /**
     * 根据记录Id拿到 用户公告
     * @param id 系统公告 记录Id
     * @return 查询成功：返回系统公告，查询失败，返回null
     */
    SystemNoticeDO getSystemNoticeById(int id);

    /**
     * 根据条件分页查询用户公告信息
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @param pageNumber 查询的页面
     * @param pageSize 每页的信息数量
     * @return 成功返回：公告列表，失败或无数据返回：null
     */
    List<SystemNoticeDO> getNoticeForBack(String noticeType, String noticeTitle, int pageNumber, int pageSize);

    /**
     * 查询用户公告总数
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @return 成功返回：总数，失败或无数据返回：0
     */
    int sumNoticeForBack(String noticeType, String noticeTitle);

    /**
     * 修改 用户公告
     * @param systemNoticeDO 系统公告
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateSystemNotice(SystemNoticeDO systemNoticeDO);

    /**
     * 删除 用户公告
     * @param id 系统公告 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    boolean deleteSystemNotice(int id);
}
