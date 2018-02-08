package com.jydp.dao;

import com.jydp.entity.DO.user.UserIdentificationDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Description: 用户认证
 * Author: hht
 * Date: 2018-02-07 16:29
 */
public interface IUserIdentificationDao {

    /**
     * 新增用户认证
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean insertUserIdentification (UserIdentificationDO userIdentificationDO);

    /**
     * 查询用户认证信息
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    UserIdentificationDO getUserIdentificationById(long id);

    /**
     * 查询用户认证信息
     *
     * @param userCertNo 身份证号码
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    UserIdentificationDO getUserIdentificationByUserCertNo(String userCertNo);

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
    int countUserIdentificationForBacker(String userAccount, String userPhone, int identificationStatus, Timestamp startTime, Timestamp endTime);

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
    List<UserIdentificationDO> listUserIdentificationForBacker(String userAccount, String userPhone, int identificationStatus,
                                                               Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize);
}
