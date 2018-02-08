package com.jydp.service.impl.user;

import com.jydp.dao.IUserIdentificationDao;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.service.IUserIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Description: 用户认证
 * Author: hht
 * Date: 2018-02-07 16:56
 */
@Service("userIdentificationService")
public class UserIdentificationServiceImpl implements IUserIdentificationService {

    /** 用户认证 */
    @Autowired
    private IUserIdentificationDao userIdentificationService;

    /**
     * 新增用户认证
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertUserIdentification (UserIdentificationDO userIdentificationDO) {
        return userIdentificationService.insertUserIdentification(userIdentificationDO);
    }

    /**
     * 查询用户认证信息
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationById(long id) {
        return userIdentificationService.getUserIdentificationById(id);
    }

    /**
     * 查询用户认证信息
     *
     * @param userCertNo 身份证号码
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationByUserCertNo(String userCertNo) {
        return userIdentificationService.getUserIdentificationByUserCertNo(userCertNo);
    }

    /**
     * 查询实名认证信息总数（后台）
     *
     * @param userAccount 用户账号(可为null)
     * @param userPhone   手机号(可为null)
     * @param identificationStatus   实名认证状态，1：待审核，2：审核通过，3：审核拒绝（查全部为0）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 操作成功：返回用户信息总数，操作失败：返回0
     */
    public int countUserIdentificationForBacker(String userAccount, String userPhone, int identificationStatus, Timestamp startTime, Timestamp endTime) {
        return userIdentificationService.countUserIdentificationForBacker(userAccount, userPhone, identificationStatus, startTime, endTime);
    }

    /**
     * 查询实名认证信息列表（后台）
     *
     * @param userAccount 用户账号(可为null)
     * @param userPhone   手机号(可为null)
     * @param identificationStatus   实名认证状态，1：待审核，2：审核通过，3：审核拒绝（查全部为0）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 操作成功 ：返回用户认证信息，操作失败：返回null
     */
    public List<UserIdentificationDO> listUserIdentificationForBacker(String userAccount, String userPhone, int identificationStatus,
                                                                      Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize) {
        return userIdentificationService.listUserIdentificationForBacker(userAccount, userPhone, identificationStatus, startTime, endTime, pageNumber, pageSize);
    }
}
