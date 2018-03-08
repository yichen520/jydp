package com.jydp.dao;

import com.jydp.entity.DO.system.SystemHotDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
public interface ISystemHotDao {

    /**
     * web用户端热门话题列表查询
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    List<SystemHotDO> getSystemHotlistForWeb();

    /**
     * 查询热门话题总数(后台操作)
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @return 成功返回：总数，失败或无数据返回：0
     */
    int sumHotForBack(String noticeType, String noticeTitle);

    /**
     * 分页查询热门话题列表（后台操作）
     * @param noticeTitle 话题标题,没有值填null
     * @param noticeType 话题类型，没有值填null
     * @param pageNumber 当前页
     * @param pageSize 每页的大小
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    List<SystemHotDO> listSystemHotForBack(String noticeTitle, String noticeType, int pageNumber, int pageSize);

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
    boolean insertSystemHot(String noticeTitle, String noticeType, String noticeUrl, String content, int rankNumber, Timestamp addTime);

    /**
     * 根据记录id查询热门话题
     * @param id 记录id
     * @return 查询成功：热门话题列表；查询失败：返回null
     */
    SystemHotDO getSystemHotById(int id);

    /**
     * 修改热门话题
     * @param id 记录id
     * @param noticeTitle 话题标题
     * @param noticeType 话题类型
     * @param noticeUrl 话题封面图地址
     * @param content 话题内容
     * @return 查询成功：返回true；查询失败：返回false
     */
    boolean updateSystemHot(int id, String noticeTitle, String noticeType, String noticeUrl, String content);

    /**
     * 删除热门话题
     * @param id 记录id
     * @return 查询成功：返回true；查询失败：返回false
     */
    boolean deteleSystemHot(int id);

    /**
     * 置顶热门话题
     * @param id 记录id
     * @param topTime 置顶时间
     * @return 查询成功：返回true；查询失败：返回false
     */
    boolean topHotTopic(int id, Timestamp topTime);

    /**
     * 查询热门话题总数 (web端)
     * @return 查询成功:返回热门话题总数, 查询失败:返回0
     */
    int countSystemHotForUser();

    /**
     * 查询热门话题列表 (web端)
     * @param pageNumber 当前页数
     * @param pageSize 每面大小
     * @return 查询成功:返回热门话题列表, 查询失败:返回null
     */
    List<SystemHotDO> listSystemHotForUser(int pageNumber, int pageSize);

    /**
     * 通过排名获取当前热门话题id
     * @param rankNumber 排名
     * @return 查询成功:返回热门话题id, 查询失败:返回0
     */
    int getIdByRankForBack(int rankNumber);

    /**
     * 上移热门话题
     * @param id 热门话题id
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean upMoveHotTopicForBack(int id);

    /**
     * 下移热门话题
     * @param id 热门话题id
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean downMoveHotTopicForBack(int id);

    /**
     * 所有热门话题排名+1(后台新增热门话题)
     * @return 操作成功:返回true, 操作失败:返回false
     */
    boolean updateSystemHotRankNumber();

    /**
     * 获取当前热门话题排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    int getMaxRankForBack();

    /**
     * 修改热门话题排名（大于该排名的所有广告排名-1）
     * @param rankNumber 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateHotTopicRank(int rankNumber);

    /**
     * 修改热门话题的排名为指定排名(仅置顶功能使用)
     * @param id 热门话题id
     * @param rankNumber 指定排名
     * @return 操作成功:返回true, 操作失败:返回false
     */
    boolean updateHotTopicToRankNumber(int id,int rankNumber);

    /**
     * 修改热门话题排名（小于该排名的所有广告排名+1）
     * @param rankNumber 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateHotTopicRankNumberUp(int rankNumber);
}
