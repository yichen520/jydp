package com.jydp.dao;

import com.jydp.entity.DO.system.SystemAdsHomepagesDO;

import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
public interface ISystemAdsHomepagesDao {

    /**
     * 列表首页广告
     * @return 操作成功：返回首页广告数据列表，操作失败或无数据：返回null
     */
    List<SystemAdsHomepagesDO> getAdsHomepagesForBack();

    /**
     * 获取当前广告排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    int getMaxRankForBack();

    /**
     * 新增 首页广告
     * @param systemAdsHomepagesDO 待新增的 首页广告
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO);

    /**
     * 根据记录Id拿到 首页广告
     * @param id 首页广告 记录Id
     * @return 查询成功：返回首页广告，查询失败，返回null
     */
    SystemAdsHomepagesDO getSystemAdsHomePagesById(int id);

    /**
     * 修改 首页广告
     * @param systemAdsHomepagesDO 首页广告
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateSystemAdsHomePages(SystemAdsHomepagesDO systemAdsHomepagesDO);

    /**
     * 删除 首页广告
     * @param id 首页广告 的id
     * @return 删除成功：返回true，删除失败：返回false
     */
    boolean deleteSystemAdsHomePages(int id);

    /**
     * 修改首页广告排名（大于该排名的所有广告排名-1）
     * @param rank 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateAdsHomepagesRank(int rank);

    /**
     * 通过排名获取当前广告id
     * @param rankNumber 排名
     * @return 查询成功：返回广告id，查询失败：返回0
     */
    int getIdByRankForBack(int rankNumber);

    /**
     * 上移首页广告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean upMoveAdsHomepagesForBack(int id);

    /**
     * 下移首页广告
     * @param id 首页广告id
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean downMoveAdsHomepagesForBack(int id);

}
