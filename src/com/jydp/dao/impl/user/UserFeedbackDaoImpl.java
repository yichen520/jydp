package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserFeedbackDao;
import com.jydp.entity.DO.user.UserFeedbackDO;
import org.apache.commons.collections4.map.HashedMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 意见反馈
 * Author: hht
 * Date: 2018-02-07 17:22
 */
@Repository
public class UserFeedbackDaoImpl implements IUserFeedbackDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询意见反馈总数
     * @param userAccount 反馈人账号，没有填null
     * @param feedbackTitle 反馈标题，没有填null
     * @param handleStatus 处理状态，没有填0
     * @param startTime 反馈开始时间，没有填null
     * @param endTime 反馈结束时间，没有填null
     * @return 查询成功：返回总记录数，失败或未查询到数据：返回0
     */
    public int countUserFeedback(String userAccount, String feedbackTitle, int handleStatus, Timestamp startTime,
                                 Timestamp endTime) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("userAccount", userAccount);
        map.put("feedbackTitle", feedbackTitle);
        map.put("handleStatus", handleStatus);

        int totalNumber = 0;
        try {
            totalNumber = sqlSessionTemplate.selectOne("UserFeedback_countUserFeedback", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return totalNumber;
    }

    /**
     * 分页查询意见反馈
     * @param userAccount 反馈人账号，没有填null
     * @param feedbackTitle 反馈标题，没有填null
     * @param handleStatus 处理状态，没有填0
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @param startTime 反馈时间，没有填null
     * @param endTime 反馈时间，没有填null
     * @return 查询成功：返回反馈记录列表信息，失败或未查询到数据：返回null
     */
    public List<UserFeedbackDO> listUserFeedbackByPage(String userAccount, String feedbackTitle, int handleStatus, int pageNumber,
                                                       int pageSize, Timestamp startTime, Timestamp endTime) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("startNumber", pageSize * pageNumber);
        map.put("pageSize", pageSize);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("userAccount", userAccount);
        map.put("feedbackTitle", feedbackTitle);
        map.put("handleStatus", handleStatus);

        List<UserFeedbackDO> list = null;
        try {
            list = sqlSessionTemplate.selectList("UserFeedback_listUserFeedbackByPage", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return list;
    }

    /**
     * 回复意见反馈(后台回复)
     * @param id 记录Id
     * @param handleStatus 处理状态，1：待处理，2：处理中，3：已处理
     * @param handleContent 处理说明
     * @param backerAccount 处理的后台管理员帐号
     * @param handleTime 处理时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateUserFeedbackById(long id, int handleStatus, String handleContent, String backerAccount, Timestamp handleTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("handleStatus", handleStatus);
        map.put("handleContent", handleContent);
        map.put("backerAccount", backerAccount);
        map.put("handleTime", handleTime);

        int result = 0;

        try {
            result = sqlSessionTemplate.update("UserFeedback_updateUserFeedbackById", map);
        } catch(Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询意见反馈总数 (web端)
     * @return 查询成功:返回意见反馈总数, 查询失败:返回0
     */
    public int countUserFeedbackForUser() {
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("UserFeedback_countUserFeedbackForUser");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 分页查询意见反馈 (web端)
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功:返回当前页的意见反馈列表, 查询失败:返回null
     */
    public List<UserFeedbackDO> listUserFeedbackForUser(int pageNumber, int pageSize) {
        List<UserFeedbackDO> resultList = null;

        Map<String, Object> map = new HashedMap<String, Object>();
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("UserFeedback_listUserFeedbackForUser", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 新增意见反馈
     * @param userFeedbackDO 待新增的意见反馈
     * @return 操作成功:返回true, 操作失败:返回false
     */
    public boolean insertUserFeedback(UserFeedbackDO userFeedbackDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserFeedback_insertUserFeedback", userFeedbackDO);
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
