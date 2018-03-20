package com.jydp.service.impl.user;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.IUserFeedbackDao;
import com.jydp.entity.DO.user.UserFeedbackDO;
import com.jydp.service.IUserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Description: 意见反馈
 * Author: hht
 * Date: 2018-02-07 17:23
 */
@Service("userFeedbackService")
public class UserFeedbackServiceImpl implements IUserFeedbackService {

    /** 意见反馈 */
    @Autowired
    private IUserFeedbackDao userFeedbackDao;

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
        return userFeedbackDao.countUserFeedback(userAccount, feedbackTitle, handleStatus, startTime, endTime);
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
        return userFeedbackDao.listUserFeedbackByPage(userAccount, feedbackTitle, handleStatus, pageNumber, pageSize, startTime, endTime);
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
    public boolean updateUserFeedbackById(long id, int handleStatus, String handleContent,
                                   String backerAccount, Timestamp handleTime){
        return userFeedbackDao.updateUserFeedbackById(id, handleStatus, handleContent, backerAccount, handleTime);
    }

    /**
     * 查询意见反馈总数 (web端)
     * @param userId 用户id
     * @return 查询成功:返回意见反馈总数, 查询失败:返回0
     */
    public int countUserFeedbackForUser(int userId) {
        return userFeedbackDao.countUserFeedbackForUser(userId);
    }

    /**
     * 分页查询意见反馈 (web端)
     * @param userId 用户id
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功:返回当前页的意见反馈列表, 查询失败:返回null
     */
    public List<UserFeedbackDO> listUserFeedbackForUser(int userId, int pageNumber, int pageSize) {
        return userFeedbackDao.listUserFeedbackForUser(userId, pageNumber, pageSize);
    }

    /**
     * 新增意见反馈
     * @param userId 用户Id
     * @param userAccount 用户帐号
     * @param feedbackTitle 反馈标题
     * @param feedbackContent 反馈内容
     * @return 操作成功:返回true, 操作失败:返回false
     */
    public boolean insertUserFeedback(int userId, String userAccount, String feedbackTitle, String feedbackContent) {
        UserFeedbackDO userFeedbackDO = new UserFeedbackDO();
        userFeedbackDO.setUserId(userId);
        userFeedbackDO.setUserAccount(userAccount);
        userFeedbackDO.setFeedbackTitle(feedbackTitle);
        userFeedbackDO.setFeedbackContent(feedbackContent);
        userFeedbackDO.setAddTime(DateUtil.getCurrentTime());
        userFeedbackDO.setHandleStatus(1);

        return userFeedbackDao.insertUserFeedback(userFeedbackDO);
    }
}
