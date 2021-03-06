package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemHotDao;
import com.jydp.entity.DO.system.SystemHotDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.Keymap;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 热门话题
 * @author zym
 *
 */
@Repository
public class SystemHotDaoImpl implements ISystemHotDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    /**
     * web用户端热门话题列表查询
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    @Override
    public List<SystemHotDO> getSystemHotlistForWeb() {
        List<SystemHotDO> systemHotList = null;

        try {
            systemHotList = sqlSessionTemplate.selectList("SystemHot_getSystemHotlistForWeb");
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }
        return systemHotList;
    }

    /**
     * 查询热门话题总数(后台操作)
     * @param noticeType 公告类型，没有填null
     * @param noticeTitle 公告标题，没有填null
     * @return 成功返回：总数，失败或无数据返回：0
     */
    public int sumHotForBack(String noticeType, String noticeTitle){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noticeTitle", noticeTitle);
        map.put("noticeType", noticeType);
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("SystemHot_sumHotForBack", map);
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        return result;
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
        List<SystemHotDO> resultList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noticeTitle", noticeTitle);
        map.put("noticeType", noticeType);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try{
            resultList = sqlSessionTemplate.selectList("SystemHot_listSystemHotForBack", map);

        }catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        return resultList;
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
    public boolean insertSystemHot(String noticeTitle, String noticeType, String noticeUrl, String content, int rankNumber, Timestamp addTime){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noticeTitle", noticeTitle);
        map.put("noticeType", noticeType);
        map.put("noticeUrl", noticeUrl);
        map.put("content", content);
        map.put("rankNumber", rankNumber);
        map.put("addTime", addTime);

        int result = 0;
        try{
            result = sqlSessionTemplate.insert("SystemHot_insertSystemHot", map);
        }catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        if (result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据记录id查询热门话题
     * @param id 记录id
     * @return 查询成功：热门话题列表；查询失败：返回null
     */
    public SystemHotDO getSystemHotById(int id){
        SystemHotDO systemHotDO = null;

        try{
            systemHotDO = sqlSessionTemplate.selectOne("SystemHot_getSystemHotById", id);
        }catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        return systemHotDO;
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noticeTitle", noticeTitle);
        map.put("noticeType", noticeType);
        map.put("noticeUrl", noticeUrl);
        map.put("content", content);
        map.put("id", id);
        int result = 0;
        try{
            result = sqlSessionTemplate.update("SystemHot_updateSystemHot", map);
        }catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        if (result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除热门话题
     * @param id 记录id
     * @return 查询成功：返回true；查询失败：返回false
     */
    public boolean deteleSystemHot(int id){
        int result = 0;

        try{
            result = sqlSessionTemplate.delete("SystemHot_deteleSystemHot", id);
        }catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        if (result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 置顶热门话题
     * @param id 记录id
     * @param topTime 置顶时间
     * @return 查询成功：返回true；查询失败：返回false
     */
    public boolean topHotTopic(int id, Timestamp topTime){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("topTime", topTime);
        int result = 0;

        try{
            result = sqlSessionTemplate.update("SystemHot_topHotTopic", map);
        }catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        if (result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查询热门话题总数 (web端)
     * @return 查询成功:返回热门话题总数, 查询失败:返回0
     */
    public int countSystemHotForUser() {
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("SystemHot_countSystemHotForUser");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询热门话题列表 (web端)
     * @param pageNumber 当前页数
     * @param pageSize 每面大小
     * @return 查询成功:返回热门话题列表, 查询失败:返回null
     */
    public List<SystemHotDO> listSystemHotForUser(int pageNumber, int pageSize) {
        List<SystemHotDO> resultList = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("SystemHot_listSystemHotForUser", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 通过排名获取当前热门话题id
     * @param rankNumber 排名
     * @return 查询成功:返回热门话题id, 查询失败:返回0
     */
    public int getIdByRankForBack(int rankNumber) {
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("SystemHot_getIdByRankForBack", rankNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 上移热门话题
     * @param id 热门话题id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean upMoveHotTopicForBack(int id) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemHot_upMoveHotTopicForBack", id );
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
     * 下移热门话题
     * @param id 热门话题id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean downMoveHotTopicForBack(int id) {
        int reslut = 0;

        try {
            reslut = sqlSessionTemplate.update("SystemHot_downMoveHotTopicForBack", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (reslut > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 所有热门话题排名+1(后台新增热门话题)
     * @return 操作成功:返回true, 操作失败:返回false
     */
    public boolean updateSystemHotRankNumber() {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemHot_updateSystemHotRankNumber");
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
     * 获取当前热门话题排名的最大位置
     * @return 查询成功：返回最大的排名，查询失败：返回0
     */
    public int getMaxRankForBack() {
        int changeNumber = 0;
        try {
            changeNumber = sqlSessionTemplate.selectOne("SystemHot_getMaxRankForBack");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return changeNumber;
    }

    /**
     * 修改热门话题排名（大于该排名的所有广告排名-1）
     * @param rankNumber 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateHotTopicRank(int rankNumber) {
        int changeNumber = 0;

        try {
            changeNumber = sqlSessionTemplate.update("SystemHot_updateHotTopicRank", rankNumber);
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
     * 修改热门话题的排名为指定排名(仅置顶功能使用)
     * @param id 热门话题id
     * @param rankNumber 指定排名
     * @return 操作成功:返回true, 操作失败:返回false
     */
    public boolean updateHotTopicToRankNumber(int id, int rankNumber) {
        int result = 0;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("rankNumber", rankNumber);

        try {
            result = sqlSessionTemplate.update("SystemHot_updateHotTopicToRankNumber", map);
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
     * 修改热门话题排名（小于该排名的所有广告排名+1）
     * @param rankNumber 排名
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateHotTopicRankNumberUp(int rankNumber) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemHot_updateHotTopicRankNumberUp", rankNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
