package com.jydp.service.impl.user;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.jydp.dao.IUserIdentificationDao;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private IUserIdentificationDao userIdentificationDao;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

    /**
     * 新增用户认证
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO insertUserIdentification (UserIdentificationDO userIdentificationDO) {
        return userIdentificationDao.insertUserIdentification(userIdentificationDO);
    }

    /**
     * 新增用户认证和用户认证详情图记录
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean insertUserIdentificationAndImage (UserIdentificationDO userIdentificationDO,
                                                     List<String> imageUrlList) {
        boolean executeSuccess = false;

        //新增用户认证
        UserIdentificationDO insertUserIdentification = userIdentificationDao.insertUserIdentification(userIdentificationDO);
        if (insertUserIdentification != null) {
            executeSuccess = true;
        }

        if (executeSuccess) {
            List<UserIdentificationImageDO> userIdentificationImageList = new ArrayList<>();
            Timestamp addTime = DateUtil.getCurrentTime();
            for (String url : imageUrlList) {
                UserIdentificationImageDO identificationImageDO = new UserIdentificationImageDO();
                identificationImageDO.setIdentificationId(insertUserIdentification.getId());  //用户认证Id
                identificationImageDO.setImageUrl(url);  //用户认证详情图地址
                identificationImageDO.setAddTime(addTime);  //添加时间
                userIdentificationImageList.add(identificationImageDO);
            }
            //批量新增用户认证详情图列表
            executeSuccess = userIdentificationImageService.insertUserIdentificationImageList(userIdentificationImageList);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 删除文件
            FileWriteRemoteUtil.deleteFileList(imageUrlList);
        }
        return executeSuccess;
    }

    /**
     * 查询用户认证信息
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationById(long id) {
        return userIdentificationDao.getUserIdentificationById(id);
    }

    /**
     * 验证该身份证号是否已被使用
     *
     * @param userCertNo 身份证号码
     * @return 已被使用：返回true，未被使用：返回false
     */
    public boolean validateIdentification(String userCertNo) {
        return userIdentificationDao.validateIdentification(userCertNo);
    }

    /**
     * 查询用户最近的认证信息
     *
     * @param userId 用户Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationByUserIdLately(int userId) {
        return userIdentificationDao.getUserIdentificationByUserIdLately(userId);
    }

    /**
     * 查询用户最近的认证信息
     *
     * @param userAccount 用户账号
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationByUserAccountLately(String userAccount) {
        return userIdentificationDao.getUserIdentificationByUserAccountLately(userAccount);
    }

    /**
     * 查询实名认证信息总数（后台）
     *
     * @param userAccount 用户账号(可为null)
     * @param userPhone   手机号(可为null)
     * @param userCertType   证件类型，1:身份证，2：护照
     * @param identificationStatus   实名认证状态，1：待审核，2：审核通过，3：审核拒绝（查全部为0）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 操作成功：返回用户信息总数，操作失败：返回0
     */
    public int countUserIdentificationForBacker(String userAccount, String userPhone,  int userCertType,
                                                int identificationStatus, Timestamp startTime, Timestamp endTime) {
        return userIdentificationDao.countUserIdentificationForBacker(userAccount, userPhone, userCertType, identificationStatus, startTime, endTime);
    }

    /**
     * 查询实名认证信息列表（后台）
     *
     * @param userAccount 用户账号(可为null)
     * @param userPhone   手机号(可为null)
     * @param userCertType   证件类型，1:身份证，2：护照
     * @param identificationStatus   实名认证状态，1：待审核，2：审核通过，3：审核拒绝（查全部为0）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 操作成功 ：返回用户认证信息，操作失败：返回null
     */
    public List<UserIdentificationDO> listUserIdentificationForBacker(String userAccount, String userPhone, int userCertType,
                                                                      int identificationStatus, Timestamp startTime,
                                                                      Timestamp endTime, int pageNumber, int pageSize) {
        return userIdentificationDao.listUserIdentificationForBacker(userAccount, userPhone, userCertType, identificationStatus, startTime, endTime, pageNumber, pageSize);
    }

    /**
     * 修改用户实名认证状态
     * @param id 记录Id
     * @param identificationStatus 实名状态，1：待审核，2：审核通过，3：审核拒绝
     * @param identiTime 审核时间
     * @param remark 备注，可为null
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserIdentificationStatus (long id, int identificationStatus, Timestamp identiTime, String remark) {
        return userIdentificationDao.updateUserIdentificationStatus(id, identificationStatus, identiTime, remark);
    }
}
